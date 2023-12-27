package org.ecomm.ecommproduct.rest.model.elasticsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Setter
@Builder
@Document(indexName = "productv1")
public class ESProduct {

  @Id Integer id;

  @Field(type = FieldType.Text, name = "name")
  String name;

  @Field(type = FieldType.Text, name = "description")
  String description;

  @Field(type = FieldType.Double_Range, name = "price")
  double price;

  @Field(type = FieldType.Object, name = "category")
  Object category;

  @Field(type = FieldType.Text, name = "categoryTree")
  String categoryTree;

  @Field(type = FieldType.Object, name = "features")
  Object features;

  @Field(type = FieldType.Object, name = "inventory")
  Object inventory;
}
