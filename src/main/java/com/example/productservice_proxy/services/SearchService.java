package com.example.productservice_proxy.services;

import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.models.SortOrder;
import com.example.productservice_proxy.models.SortParam;
import com.example.productservice_proxy.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ProductRepo productRepo;
    public List<Product> searchProducts(String query, int page, int sizeOfPage, List<SortParam> sortParams){
        Sort sort = Sort.by(sortParams.get(0).getSortBy());
        if(sortParams.get(0).getSortOrder().equals(SortOrder.DESC)){
            sort = sort.descending();
        }
        for(int i = 1; i<sortParams.size(); i++){
            if(sortParams.get(i).getSortOrder().equals(SortOrder.DESC)){
                sort = sort.and(Sort.by(sortParams.get(i).getSortBy()).descending());
            }else{
                sort = sort.and(Sort.by(sortParams.get(i).getSortBy()));
            }
        }
        return this.productRepo.findAllByTitleAndIsDeletedFalse(query, PageRequest.of(page,sizeOfPage,sort));
    }
}
