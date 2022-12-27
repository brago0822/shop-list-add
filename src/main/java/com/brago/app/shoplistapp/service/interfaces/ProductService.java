package com.brago.app.shoplistapp.service.interfaces;

import com.brago.app.shoplistapp.dto.ProductDto;
import java.util.List;

public interface ProductService {

    List<ProductDto> getAllProducts();
    ProductDto getProduct(Long productId);
    ProductDto createProduct(ProductDto productDto);
    ProductDto updateProduct(Long productId, ProductDto productDto);
    void deleteProduct(Long productId);
}
