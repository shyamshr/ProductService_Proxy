package com.example.productservice_proxy.inheritanceExamples.singleClass;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "sc_instructors")
@DiscriminatorValue(value = "3")
public class Instructors extends User {
    private String rating;
}
