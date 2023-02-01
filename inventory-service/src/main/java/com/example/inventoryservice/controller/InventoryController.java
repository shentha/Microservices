package com.example.inventoryservice.controller;

import com.example.inventoryservice.model.Inventory;
import org.springframework.beans.factory.annotation.Value;
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
    @Value( "${server.port}")
    private String port;

    {
        loadInventoryList();
    }

    @GetMapping ("/inventory/port")
    public String getInventoryDetails(){
        return port;
    }

    @GetMapping("/inventory/{productid}")
    public  Inventory getInventoryInfo(@PathVariable( name = "productid") Long productId) throws InterruptedException {
        Inventory inventory =  getInventory( productId ).orElseThrow( ()-> new RuntimeException("Id not found"));
        if ( true ){
            throw new RuntimeException();
        }
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
