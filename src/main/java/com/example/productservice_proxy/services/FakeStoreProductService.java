package com.example.productservice_proxy.services;

import com.example.productservice_proxy.clients.IClientProductDto;
import com.example.productservice_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreRatingDto;
import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Products;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
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
    public Products addNewProduct(ProductDto productDto){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.addNewProduct(getFakeStoreProductDtoFromProductDto(productDto));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public Products updateSingleProduct(Long productId,ProductDto productDto){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.updateSingleProduct(productId,getFakeStoreProductDtoFromProductDto(productDto));
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }

    @Override
    public Products deleteSingleProduct(Long productId){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.deleteSingleProduct(productId);
        return getProductFromFakeStoreProductDto(fakeStoreProductDto);
    }
    @Override
    public Products patchSingleProduct(Long productId,ProductDto productDto){
        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.patchSingleProduct(productId,getFakeStoreProductDtoFromProductDto(productDto));
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
    private FakeStoreProductDto getFakeStoreProductDtoFromProductDto(ProductDto productDto) {
       FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(productDto.getId());
        fakeStoreProductDto.setTitle(productDto.getTitle());
        fakeStoreProductDto.setPrice(productDto.getPrice());
        fakeStoreProductDto.setDescription(productDto.getDescription());
        fakeStoreProductDto.setCategory(productDto.getCategory());
        fakeStoreProductDto.setImage(productDto.getImage());
        if(productDto.getRating() != null) {
            fakeStoreProductDto.setRating(new FakeStoreRatingDto());
            fakeStoreProductDto.getRating().setRate(productDto.getRating().getRate());
            fakeStoreProductDto.getRating().setCount(productDto.getRating().getCount());
        }
        return fakeStoreProductDto;
    }
}
