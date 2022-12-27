package com.brago.app.shoplistapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItemRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productId")
    Product product;

    @ManyToOne
    @JoinColumn(name = "shoppingListId")
    ShoppingList shoppingList;

    /**
     * Initial price of the products (Without the discounts)
     */
     private Long initialPrice;

     /**
     * Final price to pay applying discounts
     */
    private Long price;
    private Float quantity;
    private Float discountPercentage;
    private String notes;
    private boolean inCart;

}
