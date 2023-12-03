package com.example.productservice_proxy.repositories;

import com.example.productservice_proxy.models.Categories;
import com.example.productservice_proxy.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ProductRepoTest {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Test
    //@Transactional
    void saveCategoryAndProductRepo() {
        Categories category = new Categories();
        category.setName("Electronics123");
        category.setDescription("Electronic products");
        categoryRepo.save(category);

        Product product = new Product();
        product.setTitle("Asus Laptop123");
        product.setDescription("Laptop");
        product.setCategory(category);
        productRepo.save(product);
        //Categories category1 = categoryRepo.findById(category.getId());
        System.out.println("debug");
    }
    @Test
    @Transactional
    void saveCategoryAndProductRepo2() {
        Categories category = new Categories();
        category.setName("Fashion");
        category.setDescription("Fashion products");
        //categoryRepo.save(category);

        Product product = new Product();
        product.setTitle("T-shirt");
        product.setDescription("T-shirt");
        product.setCategory(category);
        productRepo.save(product);
        System.out.println("debug");
    }
    @Test
    @Transactional
    @Rollback(value = false)
    void saveCategoryAndProductRepo3() {
        Categories category = new Categories();
        category.setName("Electronics1234");
        category.setDescription("Electronic products");
        categoryRepo.save(category);

        Product product = new Product();
        product.setTitle("Asus Laptop123");
        product.setDescription("Laptop");
        product.setCategory(category);
        productRepo.save(product);


        product.setTitle("Fake Asus");
        product.getCategory().setName("Fake Electronics");
        entityManager.refresh(product);

        System.out.println(product.getCategory().getName());

    }
    @Test
    @Transactional
    void fetchModeAndFetchTypeTest() {
       // Categories category = categoryRepo.findById(302L);
       // System.out.println(category.getProductList().size());

    }
    @Test
    @Transactional
    void findProductsTest() {
//        Categories category = categoryRepo.findByName("Electronics");
//        List<Product> productList = productRepo.findProductByCategory(category);
//        System.out.println(productList.size());
    }
}