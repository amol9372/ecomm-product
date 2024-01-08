package org.ecomm.ecommproduct.rest.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class Promotion extends BaseModel {

  String name;
  String description;
  String appliedTo;
  String appliedToType;
  int expiresIn;
  int discount;
}
