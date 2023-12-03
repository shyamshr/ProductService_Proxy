package com.example.productservice_proxy.utils;

import com.example.productservice_proxy.dtos.CategoryDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {
    public static ProductDto getProductDtoFromProducts(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().getName());
        productDto.setImage(product.getImageUrl());
        return productDto;
    }
    public static Product getProductFromProductDto(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        if(productDto.getCategory()!=null) {
            product.setCategory(new Categories());
            product.getCategory().setName(productDto.getCategory());
        }
        product.setImageUrl(productDto.getImage());
        return product;
    }
    public static CategoryDto getCategoryDtoFromCategory(Categories categories){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(categories.getId());
        categoryDto.setName(categories.getName());
        categoryDto.setProductList(getProductTitleListFromProductList(categories.getProductList()));
        return categoryDto;
    }
    public static List<String> getProductTitleListFromProductList(List<Product> productList){
        if(productList == null)
            return null;
        List<String> productTitleList = new ArrayList<>();
        for(Product product: productList){
            productTitleList.add(product.getTitle());
        }
        return productTitleList;
    }
    public static List<ProductDto> getProductDtoListFromProductList(List<Product> productList){
        if(productList == null)
            return null;
        List<ProductDto> productDtoList = new ArrayList<>();
        for(Product product: productList){
            productDtoList.add(getProductDtoFromProducts(product));
        }
        return productDtoList;
    }
    public static List<CategoryDto> getCategoriesDtoListFromCategoriesList(List<Categories> categoriesList){
        if(categoriesList == null)
            return null;
        List<CategoryDto> categoriesDtoList = new ArrayList<>();
        for(Categories categories: categoriesList){
            categoriesDtoList.add(getCategoryDtoFromCategory(categories));
        }
        return categoriesDtoList;
    }
}
