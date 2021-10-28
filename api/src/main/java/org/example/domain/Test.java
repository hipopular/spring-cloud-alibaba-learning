package org.example.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {
    private Long uid;
    private String name;
    private int age;
}
