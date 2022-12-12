package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.service.interfaces.ProductsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/products-list")
public class ProductsListController {

    @Autowired
    ProductsListService productsListService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductsListDto> getAllProductsList() {
        return productsListService.getAllProductsLists();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductsListDto getProductsList(@PathVariable("id") Long productsListId) {
        return productsListService.getProductsList(productsListId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductsListDto createProductsList(@RequestBody @Validated ProductsListDto productsListDto) {
        return productsListService.createProductsList(productsListDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductsListDto updateProductsList(@PathVariable("id") Long productsListId,
                                              @RequestBody @Validated ProductsListDto productsListDto) {
        return productsListService.updateProductsList(productsListId, productsListDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductsList(@PathVariable("id") Long productsListId) {
        productsListService.deleteProductsList(productsListId);
    }

}
