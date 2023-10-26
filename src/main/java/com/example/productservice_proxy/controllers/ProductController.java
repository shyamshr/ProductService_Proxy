package com.example.productservice_proxy.controllers;


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
           throw e; //handled by ExceptionAdvices
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
        }catch(Exception e){
            throw e;
        }
    }
    @PostMapping("")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.addNewProduct(getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.CREATED);
            return productsResponseEntity;
        } catch (Exception e) {
           throw e;
        }
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.updateSingleProduct(productId,getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch (Exception e) {
           throw e;
        }
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteSingleProduct(@PathVariable("productId") Long productId){
        try {
            ProductDto productDto = getProductDtoFromProducts(iProductService.deleteSingleProduct(productId));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDto, HttpStatus.OK);
            return productsResponseEntity;
        }catch (Exception e){
           throw e;
        }


    }
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> patchSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = getProductDtoFromProducts(iProductService.patchSingleProduct(productId,getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch (Exception e) {
            throw e;
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
    private Products getProductFromProductDto(ProductDto productDto) {
        Products product = new Products();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(new Categories());
        product.getCategory().setName(productDto.getCategory());
        product.setImageUrl(productDto.getImage());
        return product;
    }

    //local exception handler for product controller
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<String> handleException(Exception e){
        return  new ResponseEntity<>("Invalid Arguments Passed",HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
