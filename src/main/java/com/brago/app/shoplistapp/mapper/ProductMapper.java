package com.brago.app.shoplistapp.mapper;

import com.brago.app.shoplistapp.dto.ProductDto;
import com.brago.app.shoplistapp.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDto entityToDto(Product product);
    Product dtoToEntity(ProductDto productDto);

}
