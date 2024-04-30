package com.example.productservice_proxy.repositories.category;

import com.example.productservice_proxy.models.Categories;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import javax.tools.JavaCompiler;

public interface CategoryElasticSearchRepo extends ElasticsearchRepository<Categories,Long> {

}
