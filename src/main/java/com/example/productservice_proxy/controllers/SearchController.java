package com.example.productservice_proxy.controllers;

import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.dtos.SearchRequestDto;
import com.example.productservice_proxy.services.SearchService;
import com.example.productservice_proxy.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @PostMapping("")
    public List<ProductDto> searchProducts(@RequestBody SearchRequestDto searchRequestDto){
        List<ProductDto> searchResult = CommonUtils.getProductDtoListFromProductList(this.searchService.searchProducts(searchRequestDto.getQuery(),
                        searchRequestDto.getPage(),searchRequestDto.getSizeOfPage(),searchRequestDto.getSortParams()));
        return searchResult;
    }
}
