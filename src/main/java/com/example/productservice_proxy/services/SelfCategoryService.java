package com.example.productservice_proxy.services;

import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.repositories.category.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelfCategoryService implements IDatabaseCategoryService{
    private CategoryRepo categoryRepo;


    @Autowired
    public SelfCategoryService(CategoryRepo categoryRepo){this.categoryRepo = categoryRepo;}
    @Override
    public List<Categories> getAllCategories() throws NoCategoriesFoundException {
        Optional<List<Categories>> categoriesList = Optional.ofNullable(this.categoryRepo.findAllByIsDeletedFalse());
        if (categoriesList.isEmpty()) {
            throw new NoCategoriesFoundException("No Categories were Found");
        }
        return categoriesList.get();
    }

    @Override
    public List<Product> getProductsInACategory(Long categoryId) throws CategoryNotFoundException, NoProductsFoundException {
        Optional<Categories> category = Optional.ofNullable(this.categoryRepo.findByIdAndIsDeletedFalse(categoryId));
        if(category.isEmpty()){
            throw new CategoryNotFoundException("Category with id "+categoryId+" not found");
        }
        if(category.get().getProductList() == null){
            throw new NoProductsFoundException("No Products were Found for category with id: "+categoryId);
        }
        return category.get().getProductList();
    }

    @Override
    public Categories getCategoryById(Long categoryId) {
        return this.categoryRepo.findByIdAndIsDeletedFalse(categoryId);
    }

    @Override
    public Categories getCategoryByName(String categoryName) {
        return this.categoryRepo.findByNameAndIsDeletedFalse(categoryName);
    }

    @Override
    public Categories addNewCategory(Categories category) {
        return this.categoryRepo.save(category);
    }
}
