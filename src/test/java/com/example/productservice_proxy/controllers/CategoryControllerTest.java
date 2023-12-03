package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.CategoryDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.services.IDatabaseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryControllerTest {
    @MockBean
    private IDatabaseCategoryService categoryService;
    @Autowired
    private CategoryController categoryController;


    @Test
    public void test_whenGetAllCategories_thenReturnsListOfCategories() throws NoCategoriesFoundException {

        List<Categories> categoriesList = new ArrayList<>();
        Categories c1 = new Categories();
        c1.setId(1L);
        c1.setName("c1");
        categoriesList.add(c1);

        Categories c2 = new Categories();
        c2.setId(2L);
        c2.setName("c2");
        categoriesList.add(c2);

        Categories c3 = new Categories();
        c3.setId(3L);
        c3.setName("c3");
        categoriesList.add(c3);


        when(categoryService.getAllCategories()).thenReturn(categoriesList);
        ResponseEntity<List<CategoryDto>> responseEntity = categoryController.getAllCategories();
        assertNotNull(responseEntity.getBody());
        assertEquals(categoriesList.size(),responseEntity.getBody().size());
        
    }
    @Test
    public void test_whenGetAllCategories_thenThrowsNoCategoriesFoundException() throws NoCategoriesFoundException {
        when(categoryService.getAllCategories()).thenThrow(new NoCategoriesFoundException("No Categories were Found"));
        assertThrows(NoCategoriesFoundException.class,()->categoryController.getAllCategories());
    }
    @Test
    public void test_whenGetAllCategories_thenThrowsException() throws NoCategoriesFoundException {
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->categoryController.getAllCategories());
    }

    @Test
    public void test_whenGetProductsInACategory_thenReturnsListOfProducts() throws CategoryNotFoundException, NoProductsFoundException {
        List<Product> productList = new ArrayList<>();
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Test Product 1");
        p1.setCategory(new Categories());
        productList.add(p1);
        Product p2 = new Product();
        p2.setId(2L);
        p2.setTitle("Test Product 2");
        p2.setCategory(new Categories());
        productList.add(p2);
        Product p3 = new Product();
        p3.setId(3L);
        p3.setTitle("Test Product 3");
        p3.setCategory(new Categories());
        productList.add(p3);
        when(categoryService.getProductsInACategory(any(Long.class))).thenReturn(productList);
        ResponseEntity<List<ProductDto>> responseEntity = categoryController.getProductsInACategory(1L);
        assertNotNull(responseEntity.getBody());
        assertEquals(productList.size(),responseEntity.getBody().size());
    }
    @Test
    public void test_whenGetProductsInACategory_thenThrowsCategoryNotFoundException() throws CategoryNotFoundException, NoProductsFoundException {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new CategoryNotFoundException("No Category was found with id: 1"));
        assertThrows(CategoryNotFoundException.class,()->categoryController.getProductsInACategory(1L));
    }
    @Test
    public void test_whenGetProductsInACategory_thenThrowsNoProductsFoundException() throws CategoryNotFoundException, NoProductsFoundException {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new NoProductsFoundException("No Products were found in category with id: 1"));
        assertThrows(NoProductsFoundException.class,()->categoryController.getProductsInACategory(1L));
    }
    @Test
    public void test_whenGetProductsInACategory_thenThrowsException() throws CategoryNotFoundException, NoProductsFoundException {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->categoryController.getProductsInACategory(1L));
    }

}