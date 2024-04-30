package com.example.productservice_proxy.clients.fakestore.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreRatingDto implements Serializable {
    private double rate;
    private long count;
}
