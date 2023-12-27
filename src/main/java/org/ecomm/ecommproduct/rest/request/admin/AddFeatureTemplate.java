package org.ecomm.ecommproduct.rest.request.admin;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFeatureTemplate {

    Integer categoryId;
    Object features;

}
