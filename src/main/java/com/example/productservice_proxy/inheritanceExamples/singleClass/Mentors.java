package com.example.productservice_proxy.inheritanceExamples.singleClass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sc_mentors")
@DiscriminatorValue(value = "2")
public class Mentors extends User {
    private String company;
}
