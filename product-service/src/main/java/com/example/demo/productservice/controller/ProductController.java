package com.example.demo.productservice.controller;

import com.example.demo.productservice.model.Inventory;
import com.example.demo.productservice.model.Price;
import com.example.demo.productservice.model.Product;
import com.example.demo.productservice.model.ProductInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ProductController {
    List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();

    @Autowired
    PriceClient priceClient;

    @Autowired
    InventoryClient inventoryClient;

    @Autowired
    private EurekaClient eurekaClient;

    {
        loadProductList();
    }


    public void ProductController(){
        loadProductList();
    }

    @GetMapping("/products/{productid}")
    public Product getProductDetails(@PathVariable Long productid) {

        System.out.println( "Apps List : " +  eurekaClient.getApplications().getRegisteredApplications() );

        // Get Name and Desc from product-service
        ProductInfo productInfo = getProductInfo(productid).orElseThrow(()->new NoSuchElementException("Id not found"));

        // Get Price from pricing-service
        Price price = priceClient.getProductInfo( productid );

        // Get Stock Avail from inventory-service
        Inventory inventory = inventoryClient.getInventoryInfo( productid );

        return new Product(productInfo.getProductID(), productInfo.getProductName(), productInfo.getProductDesc(), price.getDiscountedPrice(),
                inventory.getStock());
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
