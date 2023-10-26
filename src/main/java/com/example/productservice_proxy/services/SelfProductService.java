package com.example.productservice_proxy.services;

import com.example.productservice_proxy.models.Products;
import com.example.productservice_proxy.repositories.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SelfProductService implements IProductService {
    private ProductRepo productRepo;
    public SelfProductService(ProductRepo productRepo){
        this.productRepo = productRepo;
    }
    @Override
    public List<Products> getAllProducts() {
        return null;
    }

    @Override
    public Products getSingleProduct(Long productId) {
        return null;
    }

    @Override
    public Products addNewProduct(Products product) {
        return this.productRepo.save(product);
    }

    @Override
    public Products updateSingleProduct(Long productId, Products product) {
        return null;
    }

    @Override
    public Products deleteSingleProduct(Long productId) {
        return null;
    }
    @Override
    public Products patchSingleProduct(Long productId,Products product){
        return null;
    }
}
