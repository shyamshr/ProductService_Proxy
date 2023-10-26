package com.example.productservice_proxy.services;


import com.example.productservice_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Products;


import java.util.ArrayList;
import java.util.List;


//@Service
public class FakeStoreProductService implements IProductService {
    private FakeStoreClient fakeStoreClient;
    public FakeStoreProductService(FakeStoreClient fakeStoreClient){
        this.fakeStoreClient = fakeStoreClient;
    }
    @Override
    public List<Products> getAllProducts(){
        List<FakeStoreProductDto> fakeStoreProductDtoList = this.fakeStoreClient.getAllProducts();
        List<Products> productsList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtoList){
            productsList.add(getProductFromFakeStoreProductDto(fakeStoreProductDto));
        }
        return productsList;
    }
    @Override
    public Products getSingleProduct(Long productId){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.getSingleProduct(productId);
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);

    }
    @Override
    public Products addNewProduct(Products product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.addNewProduct(getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public Products updateSingleProduct(Long productId,Products product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.updateSingleProduct(productId,getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public Products deleteSingleProduct(Long productId){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.deleteSingleProduct(productId);
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }
    @Override
    public Products patchSingleProduct(Long productId,Products product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.patchSingleProduct(productId,getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }
    private Products getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto) {
        Products product = new Products();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Categories());
        product.getCategory().setName(fakeStoreProductDto.getCategory());
        product.setImageUrl(fakeStoreProductDto.getImage());
        return product;
    }
    private FakeStoreProductDto getFakeStoreProductDtoFromProduct(Products product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        fakeStoreProductDto.setImage(product.getImageUrl());
        return fakeStoreProductDto;
    }
}
