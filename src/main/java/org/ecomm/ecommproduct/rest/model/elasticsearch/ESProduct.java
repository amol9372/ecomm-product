package org.ecomm.ecommproduct.rest.model.elasticsearch;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "productv1", writeTypeHint = WriteTypeHint.FALSE)
public class ESProduct {

  @Id Integer id;

  @Field(type = FieldType.Text, name = "name")
  String name;

  @Field(type = FieldType.Text, name = "description")
  String description;

  @Field(type = FieldType.Double_Range, name = "price")
  double price;

  @Field(type = FieldType.Object, name = "category")
  ESCategory category;

  @Field(type = FieldType.Keyword, name = "category_tree")
  String categoryTree;

  @Field(type = FieldType.Object, name = "features")
  Object features;

  @Field(type = FieldType.Object, name = "inventory")
  ESInventory inventory;
}
