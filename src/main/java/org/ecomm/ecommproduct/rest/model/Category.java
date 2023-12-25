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
public class Category extends BaseModel {

    String name;
    List<Category> categories;

}
