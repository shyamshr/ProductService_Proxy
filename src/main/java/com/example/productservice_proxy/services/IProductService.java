package com.example.productservice_proxy.services;

import com.example.productservice_proxy.dtos.ProductDto;

public interface IProductService {
    String getAllProducts();

    String getSingleProduct(Long productId);

    String addNewProduct(ProductDto productDto);

    String updateSingleProduct(Long productId);

    String deleteSingleProduct(Long productId);

    String patchSingleProduct(Long productId);
}
