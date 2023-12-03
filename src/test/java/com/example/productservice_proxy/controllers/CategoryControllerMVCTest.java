package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.CategoryDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.CategoryNotFoundException;
import com.example.productservice_proxy.exceptions.NoCategoriesFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.services.IDatabaseCategoryService;
import com.example.productservice_proxy.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IDatabaseCategoryService categoryService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_whenGetAllCategories_thenStatusOkAndListOfCategories() throws Exception {

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
        mockMvc.perform(get("/products/categories"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CommonUtils.getCategoriesDtoListFromCategoriesList(categoriesList))));
    }
    @Test
    public void test_whenGetAllCategories_thenStatusNotFound() throws Exception {
        when(categoryService.getAllCategories()).thenThrow(new NoCategoriesFoundException("No Categories were Found"));
        mockMvc.perform(get("/products/categories"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Categories were Found"));
    }
    @Test
    public void test_whenGetAllCategories_thenStatusInternalServerError() throws Exception {
        when(categoryService.getAllCategories()).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get("/products/categories"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenGetProductsInACategory_thenStatusOkAndListOfProducts() throws Exception {
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
        mockMvc.perform(get("/products/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(CommonUtils.getProductDtoListFromProductList(productList))));
    }
    @Test
    public void test_whenGetProductsInACategory_thenStatusNotFound() throws Exception {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new CategoryNotFoundException("No Category was found with id: 1"));
        mockMvc.perform(get("/products/categories/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Category was found with id: 1"));
    }
    @Test
    public void test_whenGetProductsInACategory_thenStatusNotFound2() throws Exception {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new NoProductsFoundException("No Products were found in Category with id: 1"));
        mockMvc.perform(get("/products/categories/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Products were found in Category with id: 1"));
    }
    @Test
    public void test_whenGetProductsInACategory_thenStatusInternalServerError() throws Exception {
        when(categoryService.getProductsInACategory(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get("/products/categories/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
}
