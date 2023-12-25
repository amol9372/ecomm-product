package org.ecomm.ecommproduct.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Product {

  String name;
  String description;
  double price;
  Category category;
  List<ProductImage> images;
  // inventory
  String sku;
  int quantity;
}
