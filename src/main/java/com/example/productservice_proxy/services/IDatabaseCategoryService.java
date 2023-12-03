package com.example.productservice_proxy.services;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;

import java.util.List;

public interface IDatabaseCategoryService extends ICategoryService{
    public Categories getCategoryById(Long categoryId);
    public Categories getCategoryByName(String categoryName);
    public Categories addNewCategory(Categories category);
}
