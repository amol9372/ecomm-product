package org.ecomm.ecommproduct.rest.services;

import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.rest.model.Product;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductESService {
    
    void saveProduct(EProduct product);


    PagedResponse<Product> searchProducts(SearchRequest request);
}
