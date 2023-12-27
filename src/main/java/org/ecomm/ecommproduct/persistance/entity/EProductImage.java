package org.ecomm.ecommproduct.persistance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.ecomm.ecommweb.persistance.entity.BaseEntity;

@Entity
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "product_images")
public class EProductImage extends BaseEntity {

  @Column(name = "image_url")
  String imageUrl;

  @Enumerated(EnumType.STRING)
  ProductImageType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  EProduct product;
}
