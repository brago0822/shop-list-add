package com.brago.app.shoplistapp.dto;

import com.brago.app.shoplistapp.model.Product;
import com.brago.app.shoplistapp.model.ShoppingList;
import lombok.Data;

@Data
public class ItemRecordDto {
    private Long id;

    Product product;

    ShoppingList shoppingList;

    private Long initialPrice;
    private Long price;
    private Float quantity;
    private Float discountPercentage;
    private String notes;
    private boolean inCart;
}
