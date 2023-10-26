package com.example.productservice_proxy.inheritanceExamples.mappedSuperClass;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "mapped_instructors")

public class Instructors extends User {
    private String rating;
}
