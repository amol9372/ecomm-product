package org.ecomm.ecommproduct.rest.controller;

import org.ecomm.ecommproduct.rest.model.Product;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.ecomm.ecommproduct.rest.services.ProductESService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product")
public class ProductController {

  @Autowired ProductESService productESService;

  @PostMapping("search")
  public PagedResponse<Product> searchProducts(@RequestBody SearchRequest request) {
    
    return productESService.searchProducts(request);
  }
}
