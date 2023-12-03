package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.CategoryDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.services.ICategoryService;
import com.example.productservice_proxy.services.IProductService;
import com.example.productservice_proxy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    private ICategoryService iCategoryService;
    @Autowired
    public CategoryController(ICategoryService iCategoryService){
        this.iCategoryService = iCategoryService;
    }
    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() throws NoCategoriesFoundException {
        try {
            List<Categories> categoryList = this.iCategoryService.getAllCategories();
            List<CategoryDto> categoryDtoList = CommonUtils.getCategoriesDtoListFromCategoriesList(categoryList);
            ResponseEntity<List<CategoryDto>> responseEntity = new ResponseEntity<>(categoryDtoList, HttpStatus.OK);
            return responseEntity;
        } catch (NoCategoriesFoundException e) {
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ProductDto>> getProductsInACategory(@PathVariable("categoryId") Long categoryId) throws CategoryNotFoundException, NoProductsFoundException {
        try {
            List<Product> productList = this.iCategoryService.getProductsInACategory(categoryId);
            List<ProductDto> productDtoList = CommonUtils.getProductDtoListFromProductList(productList);
            ResponseEntity<List<ProductDto>> responseEntity = new ResponseEntity<>(productDtoList, HttpStatus.OK);
            return responseEntity;
        }catch (CategoryNotFoundException e){
            throw e;
        }
        catch (NoProductsFoundException e) {
            throw e;
        }
        catch (Exception e) {
            throw e; //handled by ExceptionAdvices
        }
    }

}
