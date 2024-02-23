package org.ecomm.ecommproduct.rest.builder;

import org.ecomm.ecommproduct.persistance.entity.*;
import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.model.ProductDetails;
import org.ecomm.ecommproduct.rest.model.ProductImage;
import org.ecomm.ecommproduct.rest.request.admin.Brand;
import org.ecomm.ecommproduct.rest.request.admin.ProductVariant;
import org.ecomm.ecommproduct.utils.Utility;

import java.util.List;
import java.util.stream.Collectors;

public class ProductBuilder {

  public static ProductDetails with(EProduct eProduct) {

    return ProductDetails.builder()
        .description(eProduct.getDescription())
        .category(
            Category.builder()
                .id(eProduct.getCategory().getId())
                .name(eProduct.getCategory().getName())
                .build())
        .id(eProduct.getId())
        .categoryTree(eProduct.getCategoryTree())
        .features(eProduct.getFeatures())
        .name(eProduct.getName())
        .brand(
            Brand.builder()
                .name(eProduct.getBrand().getName())
                .category(eProduct.getCategoryTree())
                .build())
        .variants(
            Utility.stream(eProduct.getVariants())
                .map(eProductVariant -> with(eProductVariant, eProduct.getInventories()))
                .collect(Collectors.toList()))
        .build();
  }

  public static ProductVariant with(EProductVariant eProductVariant, List<EInventory> inventories) {

    int quantity =
        inventories.stream()
            .filter(inventory -> inventory.getSku().equals(eProductVariant.getSku()))
            .findFirst()
            .orElseThrow()
            .getQuantityAvailable();

    return ProductVariant.builder()
        .price(eProductVariant.getPrice())
        .sku(eProductVariant.getSku())
        .id(eProductVariant.getId())
        .features(eProductVariant.getFeatureValues())
        .images(getProductImages(eProductVariant.getProductImages()))
        .quantity(quantity)
        .build();
  }

  public static List<ProductImage> getProductImages(List<EProductImage> images) {
    return Utility.stream(images)
        .map(
            image ->
                ProductImage.builder()
                    .url(image.getImageUrl())
                    .type(image.getType().name())
                    .build())
        .collect(Collectors.toList());
  }
}
