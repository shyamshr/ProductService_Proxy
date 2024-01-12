package com.example.productservice_proxy.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {
    private String sortBy;
    private SortOrder sortOrder;
}
