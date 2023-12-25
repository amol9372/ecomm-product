package org.ecomm.ecommproduct.rest.services.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.persistance.repository.CategoryRepository;
import org.ecomm.ecommproduct.persistance.repository.InventoryRepository;
import org.ecomm.ecommproduct.persistance.repository.ProductRepository;
import org.ecomm.ecommproduct.rest.request.admin.AddProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminProductServiceImpl implements AdminProductService {

  @Autowired CategoryRepository categoryRepository;

  @Autowired ObjectMapper objectMapper = new ObjectMapper();

  @Autowired ProductRepository productRepository;

  @Autowired InventoryRepository inventoryRepository;

  @Override
  public void addProduct(AddProduct product) {
    // TODO - name validation
    // get category
    // validate features
    // save images & get s3 url
    // update inventory
    // TODO - save the entity in opensearch
    var category =
        categoryRepository
            .findById(product.getCategory())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Product category does not exist, please use GET /admin/category API to fetch all categories"));

    JsonNode jsonNode = objectMapper.valueToTree(product.getFeatures());

    var eProduct =
        EProduct.builder()
            .name(product.getName())
            .price(product.getPrice())
            .description(product.getDescription())
            .category(category)
            .features(jsonNode)
            .build();

    EProduct savedProduct = productRepository.save(eProduct);

    // Update the inventory
    inventoryRepository.save(
        EInventory.builder().sku(product.getSku()).productId(savedProduct.getId()).build());


  }
}
