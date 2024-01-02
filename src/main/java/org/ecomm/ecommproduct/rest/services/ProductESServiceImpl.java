package org.ecomm.ecommproduct.rest.services;

import lombok.extern.slf4j.Slf4j;
import org.ecomm.ecommproduct.persistance.entity.EInventory;
import org.ecomm.ecommproduct.persistance.entity.EProduct;
import org.ecomm.ecommproduct.rest.builder.ProductESBuilder;
import org.ecomm.ecommproduct.rest.model.Product;
import org.ecomm.ecommproduct.rest.model.elasticsearch.ESProduct;
import org.ecomm.ecommproduct.rest.request.pagination.PagedResponse;
import org.ecomm.ecommproduct.rest.request.pagination.SearchRequest;
import org.ecomm.ecommproduct.utils.ElasticSearchQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductESServiceImpl implements ProductESService {

  @Autowired ElasticsearchOperations elasticsearchOperations;

  @Override
  public void saveProduct(EProduct product, EInventory inventory) {

    ESProduct esProduct = ProductESBuilder.with(product, inventory);
    log.info("Saving product entity in the index ::: {}", esProduct);
    elasticsearchOperations.save(esProduct);
  }

  @Override
  public PagedResponse<Product> searchProducts(SearchRequest request) {

    NativeQuery searchQuery = ElasticSearchQueryBuilder.createSearchQuery(request);
    SearchHits<ESProduct> searchHits = elasticsearchOperations.search(searchQuery, ESProduct.class);

    List<ESProduct> esProducts =
        searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

    var products = ProductESBuilder.with(esProducts);

    PagedResponse<Product> pagedResponse =
        PagedResponse.<Product>builder()
            .page(request.getPagination().getPageNo())
            .pageSize(request.getPagination().getPageSize())
            .items(products)
            .resultCount((int) searchHits.stream().count())
            .build();

    log.info("Criteria search results are ::: {}", searchHits.stream().count());

    return pagedResponse;
  }
}
