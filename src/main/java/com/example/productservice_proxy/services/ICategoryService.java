package com.example.productservice_proxy.services;

import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;

import java.util.List;

public interface ICategoryService {
    public List<Categories> getAllCategories() throws NoCategoriesFoundException;
    public List<Product> getProductsInACategory(Long categoryId) throws CategoryNotFoundException, NoProductsFoundException;
}
