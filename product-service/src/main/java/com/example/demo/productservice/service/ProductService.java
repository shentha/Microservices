package com.example.demo.productservice.service;

import com.example.demo.productservice.model.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    public void configureHystrix(){

    }


}
