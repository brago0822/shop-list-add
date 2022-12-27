package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.dto.ProductDto;
import com.brago.app.shoplistapp.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDto> getAllProduct() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable("id") Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody @Validated ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable("id") Long productId, @RequestBody @Validated ProductDto productDto) {
        return productService.updateProduct(productId, productDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }

}
