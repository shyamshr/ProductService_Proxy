package com.example.productservice_proxy.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products/categories")
public class CategoryController {
    @GetMapping("")
    public String getAllCategories(){
        return "returning list of all categories";
    }
    @GetMapping("/{categoryId}")
    public String getSingleProductCategory(@PathVariable("categoryId") Long categoryId){
        return "returning single product category with categoryId: " + categoryId;
    }
}
