package com.example.productservice_proxy.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Products extends BaseModel{
    private String title;
    private String description;
    private double price;
    private Categories category;
    private String imageUrl;
}
