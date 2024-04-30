package com.example.productservice_proxy.services;


import com.example.productservice_proxy.clients.fakestore.client.FakeStoreClient;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
//@Primary
public class FakeStoreProductService implements IProductService {
    private FakeStoreClient fakeStoreClient;
    private RedisTemplate<Long, Object> redisTemplate;
    public FakeStoreProductService(FakeStoreClient fakeStoreClient, RedisTemplate<Long, Object> redisTemplate){
        this.fakeStoreClient = fakeStoreClient;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Page<Product> getProductsByTitle(String query, int sizeOfPage, int offset) {
        return null;
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
        FakeStoreProductDto redisFakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get(productId,"PRODUCTS");
        if(redisFakeStoreProductDto != null){
            return getProductFromFakeStoreProductDto(redisFakeStoreProductDto);
        }

        FakeStoreProductDto fakeStoreProductDto = this.fakeStoreClient.getSingleProduct(productId);
       redisTemplate.opsForHash().put(productId,"PRODUCTS",fakeStoreProductDto);

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
