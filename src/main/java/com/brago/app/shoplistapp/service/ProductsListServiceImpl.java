package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.exception.ErrorType;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
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
    public ProductsListDto getProductsList(Long productsListId) {
        var productsList = productsListRepository
                .findById(productsListId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, "PRODUCTS LIST", productsListId.toString()));
        return productsListMapper.entityToDto(productsList);
    }

    @Override
    public ProductsListDto createProductsList(ProductsListDto productsListDto) {
        ProductsList productsList = productsListMapper.dtoToEntity(productsListDto);
        return productsListMapper.entityToDto(
                productsListRepository.save(productsList)
        );
    }

    @Override
    public ProductsListDto updateProductsList(Long productsListId, ProductsListDto productsListDto) {
        var productsListToUpdate = productsListRepository
                .findById(productsListId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, "PRODUCTS LIST", productsListId.toString()));

        productsListToUpdate.setName(productsListDto.getName());
        productsListToUpdate.setDescription(productsListDto.getDescription());

        return productsListMapper.entityToDto(
                productsListRepository.save(productsListToUpdate)
        );
    }

    @Override
    public void deleteProductsList(Long productsListId) {
        if (!productsListRepository.existsById(productsListId)) {
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, "PRODUCTS LIST", productsListId.toString());
        }
        productsListRepository.deleteById(productsListId);
    }
}
