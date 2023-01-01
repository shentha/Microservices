package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Inventory;
import com.example.demo.productservice.model.Price;
import com.example.demo.productservice.model.Product;
import com.example.demo.productservice.model.ProductInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {
    List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

    public WebClient webClient = WebClient.create();

    {
        loadProductList();
    }


    public void ProductController(){
        loadProductList();
    }


    @GetMapping("/products/{productid}")
    public Mono<Product> getProduct(@PathVariable( name = "productid") Long productId){
         Mono<ProductInfo> prodInfo = Mono.just( getProductInfo( productId ).orElseThrow( ()-> new RuntimeException("Id not found")) );

         //get price from pricing service
         Mono<Price> price = webClient.get().uri( "http://localhost:8002/price/{productid}", productId).retrieve().bodyToMono( Price.class);

         //get inventory from inventory service
         Mono<Inventory> inventory = webClient.get().uri( "http://localhost:8003/inventory/{productid}",productId).retrieve().bodyToMono( Inventory.class);

         //return new Product( prodInfo.,prodInfo.getProductName(),prodInfo.getProductDesc(), 999.70,true);
         return Mono.zip( prodInfo, price, inventory).map( tuple -> new Product( tuple.getT1().getProductID(), tuple.getT1().getProductName(), tuple.getT1().getProductDesc(),
                 tuple.getT2().getDiscountedPrice(), tuple.getT3().getStock()) );
    }

    @GetMapping("/products")
    public Flux<Product> getProducts(){
        return Flux.fromStream( productInfoList.stream() ).flatMap( productInfo -> {
            Mono<Price> price = webClient.get().uri( "http://localhost:8002/price/{productid}", productInfo.getProductID()).retrieve().bodyToMono( Price.class);

            //get inventory from inventory service
            Mono<Inventory> inventory = webClient.get().uri( "http://localhost:8003/inventory/{productid}",productInfo.getProductID()).retrieve().bodyToMono( Inventory.class);

            //return new Product( prodInfo.,prodInfo.getProductName(),prodInfo.getProductDesc(), 999.70,true);
            return Mono.zip( price, inventory).map( tuple -> new Product( productInfo.getProductID(), productInfo.getProductName(), productInfo.getProductDesc(),
                    tuple.getT1().getDiscountedPrice(), tuple.getT2().getStock()) );
        });
    }

    private Optional<ProductInfo>  getProductInfo(Long productid) {
        return productInfoList.stream().filter(p -> p.getProductID() == productid ).findAny();
    }

    private void loadProductList(){
        productInfoList.add( new ProductInfo( 101L, "iphone", "iphone-14" ) );
        productInfoList.add( new ProductInfo( 102L, "Book", "Spring Dev Exam Book" ) );
        productInfoList.add( new ProductInfo( 103L, "RoboRock", "Vaccum and Mop" ) );
    }
}
