package org.ecomm.ecommproduct.rest.services.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.exception.ErrorResponse;
import org.ecomm.ecommproduct.exception.InvalidSchemaException;
import org.ecomm.ecommproduct.persistance.entity.*;
import org.ecomm.ecommproduct.persistance.repository.CategoryRepository;
import org.ecomm.ecommproduct.persistance.repository.FeatureTemplateRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.persistance.repository.ProductRepository;
import org.ecomm.ecommproduct.rest.model.Inventory;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProduct;
import org.ecomm.ecommproduct.rest.request.admin.AddProductRequest;
import org.ecomm.ecommproduct.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

  @Autowired CategoryRepository categoryRepository;

  @Autowired ObjectMapper objectMapper = new ObjectMapper();

  @Autowired ProductRepository productRepository;

  @Autowired InventoryRepository inventoryRepository;

  @Autowired FeatureTemplateRepository featureTemplateRepository;

  @Autowired ElasticsearchOperations elasticsearchOperations;

  @Autowired AdminCategoryService adminCategoryService;

  @Override
  @Transactional
  public void addProduct(AddProductRequest request) {
    // TODO - name validation [with ElasticSearch]
    // TODO - features validation
    // update inventory
    // save product
    // TODO - save images & get s3 url
    // save the entity in elasticsearch
    var category =
        categoryRepository
            .findById(request.getCategory())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Product category does not exist, please use GET /admin/category API to fetch all categories"));

    String categoryTree = getCategoryTree(category);

    JsonNode features = objectMapper.valueToTree(request.getFeatures());

    // validateFeatureTemplate(product, features);

    List<EProductImage> productImages =
        Utility.stream(request.getImages())
            .map(
                item ->
                    EProductImage.builder()
                        .imageUrl(item.getUrl())
                        // .product(savedProduct)
                        .type(ProductImageType.PRODUCT_IMAGES)
                        .build())
            .collect(toList());

    var eProduct =
        EProduct.builder()
            .name(request.getName())
            .price(request.getPrice())
            .description(request.getDescription())
            .category(category)
            .categoryTree(categoryTree)
            .features(features)
            .productImages(productImages)
            .build();

    EProduct savedProduct = productRepository.save(eProduct);

    // Update the inventory
    EInventory savedInventory =
        inventoryRepository.save(
            EInventory.builder()
                .sku(request.getSku())
                .quantityAvailable(request.getQuantity())
                .productId(savedProduct.getId())
                .build());

    saveProductIndex(request, savedProduct, category, savedInventory);
  }

  private String getCategoryTree(ECategory category) {
    List<ECategory> eCategories = categoryRepository.findAll();
    var categoryTreeBuilder = createCategoryTree(eCategories, category, new StringBuilder());

    var categoryTree = Arrays.stream(categoryTreeBuilder.toString().split(" > ")).collect(toList());
    Collections.reverse(categoryTree);

    return categoryTree.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(" > "));
  }

  private StringBuilder createCategoryTree(
      List<ECategory> entities, ECategory eCategory, StringBuilder categoryTree) {

    if (null == eCategory.getId()) {
      return categoryTree;
    }

    ECategory entity =
        entities.stream()
            .filter(item -> eCategory.getId().equals(item.getId()))
            .findFirst()
            .orElseThrow();

    createCategoryTree(
        entities,
        ECategory.builder().id(entity.getParentId()).build(),
        categoryTree.append(entity.getName()).append(" > "));

    return categoryTree;
  }

  private void saveProductIndex(
      AddProductRequest product,
      EProduct savedProduct,
      ECategory category,
      EInventory savedInventory) {
    ESProduct esProduct =
        ESProduct.builder()
            .id(savedProduct.getId())
            .features(product.getFeatures())
            .price(product.getPrice())
            .name(product.getName())
            .description(product.getDescription())
            .category(category)
            .categoryTree(savedProduct.getCategoryTree())
            .inventory(
                Inventory.builder()
                    .id(savedProduct.getId())
                    .quantityAvailable(savedInventory.getQuantityAvailable())
                    .sku(savedInventory.getSku())
                    .build())
            .build();

    elasticsearchOperations.save(esProduct);
  }

  private void validateFeatureTemplate(AddProductRequest request, JsonNode features) {
    var template = featureTemplateRepository.findByCategoryId(request.getCategory());

    // match template features with request features
    var templateFeatures = template.getFeatures();
    boolean isRequestConsistentWithTemplate =
        templateFeatures.fieldNames().equals(features.fieldNames());

    if (!isRequestConsistentWithTemplate) {
      throw new InvalidSchemaException(
          HttpStatus.UNPROCESSABLE_ENTITY,
          ErrorResponse.builder()
              .code("product-invalid-schema")
              .message(
                  "Please use the correct template for features, use the GET /admin/template/{category-id} API to get the template")
              .build());
    }
  }
}
