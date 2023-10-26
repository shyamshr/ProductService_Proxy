package com.example.productservice_proxy.inheritanceExamples.tablePerClass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "tpc_ta")
public class TA extends User{
    private String gradYear;
}
