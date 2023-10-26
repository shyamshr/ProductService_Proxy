package com.example.productservice_proxy.services;

import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Products;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {
    List<Products> getAllProducts();

    Products getSingleProduct(Long productId);

    Products addNewProduct(Products product);

    Products updateSingleProduct(Long productId,Products product);

    Products deleteSingleProduct(Long productId);

    Products patchSingleProduct(Long productId,Products product);
}
