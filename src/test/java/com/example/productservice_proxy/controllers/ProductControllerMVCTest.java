package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.services.IProductService;
import com.example.productservice_proxy.utils.CommonUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;

@WebMvcTest(ProductController.class)
public class ProductControllerMVCTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IProductService productService;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void test_whenGetAllProducts_thenStatusOkAndProductList() throws Exception {
        // Arrange
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
        List<ProductDto> productDtoList = CommonUtils.getProductDtoListFromProductList(productList);
        // Act
        when(productService.getAllProducts()).thenReturn(productList);
        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(productDtoList)))
                //matching directly from json without converting
                .andExpect(jsonPath("$[0].title" , is("Test Product 1") ));

    }
    @Test
    public void test_whenGetAllProducts_thenStatusNotFound() throws Exception {
        when(productService.getAllProducts()).thenThrow(new NoProductsFoundException("No Products were found"));
        mockMvc.perform(get("/products"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("No Products were found"));

    }
    @Test
    public void test_whenGetAllProducts_thenStatusInternalServerError() throws Exception {
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get("/products"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenGetSingleProduct_thenStatusOKAndProduct() throws Exception {
        // Arrange
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Test Product 1");
        p1.setCategory(new Categories());
        // Act
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p1);
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CommonUtils.getProductDtoFromProducts(p1))));

    }
    @Test
    public void test_whenGetSingleProduct_thenStatusNotFound() throws Exception {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new ProductNotFoundException("Product with id: 1 was not found"));
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id: 1 was not found"));

    }
    @Test
    public void test_whenGetSingleProduct_thenStatusInternalServerError() throws Exception {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(get("/products/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenAddNewProduct_thenStatusCreatedAndProduct() throws Exception {
        ProductDto productToCreate = new ProductDto();
        productToCreate.setTitle("Test Product 1");
        productToCreate.setCategory("Test Category 1");
        productToCreate.setDescription("Test Description 1");
        productToCreate.setPrice(0.0);
        productToCreate.setImage("Test Image 1");

        Product expectedProduct = new Product();
        expectedProduct.setId(1L);
        expectedProduct.setTitle("Test Product 1");
        expectedProduct.setCategory(new Categories());
        expectedProduct.setDescription("Test Description 1");
        expectedProduct.setPrice(0.0);
        expectedProduct.setImageUrl("Test Image 1");

        when(productService.addNewProduct(any(Product.class))).thenReturn(expectedProduct);
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(CommonUtils.getProductDtoFromProducts(expectedProduct))));

    }
    @Test
    public void test_whenAddNewProduct_thenStatusInternalServerError() throws Exception {
        ProductDto productToCreate = new ProductDto();
        productToCreate.setTitle("Test Product 1");
        productToCreate.setCategory("Test Category 1");
        productToCreate.setDescription("Test Description 1");
        productToCreate.setPrice(0.0);
        productToCreate.setImage("Test Image 1");
        when(productService.addNewProduct(any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(post("/products")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(productToCreate)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenUpdateSingleProduct_thenStatusOkAndProduct() throws Exception {
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        Product expectedProduct = CommonUtils.getProductFromProductDto(p1);
        expectedProduct.setId(1L);
        // Act
        when(productService.updateSingleProduct(any(Long.class),any(Product.class))).thenReturn(expectedProduct);
        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CommonUtils.getProductDtoFromProducts(expectedProduct))));
    }
    @Test
    public void test_whenUpdateSingleProduct_thenStatusInternalServerError() throws Exception {
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        // Act
        when(productService.updateSingleProduct(any(Long.class),any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(put("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenReturnProduct() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Test Product 1");
        p.setCategory(new Categories());
        p.setDeleted(false);
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p);
        doAnswer(invocationOnMock -> {
            p.setDeleted(true);
            return null;
        }).when(productService).deleteSingleProduct(any(Long.class));
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CommonUtils.getProductDtoFromProducts(p))));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenStatusNotFound() throws Exception {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new ProductNotFoundException("Product with id: 1 was not found"));
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id: 1 was not found"));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenStatusInternalServerError() throws Exception {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenStatusNotFound2() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Test Product 1");
        p.setCategory(new Categories());
        p.setDeleted(false);
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p);
        doThrow(new ProductNotFoundException("Product with id: 1 was not found")).when(productService).deleteSingleProduct(any(Long.class));
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id: 1 was not found"));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenStatusInternalServerError2() throws Exception {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Test Product 1");
        p.setCategory(new Categories());
        p.setDeleted(false);
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p);
        doThrow(new RuntimeException("Something went wrong")).when(productService).deleteSingleProduct(any(Long.class));
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }
    @Test
    public void test_whenPatchSingleProduct_thenStatusOkAndProduct() throws Exception {
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        Product expectedProduct = CommonUtils.getProductFromProductDto(p1);
        expectedProduct.setId(1L);

        // Act
        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenReturn(expectedProduct);
        mockMvc.perform(patch("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(CommonUtils.getProductDtoFromProducts(expectedProduct))));
    }
    @Test
    public void test_whenPatchSingleProduct_thenStatusNotFound() throws Exception {
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenThrow(new ProductNotFoundException("Product with id: 1 was not found"));
        mockMvc.perform(patch("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product with id: 1 was not found"));
    }
    @Test
    public void test_whenPatchSingleProduct_thenStatusInternalServerError() throws Exception {
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        mockMvc.perform(patch("/products/1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(p1)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Something went wrong"));
    }


    

}
