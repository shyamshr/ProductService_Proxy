package com.example.productservice_proxy.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private double rate;
    private long count;
}
