package org.ecomm.ecommproduct.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel {

  String name;
  String description;
  double price;
  Category category;
  Object features;
  List<ProductImage> images;
  // inventory
  String sku;
  int quantity;
  Inventory inventory;
}
