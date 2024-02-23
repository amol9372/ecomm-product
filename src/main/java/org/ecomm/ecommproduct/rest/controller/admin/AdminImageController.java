package org.ecomm.ecommproduct.rest.controller.admin;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.ecomm.ecommproduct.rest.services.ProductService;
import org.ecomm.ecommproduct.rest.services.admin.AdminProductImageService;
import org.ecomm.ecommproduct.rest.services.admin.AdminProductService;
import org.ecomm.ecommproduct.utils.S3Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("admin/image")
public class AdminImageController {

  @Autowired S3Utility s3Utility;

  @Autowired HttpServletRequest request;

  @Autowired AdminProductImageService adminProductImageService;

  @PostMapping("upload")
  @Operation(description = "Upload files for a particular product variant")
  public ResponseEntity<?> uploadImagesForVariant(
      @RequestParam("files") List<MultipartFile> files,
      @RequestParam("variant") Integer variantId) {

    adminProductImageService.uploadImageForVariant(files, variantId);

    return new ResponseEntity<>(null, HttpStatus.CREATED);
  }
}
