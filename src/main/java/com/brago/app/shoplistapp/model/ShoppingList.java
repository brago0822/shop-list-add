package com.brago.app.shoplistapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "ShoppingList")
@Getter
@Setter
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToMany(mappedBy = "shoppingList", fetch = FetchType.LAZY)
    private Set<ItemRecord> registrations;

}
