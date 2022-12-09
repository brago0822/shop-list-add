package com.brago.app.shoplistapp.service.interfaces;

import com.brago.app.shoplistapp.dto.ProductsListDto;

import java.util.List;

public interface ProductsListService {

    List<ProductsListDto> getAllProductsLists();
    ProductsListDto createProductsList(ProductsListDto productsListDto);
}
