package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Inventory;
import com.example.demo.productservice.model.Price;
import com.example.demo.productservice.model.Product;
import com.example.demo.productservice.model.ProductInfo;
import com.example.demo.productservice.service.ProductService;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactivefeign.webclient.WebReactiveFeign;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ProductController {
    List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

    @Autowired
    ProductService productService;

    @Autowired
    PriceClient priceClient;

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    private EurekaClient eurekaClient;

    @Value( "${eureka.client.service-url.defaultZone}")
    String url ;

    {
        loadProductList();
    }

    public void ProductController(){
        loadProductList();
    }


    @GetMapping("/products/port")
    public String getPortInfo(){
        return inventoryClient.getInventoryDetails();
    }


    @HystrixProperty( name = "execution.isolation.thread.timeoutInMilliseconds", value="2000")
    @HystrixCommand( fallbackMethod = "fallbackMethod")
    @GetMapping("/products/{productid}")
    public Product getProductDetails(@PathVariable Long productid) {

        System.out.println( "Apps List : " +  eurekaClient.getApplications().getRegisteredApplications() );

        // Get Name and Desc from product-service
        ProductInfo productInfo = getProductInfo(productid).orElseThrow(()->new NoSuchElementException("Id not found"));

        RestTemplate restTemplate = new RestTemplate();

        // Get Price from pricing-service
        Price price = restTemplate.getForObject( "http://localhost:8002/price/"+productid , Price.class);

        // Get Stock Avail from inventory-service
        Inventory inventory = restTemplate.getForObject("http://localhost:8005/inventory/"+productid, Inventory.class);

        return new Product(productInfo.getProductID(), productInfo.getProductName(), productInfo.getProductDesc(), price.getDiscountedPrice(),
                inventory.getStock());
    }

    public Product fallbackMethod(Long productid){
        System.out.println( " In fallback method");
        return new Product();
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
