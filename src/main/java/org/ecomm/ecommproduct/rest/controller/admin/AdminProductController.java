package org.ecomm.ecommproduct.rest.controller.admin;

import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.ecomm.ecommproduct.rest.services.admin.AdminProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("admin/product")
public class AdminProductController {

  // create offer - [can be different microservice]
  @Autowired AdminProductService adminProductService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public void addProduct(@RequestBody AddProductRequest request) {
    adminProductService.addProduct(request);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(
      value = "v2",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public void addProductV2(
          @RequestPart("data") AddProductRequest request, @RequestPart("files") List<MultipartFile> files) {



    adminProductService.addProduct(request);
  }

  @GetMapping
  public void getProduct() {
    adminProductService.getProduct();
  }
}
