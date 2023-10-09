package com.example.productservice_proxy.dtos;

import com.example.productservice_proxy.models.Categories;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDto {
    private String title;
    private String description;
    private double price;
    private Categories category;
    private String imageUrl;
}
