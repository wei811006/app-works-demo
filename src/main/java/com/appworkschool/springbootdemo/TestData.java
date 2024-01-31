package com.appworkschool.springbootdemo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@ToString
public class TestData implements Serializable {

    public TestData(String name, String email) {
        this.name = name;
        this.email = email;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    private Integer id;

    private String name;

    private String email;

}
