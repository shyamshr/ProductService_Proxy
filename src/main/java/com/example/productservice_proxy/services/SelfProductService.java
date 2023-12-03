package com.example.productservice_proxy.services;

import com.example.productservice_proxy.exceptions.NoProductsFoundException;
import com.example.productservice_proxy.exceptions.ProductNotFoundException;
import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import com.example.productservice_proxy.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SelfProductService implements IProductService {
    private ProductRepo productRepo;
    private IDatabaseCategoryService databaseCategoryService;

    @Autowired
    public SelfProductService(ProductRepo productRepo, IDatabaseCategoryService databaseCategoryService){
        this.productRepo = productRepo;
        this.databaseCategoryService = databaseCategoryService;
    }
    @Override
    public List<Product> getAllProducts() throws NoProductsFoundException {
        Optional<List<Product>> productList = Optional.ofNullable(this.productRepo.findAllByIsDeletedFalse());
        if(productList.isEmpty())
            throw new NoProductsFoundException("No Products were Found");
        return this.productRepo.findAllByIsDeletedFalse();
    }

    @Override
    public Product getSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> product = Optional.ofNullable(this.productRepo.findProductByIdAndIsDeletedFalse(productId));
        if(product.isEmpty())
            throw new ProductNotFoundException("No Product was found with id: "+productId);
        return this.productRepo.findProductByIdAndIsDeletedFalse(productId);
    }

    @Override
    public Product addNewProduct(Product product) {
        Categories categories = this.databaseCategoryService.getCategoryByName(product.getCategory().getName());
        if(categories != null){
            product.setCategory(categories);
        }
        else{
            product.setCategory(this.databaseCategoryService.addNewCategory(product.getCategory()));
        }

        return this.productRepo.save(product);
    }

    @Override
    public Product updateSingleProduct(Long productId, Product product){
        product.setId(productId);
        Categories categories = this.databaseCategoryService.getCategoryByName(product.getCategory().getName());
        if(categories != null){
            product.setCategory(categories);
        }
        else{
            product.setCategory(this.databaseCategoryService.addNewCategory(product.getCategory()));
        }
        return this.productRepo.save(product);
    }

    @Override
    public void deleteSingleProduct(Long productId) throws ProductNotFoundException {
        Optional<Product> product = Optional.ofNullable(this.productRepo.findProductById(productId));
        if(product.isEmpty())
            throw new ProductNotFoundException("No Product was found with id: "+productId);
        product.get().setDeleted(true);
        this.productRepo.save(product.get());
    }
    @Override
    public Product patchSingleProduct(Long productId, Product product) throws ProductNotFoundException{
        Optional<Product> existingProduct = Optional.ofNullable(this.productRepo.findProductByIdAndIsDeletedFalse(productId));
        //got the saved product
        if(existingProduct.isEmpty())
            throw new ProductNotFoundException("No Product was found with id: "+productId);
        Product patchedProduct = this.convertExistingProductToPatchedProduct(existingProduct.get(), product);
        return this.productRepo.save(patchedProduct);
    }
    public List<Product> getProductsInACategory(Long categoryId){
        return this.productRepo.findProductByCategoryId(categoryId);
    }
    private Product convertExistingProductToPatchedProduct(Product existingProduct, Product product){
        if(product.getTitle() != null){
            existingProduct.setTitle(product.getTitle());
        }
        if(product.getDescription() != null){
            existingProduct.setDescription(product.getDescription());
        }
        if(product.getPrice() != 0){
            existingProduct.setPrice(product.getPrice());
        }
        if(product.getImageUrl() != null){
            existingProduct.setImageUrl(product.getImageUrl());
        }
        if(product.getCategory() != null){
            Categories category = this.databaseCategoryService.getCategoryByName(product.getCategory().getName());
            if(category != null){
                existingProduct.setCategory(category);
            }
            else{
                existingProduct.setCategory(this.databaseCategoryService.addNewCategory(product.getCategory()));
            }
        }
        return existingProduct;
    }
}
