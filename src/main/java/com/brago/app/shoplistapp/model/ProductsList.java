package com.brago.app.shoplistapp.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ProductsList")
@Data
public class ProductsList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

}
