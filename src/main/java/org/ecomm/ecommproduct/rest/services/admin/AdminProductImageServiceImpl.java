package org.ecomm.ecommproduct.rest.services.admin;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import org.ecomm.ecommproduct.persistance.entity.EProductImage;
import org.ecomm.ecommproduct.persistance.entity.EProductVariant;
import org.ecomm.ecommproduct.persistance.entity.ProductImageType;
import org.ecomm.ecommproduct.persistance.repository.ProductImageRepository;
import org.ecomm.ecommproduct.persistance.repository.ProductVariantRepository;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProductVariant;
import org.ecomm.ecommproduct.rest.services.ProductESService;
import org.ecomm.ecommproduct.utils.S3Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AdminProductImageServiceImpl implements AdminProductImageService {

  @Autowired S3Utility s3Utility;

  @Autowired ProductVariantRepository productVariantRepository;

  @Autowired ProductImageRepository productImageRepository;

  @Autowired
    ProductESService productESService;

  @Override
  @Transactional
  public void uploadImageForVariant(List<MultipartFile> files, Integer variant) {

    List<String> images = s3Utility.putObjects(files, variant);
    EProductVariant eProductVariant = productVariantRepository.findById(variant).orElseThrow();

    List<EProductImage> variantsWithImages =
        images.stream()
            .map(
                imageUrl ->
                    EProductImage.builder()
                        .imageUrl(imageUrl)
                        .type(ProductImageType.PRODUCT_IMAGES)
                        .productVariant(eProductVariant)
                        .build())
            .collect(Collectors.toList());

    productImageRepository.saveAll(variantsWithImages);
    productESService.updateProductImages(String.valueOf(variant), images);
  }
}
