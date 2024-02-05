package com.example.productservice_proxy.services;


import com.example.productservice_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
@Primary
public class FakeStoreProductService implements IProductService {
    private FakeStoreClient fakeStoreClient;
    public FakeStoreProductService(FakeStoreClient fakeStoreClient){
        this.fakeStoreClient = fakeStoreClient;
    }
    @Override
    public List<Product> getAllProducts(){
        List<FakeStoreProductDto> fakeStoreProductDtoList = this.fakeStoreClient.getAllProducts();
        List<Product> productList = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto: fakeStoreProductDtoList){
            productList.add(getProductFromFakeStoreProductDto(fakeStoreProductDto));
        }
        return productList;
    }
    @Override
    public Product getSingleProduct(Long productId){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.getSingleProduct(productId);
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);

    }
    @Override
    public Product addNewProduct(Product product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.addNewProduct(getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public Product updateSingleProduct(Long productId, Product product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.updateSingleProduct(productId,getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public void deleteSingleProduct(Long productId){this.fakeStoreClient.deleteSingleProduct(productId);
    }
    @Override
    public Product patchSingleProduct(Long productId, Product product){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.patchSingleProduct(productId,getFakeStoreProductDtoFromProduct(product));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }
    private Product getProductFromFakeStoreProductDto(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setCategory(new Categories());
        product.getCategory().setName(fakeStoreProductDto.getCategory());
        product.setImageUrl(fakeStoreProductDto.getImage());
        return product;
    }
    private FakeStoreProductDto getFakeStoreProductDtoFromProduct(Product product) {
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
