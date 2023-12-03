package com.example.productservice_proxy.services;

import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.models.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts() throws NoProductsFoundException;

    Product getSingleProduct(Long productId) throws ProductNotFoundException;

    Product addNewProduct(Product product);

    Product updateSingleProduct (Long productId, Product product);

    void deleteSingleProduct(Long productId) throws ProductNotFoundException;

    Product patchSingleProduct(Long productId, Product product) throws ProductNotFoundException;
}
