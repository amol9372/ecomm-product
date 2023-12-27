package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.ecomm.ecommproduct.rest.services.admin.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/product")
public class AdminProductController {

    // create offer - [can be different microservice]
    @Autowired
    AdminProductService adminProductService;

    @PostMapping
    public void addProduct(@RequestBody AddProductRequest product){
        adminProductService.addProduct(product);
    }

}
