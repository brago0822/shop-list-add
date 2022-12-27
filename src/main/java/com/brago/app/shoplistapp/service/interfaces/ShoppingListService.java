package com.brago.app.shoplistapp.service.interfaces;

import com.brago.app.shoplistapp.dto.ShoppingListDto;

import java.util.List;

public interface ShoppingListService {

    List<ShoppingListDto> getAllShoppingLists();
    ShoppingListDto getShoppingList(Long shoppingListId);
    ShoppingListDto createShoppingList(ShoppingListDto shoppingListDto);
    ShoppingListDto updateShoppingList(Long shoppingListId, ShoppingListDto shoppingListDto);
    void deleteShoppingList(Long shoppingListId);
}
