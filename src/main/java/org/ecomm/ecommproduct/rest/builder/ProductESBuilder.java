package org.ecomm.ecommproduct.rest.builder;

import java.util.List;
import java.util.stream.Collectors;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.rest.model.Product;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESCategory;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESInventory;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProduct;
import org.ecomm.ecommproduct.utils.Utility;

public class ProductESBuilder {

  public static ESProduct with(EProduct product, EInventory inventory) {

    return ESProduct.builder()
        .id(product.getId())
        .features(Utility.convertKeyValueToObject(product.getFeatures()))
        .price(product.getPrice())
        .name(product.getName())
        .description(product.getDescription())
        .category(
            ESCategory.builder()
                .id(product.getCategory().getId())
                .name(product.getCategory().getName())
                .build())
        .categoryTree(product.getCategoryTree())
        .inventory(
            ESInventory.builder()
                .id(inventory.getId())
                .quantityAvailable(inventory.getQuantityAvailable())
                .sku(inventory.getSku())
                .build())
        .build();
  }

  public static Product with(ESProduct esProduct) {
    return Product.builder()
        .id(esProduct.getId())
        .features(esProduct.getFeatures())
        .price(esProduct.getPrice())
        .name(esProduct.getName())
        .description(esProduct.getDescription())
        .category(
            Category.builder()
                .name(esProduct.getCategory().getName())
                .id(esProduct.getCategory().getId())
                .build())
        .inventory(
            Inventory.builder()
                .id(esProduct.getInventory().getId())
                .quantityAvailable(esProduct.getInventory().getQuantityAvailable())
                .sku(esProduct.getInventory().getSku())
                .build())
        .build();
  }

  public static List<Product> with(List<ESProduct> esProducts) {
    return Utility.stream(esProducts).map(ProductESBuilder::with).collect(Collectors.toList());
  }
}
