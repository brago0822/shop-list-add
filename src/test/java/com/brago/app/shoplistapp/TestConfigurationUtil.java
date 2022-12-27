package com.brago.app.shoplistapp;

import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.model.ShoppingList;

public class TestConfigurationUtil {

    public static ShoppingListDto createSingleShoppingListDto(Long itemNumber){
        final ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.setId(itemNumber);
        shoppingListDto.setName("List " + itemNumber);
        shoppingListDto.setDescription("Description of list " + itemNumber);

        return shoppingListDto;
    }

    public static ShoppingListDto createSingleShoppingListDtoNoId(Long itemNumber){
        final ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.setName("List " + itemNumber);
        shoppingListDto.setDescription("Description of list " + itemNumber);


        return shoppingListDto;
    }

    public static ShoppingList createSingleShoppingList (Long itemNumber){
        final ShoppingList shoppingList = new ShoppingList();
        shoppingList.setId(itemNumber);
        shoppingList.setName("List " + itemNumber);
        shoppingList.setDescription("Description of list " + itemNumber);

        return shoppingList;
    }
}
