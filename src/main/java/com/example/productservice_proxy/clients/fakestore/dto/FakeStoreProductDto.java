package com.example.productservice_proxy.clients.fakestore.dto;

import com.example.productservice_proxy.clients.IClientProductDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class FakeStoreProductDto implements IClientProductDto, Serializable {
    private long id;
    private String title;
    private String description;
    private double price;
    private String category;
    private String image;
    private FakeStoreRatingDto rating;
}
