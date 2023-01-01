package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import reactor.core.publisher.Mono;

@RestController
public class InventoryController {
    List<Inventory> inventoryList = new ArrayList<Inventory>();

    {
        loadInventoryList();
    }


    @GetMapping("/inventory/{productid}")
    public Mono<Inventory> getInventoryInfo(@PathVariable( name = "productid") Long productId){
        Mono<Inventory> inventory = Mono.just( getInventory( productId ).orElseThrow( ()-> new RuntimeException("Id not found")));
        return inventory;
    }


    private Optional<Inventory> getInventory(Long productid) {
        return inventoryList.stream().filter(p -> p.getProductID() == productid ).findAny();
    }

    private void loadInventoryList(){
        inventoryList.add( new Inventory( 1L,101L, true ) );
        inventoryList.add( new Inventory( 2L,102L, true ) );
        inventoryList.add( new Inventory( 3L,103L, false) );
    }
}
