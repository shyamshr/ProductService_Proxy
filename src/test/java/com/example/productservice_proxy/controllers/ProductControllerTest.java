package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.repositories.ProductRepo;
import com.example.productservice_proxy.services.IDatabaseCategoryService;
import com.example.productservice_proxy.services.IProductService;
import com.example.productservice_proxy.services.SelfProductService;
import com.example.productservice_proxy.utils.CommonUtils;
import jdk.jfr.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {

    @MockBean
    IProductService productService;
    @Autowired
    ProductController productController;
    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    public void test_whenGetAllProducts_thenReturnListOfProducts() throws NoProductsFoundException {
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
        // Act
        when(productService.getAllProducts()).thenReturn(productList);
        ResponseEntity<List<ProductDto>> responseEntity = productController.getAllProducts();
        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals(productList.size(),responseEntity.getBody().size());
    }
    @Test
    public void test_whenGetAllProducts_thenThrowsNoProductsFoundException() throws NoProductsFoundException {
        when(productService.getAllProducts()).thenThrow(new NoProductsFoundException("No Products were Found"));
        assertThrows(NoProductsFoundException.class,()->productController.getAllProducts());
    }
    @Test
    public void test_whenGetAllProducts_thenThrowsRuntimeException() throws NoProductsFoundException {
        when(productService.getAllProducts()).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.getAllProducts());
    }
    @Test
    public void test_whenGetSingleProduct_thenReturnProduct() throws ProductNotFoundException {
        // Arrange
        Product p1 = new Product();
        p1.setId(1L);
        p1.setTitle("Test Product 1");
        p1.setCategory(new Categories());
        // Act
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p1);
        ResponseEntity<ProductDto> responseEntity = productController.getSingleProduct(1L);
        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals("Test Product 1",responseEntity.getBody().getTitle());
    }
    @Test
    public void test_whenGetSingleProduct_thenThrowsProductNotFoundException() throws ProductNotFoundException {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new ProductNotFoundException("No Product was found with id"));
        assertThrows(ProductNotFoundException.class,()->productController.getSingleProduct(1L));
    }
    @Test
    public void test_whenGetSingleProduct_thenThrowsRuntimeException() throws ProductNotFoundException {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.getSingleProduct(1L));
    }
    @Test
    public void test_whenAddNewProduct_thenReturnProduct(){
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        Product expectedProduct = CommonUtils.getProductFromProductDto(p1);
        expectedProduct.setId(1L);
        // Act
        when(productService.addNewProduct(any(Product.class))).thenReturn(expectedProduct);
        ResponseEntity<ProductDto> responseEntity = productController.addNewProduct(p1);
        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals("Test Product 1",responseEntity.getBody().getTitle());
        assertEquals("Test category",responseEntity.getBody().getCategory());
    }
    @Test
    public void test_whenAddNewProduct_thenThrowsRuntimeException(){
        when(productService.addNewProduct(any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.addNewProduct(new ProductDto()));
    }
    @Test
    public void test_whenUpdateSingleProduct_thenReturnProduct(){
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        Product expectedProduct = CommonUtils.getProductFromProductDto(p1);
        expectedProduct.setId(1L);

        // Act
        when(productService.updateSingleProduct(any(Long.class),any(Product.class))).thenReturn(expectedProduct);
        ResponseEntity<ProductDto> responseEntity = productController.updateSingleProduct(1L,p1);
        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals("Test Product 1",responseEntity.getBody().getTitle());
        assertEquals("Test category",responseEntity.getBody().getCategory());
    }
    @Test
    public void test_whenUpdateSingleProduct_thenThrowsRuntimeException(){
        when(productService.updateSingleProduct(any(Long.class),any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.updateSingleProduct(1L,new ProductDto()));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenReturnProduct() throws ProductNotFoundException {
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
        ResponseEntity<ProductDto> responseEntity = productController.deleteSingleProduct(1L);
        assertNotNull(responseEntity.getBody());
        assertEquals("Test Product 1",responseEntity.getBody().getTitle());
    }
    @Test
    public void test_whenDeleteSingleProduct_thenThrowsProductNotFoundException() throws ProductNotFoundException {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new ProductNotFoundException("No Product was found with id"));
        assertThrows(ProductNotFoundException.class,()->productController.deleteSingleProduct(1L));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenThrowsRuntimeException() throws ProductNotFoundException {
        when(productService.getSingleProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.deleteSingleProduct(1L));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenThrowsProductNotFoundException2() throws ProductNotFoundException {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Test Product 1");
        p.setCategory(new Categories());
        p.setDeleted(false);
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p);
        doThrow(new ProductNotFoundException("No product was found with id")).when(productService).deleteSingleProduct(any(Long.class));
        assertThrows(ProductNotFoundException.class,()->productController.deleteSingleProduct(1L));
    }
    @Test
    public void test_whenDeleteSingleProduct_thenThrowsRuntimeException2() throws ProductNotFoundException {
        Product p = new Product();
        p.setId(1L);
        p.setTitle("Test Product 1");
        p.setCategory(new Categories());
        p.setDeleted(false);
        when(productService.getSingleProduct(any(Long.class))).thenReturn(p);
        doThrow(new RuntimeException("Something went wrong")).when(productService).deleteSingleProduct(any(Long.class));
        assertThrows(RuntimeException.class,()->productController.deleteSingleProduct(1L));
    }

    @Test
    public void test_whenPatchSingleProduct_thenReturnProduct() throws ProductNotFoundException {
        // Arrange
        ProductDto p1 = new ProductDto();
        p1.setTitle("Test Product 1");
        p1.setCategory("Test category");

        Product expectedProduct = CommonUtils.getProductFromProductDto(p1);
        expectedProduct.setId(1L);
        // Act
        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenReturn(expectedProduct);
        ResponseEntity<ProductDto> responseEntity = productController.patchSingleProduct(1L,p1);
        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals("Test Product 1",responseEntity.getBody().getTitle());
        assertEquals("Test category",responseEntity.getBody().getCategory());
    }
    @Test
    public void test_whenPatchSingleProduct_thenThrowsProductNotFoundException() throws ProductNotFoundException {
        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenThrow(new ProductNotFoundException("No Product was found with id"));
        assertThrows(ProductNotFoundException.class,()->productController.patchSingleProduct(1L,new ProductDto()));
    }
    @Test
    public void test_whenPatchSingleProduct_thenThrowsRuntimeException() throws ProductNotFoundException {
        when(productService.patchSingleProduct(any(Long.class),any(Product.class))).thenThrow(new RuntimeException("Something went wrong"));
        assertThrows(RuntimeException.class,()->productController.patchSingleProduct(1L,new ProductDto()));
    }

    @Test
    @Description("Not working using callRealMethod()")
    @Disabled
    public void test_productControllerCallsProductServiceWithSameID() throws ProductNotFoundException {
        Long id = 1L;
        when(productService.getSingleProduct(id)).thenCallRealMethod();
        productController.getSingleProduct(id);

        verify(productController).getSingleProduct(idCaptor.capture());
        //asserting using assertj
        assertThat(idCaptor.getValue()).isEqualTo(id);

    }

}