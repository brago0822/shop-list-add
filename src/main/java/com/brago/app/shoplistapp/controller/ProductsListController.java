package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.service.interfaces.ProductsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api/products-list")
public class ProductsListController {

    @Autowired
    ProductsListService productsListService;

    @GetMapping()
    public ResponseEntity<List<ProductsListDto>> getAllProductsList() {
        return ResponseEntity
                .ok(productsListService.getAllProductsLists());
    }

    @PostMapping()
    public ResponseEntity<ProductsListDto> createProductsList(@RequestBody ProductsListDto productsListDto) {
        return ResponseEntity
                .ok(productsListService.createProductsList(productsListDto));
    }
}
