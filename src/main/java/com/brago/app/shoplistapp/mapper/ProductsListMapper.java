package com.brago.app.shoplistapp.mapper;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.model.ProductsList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductsListMapper {

    ProductsListDto entityToDto(ProductsList productsList);
    ProductsList dtoToEntity(ProductsListDto productsListDto);

}
