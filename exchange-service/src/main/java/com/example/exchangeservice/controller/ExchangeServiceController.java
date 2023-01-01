package com.example.exchangeservice.controller;

import com.example.exchangeservice.model.Currencies;
import com.example.exchangeservice.model.Exchange;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
public class ExchangeServiceController {
    List<Exchange> exchangeList = new ArrayList<Exchange>();

    {
        loadExchangeList();
    }

    @GetMapping("/currexchng/{from}/{to}")
    public Exchange getExchangeInfo(@PathVariable( name = "from") String from, @PathVariable( name = "to") String to){
        Exchange exchange = getExchange( from, to).orElseThrow( ()-> new NoSuchElementException());
        return  exchange;
    }

    private Optional<Exchange> getExchange(String from , String to) {
        return exchangeList.stream()
                .filter(ex -> ex.getFrom().toString().equalsIgnoreCase( from) && ex.getTo().toString().equalsIgnoreCase(to)).findAny();
    }

    private void loadExchangeList(){
        exchangeList.add( new Exchange( 1L, Currencies.USD, Currencies.Yen,103) );
        exchangeList.add( new Exchange( 2L,Currencies.USD, Currencies.Rupee,78) );
        exchangeList.add( new Exchange( 3L,Currencies.USD, Currencies.Euro,.93) );
    }
}
