package com.example.productservice_proxy.repositories;

import com.example.productservice_proxy.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Products,Long> {
    Products save(Products product); // this method is used to save a new product

}
