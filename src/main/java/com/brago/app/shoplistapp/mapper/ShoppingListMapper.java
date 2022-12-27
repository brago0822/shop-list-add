package com.brago.app.shoplistapp.mapper;

import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.model.ShoppingList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ShoppingListMapper {

    ShoppingListDto entityToDto(ShoppingList shoppingList);
    ShoppingList dtoToEntity(ShoppingListDto shoppingListDto);

}
