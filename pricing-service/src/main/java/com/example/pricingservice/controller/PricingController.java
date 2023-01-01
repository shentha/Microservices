package com.example.pricingservice.controller;


import com.example.pricingservice.model.Price;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Mono;

@RestController
public class PricingController {
    List<Price> priceList = new ArrayList<Price>();

    {
        loadPriceList();
    }

    @GetMapping("/price/{productid}")
    public Mono<Price> getProductInfo(@PathVariable( name = "productid") Long productId){
        Mono<Price> price = Mono.just( getPriceInfo( productId ).orElseThrow( ()-> new RuntimeException("Id not found")));
        return price;
    }

    private Optional<Price> getPriceInfo(Long productid) {
        return priceList.stream().filter(p -> p.getProductID() == productid ).findAny();
    }

    private void loadPriceList(){
        priceList.add( new Price( 1L,101L, 990, 450 ) );
        priceList.add( new Price( 2L,102L, 45, 35 ) );
        priceList.add( new Price( 3L,103L, 650, 460 ) );
    }
}
