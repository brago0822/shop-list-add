package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.mapper.ProductsListMapper;
import com.brago.app.shoplistapp.model.ProductsList;
import com.brago.app.shoplistapp.repository.ProductsListRepository;
import com.brago.app.shoplistapp.service.interfaces.ProductsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductsListServiceImpl implements ProductsListService {

    @Autowired
    ProductsListRepository productsListRepository;
    @Autowired
    ProductsListMapper productsListMapper;

    @Override
    public List<ProductsListDto> getAllProductsLists() {
        return productsListRepository.findAll()
                .stream()
                .map(productsList -> productsListMapper.entityToDto(productsList)).collect(Collectors.toList());
    }

    @Override
    public ProductsListDto createProductsList(ProductsListDto productsListDto) {
        ProductsList productsList = productsListMapper.dtoToEntity(productsListDto);
        return productsListMapper.entityToDto(
                productsListRepository.save(productsList)
        );
    }
}
