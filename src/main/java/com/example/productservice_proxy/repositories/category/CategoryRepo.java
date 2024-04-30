package com.example.productservice_proxy.repositories.category;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Categories,Long> {
    Categories save(Categories category);
    List<Categories> findAllByIsDeletedFalse();

    Categories findByIdAndIsDeletedFalse(long id);
    Categories findByNameAndIsDeletedFalse(String name);
    Categories findCategoriesById(Long categoryId);


}
