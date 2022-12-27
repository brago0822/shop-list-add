package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.dto.ProductDto;
import com.brago.app.shoplistapp.exception.ErrorType;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
import com.brago.app.shoplistapp.mapper.ProductMapper;
import com.brago.app.shoplistapp.model.Product;
import com.brago.app.shoplistapp.repository.ProductRepository;
import com.brago.app.shoplistapp.service.interfaces.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    public static final String PRODUCT = "PRODUCT";
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::entityToDto)
                .toList();
    }

    @Override
    public ProductDto getProduct(Long productId) {
        var product = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCT, productId.toString()));
        return productMapper.entityToDto(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productMapper.dtoToEntity(productDto);
        return productMapper.entityToDto(
                productRepository.save(product)
        );
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        var productToUpdate = productRepository
                .findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCT, productId.toString()));

        productToUpdate.setName(productDto.getName());
        productToUpdate.setNotes(productDto.getNotes());

        return productMapper.entityToDto(
                productRepository.save(productToUpdate)
        );
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCT, productId.toString());
        }
        productRepository.deleteById(productId);
    }
}
