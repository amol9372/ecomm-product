package org.ecomm.ecommproduct.rest.builder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.persistance.entity.EProductVariant;
import org.ecomm.ecommproduct.rest.model.Category;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.rest.model.Product;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESCategory;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESInventory;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProductVariant;
import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.ecomm.ecommproduct.utils.Utility;

@Slf4j
public class ProductESBuilder {

  public static List<ESProductVariant> with(EProduct product) {

    return Utility.stream(product.getVariants())
        .map(
            item -> {
              ObjectNode allFeatures = mergeProductFeatures(product, item);

              var variantName = generateVariantName(product, item);
              ESInventory inventory = getVariantInventory(product, item);

                Object mergedFeatures = transformFeaturesToObject(allFeatures);

                return ESProductVariant.builder()
                  .id(item.getId())
                  .productId(product.getId())
                  .features(mergedFeatures)
                  .brand(product.getBrand().getName())
                  .brandCategory(product.getBrand().getBrandCategory())
                  .price(item.getPrice())
                  .name(variantName.toString())
                  .description(product.getDescription())
                  .category(
                      ESCategory.builder()
                          .id(product.getCategory().getId())
                          .name(product.getCategory().getName())
                          .build())
                  .categoryTree(product.getCategoryTree())
                  .inventory(inventory)
                  .build();
            })
        .collect(Collectors.toList());
  }

    private static Object transformFeaturesToObject(ObjectNode allFeatures) {
        Object mergedFeatures;
        var mapper = new ObjectMapper();
        try {
          mergedFeatures = mapper.treeToValue(allFeatures, Object.class);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
        return mergedFeatures;
    }

    private static ESInventory getVariantInventory(EProduct product, EProductVariant item) {
    return product.getInventories().stream()
        .filter(inventory -> inventory.getSku().equals(item.getSku()))
        .map(
            inventory ->
                ESInventory.builder()
                    .id(inventory.getId())
                    .sku(inventory.getSku())
                    .quantityAvailable(inventory.getQuantityAvailable())
                    .build())
        .findFirst()
        .orElseThrow();
  }

  private static StringBuilder generateVariantName(EProduct product, EProductVariant item) {
    var fieldIterator = item.getFeatureValues().fieldNames();

    var variantName = new StringBuilder(product.getName());
    while (fieldIterator.hasNext()) {
      var fieldName = fieldIterator.next();
      var value = item.getFeatureValues().get(fieldName).textValue();
      variantName.append(" ").append(value).append(" ").append(fieldName);
    }
    return variantName;
  }

  private static ObjectNode mergeProductFeatures(EProduct product, EProductVariant item) {
    var baseFields = product.getFeatures();
    var variantFields = item.getFeatureValues();

    ObjectNode allFeatures = baseFields.deepCopy();
    allFeatures.setAll((ObjectNode) variantFields);
    return allFeatures;
  }

  public static Product with(ESProductVariant esProductVariant) {
    return Product.builder()
        .id(esProductVariant.getId())
        .features(esProductVariant.getFeatures())
        .price(esProductVariant.getPrice())
        .name(esProductVariant.getName())
        .description(esProductVariant.getDescription())
        .category(
            Category.builder()
                .name(esProductVariant.getCategory().getName())
                .id(esProductVariant.getCategory().getId())
                .build())
        .inventory(
            Inventory.builder()
                .id(esProductVariant.getInventory().getId())
                .quantityAvailable(esProductVariant.getInventory().getQuantityAvailable())
                .sku(esProductVariant.getInventory().getSku())
                .build())
        .build();
  }

  public static List<Product> with(List<ESProductVariant> esProductVariants) {
    return Utility.stream(esProductVariants)
        .map(ProductESBuilder::with)
        .collect(Collectors.toList());
  }
}
