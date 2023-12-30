package org.ecomm.ecommproduct.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Table(name = "inventory")
public class EInventory extends BaseEntity {

    @Column(name = "product_id")
    Integer productId;

    @Column(name = "sku")
    String sku;

    @Column(name = "quantity_available")
    int quantityAvailable;

    @Column(name = "quantity_reserved")
    int quantityReserved;

    @Column(name = "quantity_sold")
    int quantitySold;

}
