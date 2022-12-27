package com.brago.app.shoplistapp;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.model.ProductsList;

public class TestConfigurationUtil {

    public static ProductsListDto createSingleProductsListDto(Long itemNumber){
        final ProductsListDto productsListDto = new ProductsListDto();
        productsListDto.setId(itemNumber);
        productsListDto.setName("List " + itemNumber);
        productsListDto.setDescription("Description of list " + itemNumber);

        return productsListDto;
    }

    public static ProductsListDto createSingleProductsListDtoNoId(Long itemNumber){
        final ProductsListDto productsListDto = new ProductsListDto();
        productsListDto.setName("List " + itemNumber);
        productsListDto.setDescription("Description of list " + itemNumber);


        return productsListDto;
    }

    public static ProductsList createSingleProductsList (Long itemNumber){
        final ProductsList productsList = new ProductsList();
        productsList.setId(itemNumber);
        productsList.setName("List " + itemNumber);
        productsList.setDescription("Description of list " + itemNumber);

        return productsList;
    }
}
