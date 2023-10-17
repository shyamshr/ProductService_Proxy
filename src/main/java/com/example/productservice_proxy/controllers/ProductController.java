package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Products;
import com.example.productservice_proxy.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//This controller will always answer to /products.
@RestController
@RequestMapping("/products")
public class ProductController {
    private IProductService iProductService;
public ProductController(IProductService iProductService){
        this.iProductService = iProductService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        try {
            List<Products> productsList = iProductService.getAllProducts();
            List<ProductDto> productDtoList = new ArrayList<>();
            for (Products product : productsList) {
                productDtoList.add(getProductDtoFromProducts(product));
            }
            ResponseEntity<List<ProductDto>> responseEntity = new ResponseEntity<>(productDtoList, HttpStatus.OK);
            return responseEntity;
        } catch (Exception e) {
            ResponseEntity<List<ProductDto>> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return responseEntity;
        }
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable("productId") Long productId){
    try {
        MultiValueMap<String,String> headers= new LinkedMultiValueMap<>();
        headers.add("Content-Type","application/json");
        headers.add("Accept","application/json");
        headers.add("auth-token","hey_provide_access");
        ProductDto productDto = getProductDtoFromProducts(iProductService.getSingleProduct(productId));
        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(productDto, headers,HttpStatus.OK);
        return responseEntity;
    }catch(NullPointerException e){
        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return responseEntity;
    }catch (Exception e) {
        ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return responseEntity;
    }
    }
    @PostMapping("")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.addNewProduct(productDto));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.CREATED);
            return productsResponseEntity;
        } catch (Exception e) {
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return productsResponseEntity;
        }
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.updateSingleProduct(productId,productDto));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch (Exception e) {
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return productsResponseEntity;
        }
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteSingleProduct(@PathVariable("productId") Long productId){
        try {
            ProductDto productDto = getProductDtoFromProducts(iProductService.deleteSingleProduct(productId));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDto, HttpStatus.OK);
            return productsResponseEntity;
        }catch (Exception e){
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return productsResponseEntity;
        }


    }
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> patchSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.patchSingleProduct(productId,productDto));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch (Exception e) {
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            return productsResponseEntity;
        }
    }
    private ProductDto getProductDtoFromProducts(Products product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setPrice(product.getPrice());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory().getName());
        productDto.setImage(product.getImageUrl());
        return productDto;
    }
}
