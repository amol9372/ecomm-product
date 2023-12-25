package org.ecomm.ecommproduct.rest.request.admin;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AddCategory {
  String name;
  Integer parentId;
}
