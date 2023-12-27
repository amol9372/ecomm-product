package org.ecomm.ecommproduct.rest.controller;

import org.ecomm.ecommproduct.rest.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @PostMapping
    public List<Product> searchProducts(){
        return null;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return null;
    }

}
