package com.example.productservice_proxy.clients.fakestore.client;

import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreProductDto;
import com.example.productservice_proxy.clients.fakestore.dto.FakeStoreRatingDto;
import com.example.productservice_proxy.dtos.ProductDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;


@Component
public class FakeStoreClient{
    private RestTemplateBuilder restTemplateBuilder;
    public FakeStoreClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }
    public List<FakeStoreProductDto> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products";
        FakeStoreProductDto[] fakeStoreProductDto = restTemplate.getForEntity(url, FakeStoreProductDto[].class).getBody();
        return Arrays.asList(fakeStoreProductDto);
    }
    public FakeStoreProductDto getSingleProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products/{productId}";
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity(url, FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }
    public FakeStoreProductDto addNewProduct(FakeStoreProductDto fakeStoreProductDto){
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = "https://fakestoreapi.com/products";
        FakeStoreProductDto fakeStoreProductDtoPosted= restTemplate.postForEntity(url, fakeStoreProductDto, FakeStoreProductDto.class).getBody();
        return fakeStoreProductDtoPosted;
    }
    public FakeStoreProductDto patchSingleProduct(Long productId,FakeStoreProductDto fakeStoreProductDto){
        String url = "https://fakestoreapi.com/products/{productId}";
        FakeStoreProductDto fakeStoreProductDtoPatched= this.requestForEntity(HttpMethod.PATCH,url, fakeStoreProductDto, FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDtoPatched;
    }
    public FakeStoreProductDto updateSingleProduct(Long productId,FakeStoreProductDto fakeStoreProductDto){
        String url = "https://fakestoreapi.com/products/{productId}";
        FakeStoreProductDto fakeStoreProductDtoUpdated= this.requestForEntity(HttpMethod.PUT,url, fakeStoreProductDto, FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDtoUpdated;
    }
    public FakeStoreProductDto deleteSingleProduct(Long productId){
        String url = "https://fakestoreapi.com/products/{productId}";
        FakeStoreProductDto fakeStoreProductDto = this.requestForEntity(HttpMethod.DELETE,url, null,FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }
    public <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod,String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();
        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


}
