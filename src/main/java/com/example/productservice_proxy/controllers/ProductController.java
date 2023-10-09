package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.ProductDto;
import org.springframework.web.bind.annotation.*;

//This controller will always answer to /products.
@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping("")
    public String getAllProducts(){
        return "returning list of all products";
    }
    @GetMapping("/{productId}")
    public String getSingleProduct(@PathVariable("productId") Long productId){
            return "returning single product with productId: " + productId;
    }
    @PostMapping
    public String addNewProduct(@RequestBody ProductDto productDto){
        return "created product " + productDto;
    }
    @PutMapping("/{productId}")
    public String updateSingleProduct(@PathVariable("productId") Long productId){
        return "updating product with productId: " + productId;
    }
    @DeleteMapping("/{productId}")
    public String deleteSingleProduct(@PathVariable("productId") Long productId){
        return "deleting product with productId: " + productId;
    }
    @PatchMapping("/{productId}")
    public String patchSingleProduct(@PathVariable("productId") Long productId){
        return "patching product with productId: " + productId;
    }
}
