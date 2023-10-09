package com.example.productservice_proxy.services;

import com.example.productservice_proxy.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

public class ProductService implements IProductService {
    @Override
    public String getAllProducts(){
        return "returning list of all products";
    }

    @Override
    public String getSingleProduct(Long productId){
        return "returning single product with productId: " + productId;
    }

    @Override
    public String addNewProduct(ProductDto productDto){
        return "created product " + productDto;
    }

    @Override
    public String updateSingleProduct(Long productId){
        return "updating product with productId: " + productId;
    }

    @Override
    public String deleteSingleProduct(Long productId){
        return "deleting product with productId: " + productId;
    }
    @Override
    public String patchSingleProduct(Long productId){
        return "patching product with productId: " + productId;
    }
}
