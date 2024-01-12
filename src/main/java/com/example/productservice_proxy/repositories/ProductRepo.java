package com.example.productservice_proxy.repositories;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    Product save(Product product); // this method is used to save a new product
    Product findProductById(long id); // this method is used to find a product by id
    Product findProductByIdAndIsDeletedFalse(long id); // this method is used to find a product by id

    List<Product> findAllByIsDeletedFalse(); // this method is used to find all products
    List<Product> findProductByCategoryId(long categoryId); // this method is used to find a product by category id
    void deleteById(long id); // this method is used to delete a product by id

    List<Product> findProductByPriceBetween(double minPrice, double maxPrice); // this method is used to find a product by price range (minPrice and maxPrice

    List<Product> findProductByCategory(Categories category); // this method is used to find a product by category
    List<Product> findByTitle(String title); // this method is used to find a product by title
    List<Product> findAllByTitleAndIsDeletedFalse(String title, Pageable pageable);
}
