package org.ecomm.ecommproduct.persistance.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "product")
public class EProduct extends BaseEntity {

  String name;
  String description;
  double price;
  Integer brandId;

  @OneToOne
  @JoinColumn(name = "category_id")
  ECategory category;

  @Column(name = "category_tree")
  String categoryTree;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  JsonNode features;

  @OneToMany(mappedBy = "product")
  List<EProductImage> productImages;
}
