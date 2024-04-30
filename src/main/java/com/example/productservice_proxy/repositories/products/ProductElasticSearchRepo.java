package com.example.productservice_proxy.repositories.products;

import com.example.productservice_proxy.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface ProductElasticSearchRepo extends ElasticsearchRepository<Product,Long> {
    @Query("{\"bool\": {\"must\": [{\"match\": {\"title\": \"?0\"}},{\"match\": {\"description\": \"?0\"}}]}}")
    Page<Product> findProductByTitleOrDescContainingPhrase(String phrase, Pageable pageable);

}
