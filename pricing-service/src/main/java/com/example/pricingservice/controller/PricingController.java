package com.example.pricingservice.controller;


import com.example.pricingservice.model.Exchange;
import com.example.pricingservice.model.Price;
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
public class PricingController {
    List<Price> priceList = new ArrayList<Price>();

    @Autowired
    ExchangeClient exchangeClient;


    {
        loadPriceList();
    }

    @GetMapping("/price/{productid}")
    public Price getPriceDetails(@PathVariable Long productid) {
        Price price = getPriceInfo(productid).orElseThrow(()->new NoSuchElementException("Id not found"));

        // Get Exchange Value  /currexchng/{from}/{to}
        Double exgVal = exchangeClient.getExchangeInfo( "USD","YEN").getExchangeValue() ;

        return new Price(price.getPriceId(), price.getProductID(), price.getOriginalPrice(),
                (int)(exgVal*price.getDiscountedPrice()));
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
