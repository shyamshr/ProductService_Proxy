package com.example.productservice_proxy.inheritanceExamples.singleClass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "sc_ta")
@DiscriminatorValue(value = "1")
public class TA extends User {
    private String gradYear;
}
