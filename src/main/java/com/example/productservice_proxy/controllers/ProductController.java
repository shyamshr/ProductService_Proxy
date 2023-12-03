package com.example.productservice_proxy.controllers;


import com.example.productservice_proxy.dtos.ProductDto;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.services.IProductService;
import com.example.productservice_proxy.utils.CommonUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

//This controller will always answer to /products.
@RestController
@RequestMapping("/products")
public class ProductController {
    private IProductService iProductService;
public ProductController(IProductService iProductService){
        this.iProductService = iProductService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProductDto>> getAllProducts() throws NoProductsFoundException {
        try {
            List<Product> productList = iProductService.getAllProducts();
            List<ProductDto> productDtoList = CommonUtils.getProductDtoListFromProductList(productList);
            ResponseEntity<List<ProductDto>> responseEntity = new ResponseEntity<>(productDtoList, HttpStatus.OK);
            return responseEntity;
        } catch (NoProductsFoundException e) {
            throw e;
        }catch (Exception e){
            throw e;
        }
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable("productId") Long productId) throws ProductNotFoundException {
        try {
            /*
            //to add headers
            MultiValueMap<String,String> headers= new LinkedMultiValueMap<>();
            headers.add("Content-Type","application/json");
            headers.add("Accept","application/json");
            headers.add("auth-token","hey_provide_access");
            */
            ProductDto productDto = CommonUtils.getProductDtoFromProducts(iProductService.getSingleProduct(productId));
            ResponseEntity<ProductDto> responseEntity = new ResponseEntity<>(productDto,HttpStatus.OK);
            return responseEntity;
        }catch(ProductNotFoundException e){
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }
    @PostMapping("")
    public ResponseEntity<ProductDto> addNewProduct(@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = CommonUtils.getProductDtoFromProducts(iProductService.addNewProduct(CommonUtils.getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.CREATED);
            return productsResponseEntity;
        } catch (Exception e) {
           throw e;
        }
    }
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto){
        try {
            ProductDto productDtoResponse = CommonUtils.getProductDtoFromProducts(iProductService.updateSingleProduct(productId,CommonUtils.getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch (Exception e) {
           throw e;
        }
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteSingleProduct(@PathVariable("productId") Long productId) throws ProductNotFoundException {
        try {
            ProductDto productDto = CommonUtils.getProductDtoFromProducts(iProductService.getSingleProduct(productId));
            iProductService.deleteSingleProduct(productId);
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDto, HttpStatus.OK);
            return productsResponseEntity;
        }catch(ProductNotFoundException e){
            throw e;
        }
        catch (Exception e) {
            throw e;
        }


    }
    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> patchSingleProduct(@PathVariable("productId") Long productId,@RequestBody ProductDto productDto) throws ProductNotFoundException {
        try {
            ProductDto productDtoResponse = CommonUtils.getProductDtoFromProducts(iProductService.patchSingleProduct(productId,CommonUtils.getProductFromProductDto(productDto)));
            ResponseEntity<ProductDto> productsResponseEntity = new ResponseEntity<>(productDtoResponse, HttpStatus.OK);
            return productsResponseEntity;
        } catch(ProductNotFoundException e){
            throw e;
        }
        catch (Exception e) {
            throw e;
        }
    }
}
