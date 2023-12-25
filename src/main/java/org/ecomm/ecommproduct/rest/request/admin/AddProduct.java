package org.ecomm.ecommproduct.rest.request.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommproduct.rest.model.ProductImage;

import java.util.List;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class AddProduct {

  String name;
  String description;
  double price;
  Integer category;
  Object features;
  List<ProductImage> images;
  // inventory
  String sku;
  int quantity;
}
