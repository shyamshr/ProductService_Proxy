package com.example.productservice_proxy.dtos;

import com.example.productservice_proxy.models.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
public class CategoryDto {
    private long id;
    private String name;
    private String description;
    private List<String> productList;
}
