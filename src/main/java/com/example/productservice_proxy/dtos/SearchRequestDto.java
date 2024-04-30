package com.example.productservice_proxy.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private int sizeOfPage;
    private int offset;
}
