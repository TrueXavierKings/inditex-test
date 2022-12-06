package com.inditex.msprices.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Brand {
    @Id
    private Long id;
    private String name;
}
