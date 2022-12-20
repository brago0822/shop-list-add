package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.TestConfigurationUtil;
import com.brago.app.shoplistapp.dto.ProductsListDto;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
import com.brago.app.shoplistapp.mapper.ProductsListMapper;
import com.brago.app.shoplistapp.model.ProductsList;
import com.brago.app.shoplistapp.repository.ProductsListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductsListServiceImplTest {

    @Mock
    private ProductsListRepository productsListRepositoryMock;

    @Mock
    private ProductsListMapper productsListMapperMock;

    @InjectMocks
    private ProductsListServiceImpl productsListServiceImplUnderTest;

    @BeforeEach
    void setUp() {}

    @Test
    void testGetAllProductsLists() {
        // Setup
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);
        final ProductsListDto productsListDto2 = TestConfigurationUtil.createSingleProductsListDto(2L);

        final List<ProductsListDto> expectedResult = List.of(productsListDto, productsListDto2);

        // Configure ProductsListRepository.findAll(...).
        final ProductsList productsList = TestConfigurationUtil.createSingleProductsList(1L);
        final ProductsList productsList2 = TestConfigurationUtil.createSingleProductsList(2L);

        final List<ProductsList> productsListsEntities = List.of(productsList, productsList2);

        when(productsListRepositoryMock.findAll()).thenReturn(productsListsEntities);

        // Configure ProductsListMapper.entityToDto(...).
        when(productsListMapperMock.entityToDto(productsList)).thenReturn(productsListDto);
        when(productsListMapperMock.entityToDto(productsList2)).thenReturn(productsListDto2);

        // Run the test
        final List<ProductsListDto> result = productsListServiceImplUnderTest.getAllProductsLists();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllProductsLists_ProductsListRepositoryReturnsNoItems() {
        // Setup
        when(productsListRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ProductsListDto> result = productsListServiceImplUnderTest.getAllProductsLists();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetProductsList() {
        // Setup
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);

        // Configure ProductsListRepository.findAll(...).
        final ProductsList productsList = TestConfigurationUtil.createSingleProductsList(1L);

        when(productsListRepositoryMock.findById(1L)).thenReturn(Optional.of(productsList));
        when(productsListMapperMock.entityToDto(productsList)).thenReturn(productsListDto);

        // Run the test
        final ProductsListDto result = productsListServiceImplUnderTest.getProductsList(1L);

        // Verify the results
        assertThat(result).isEqualTo(productsListDto);
    }

    @Test
    void testGetProductsList_ProductsListRepositoryReturnsAbsent() {
        // Setup
        when(productsListServiceImplUnderTest.productsListRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> productsListServiceImplUnderTest.getProductsList(0L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource PRODUCTS LIST not found with id 0");
    }

    @Test
    void testCreateProductsList() {
        // Setup
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);

        // Configure ProductsListMapper.dtoToEntity(...).
        final ProductsList productsList = TestConfigurationUtil.createSingleProductsList(1L);

        when(productsListMapperMock.dtoToEntity(productsListDto)).thenReturn(productsList);
        when(productsListRepositoryMock.save(productsList)).thenReturn(productsList);
        when(productsListMapperMock.entityToDto(productsList)).thenReturn(productsListDto);

        // Run the test
        final ProductsListDto result = productsListServiceImplUnderTest.createProductsList(productsListDto);

        // Verify the results
        assertThat(result).isEqualTo(productsListDto);
    }

    @Test
    void testCreateProductsList_ProductsListRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final ProductsListDto productsListDto = TestConfigurationUtil.createSingleProductsListDto(1L);

        // Configure ProductsListMapper.dtoToEntity(...).
        final ProductsList productsList = TestConfigurationUtil.createSingleProductsList(1L);

        when(productsListMapperMock.dtoToEntity(any())).thenReturn(productsList);
        when(productsListRepositoryMock.save(any()))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> productsListServiceImplUnderTest.createProductsList(productsListDto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testUpdateProductsList() {
        // Setup
        final ProductsListDto productsListDtoNew = TestConfigurationUtil.createSingleProductsListDto(2L);

        // Configure ProductsListRepository.findById(...).
        final ProductsList productsList1 = TestConfigurationUtil.createSingleProductsList(1L);
        when(productsListRepositoryMock.findById(1L)).thenReturn(Optional.of(productsList1));

        // Configure ProductsListRepository.save(...).
        final ProductsList productsList2 = TestConfigurationUtil.createSingleProductsList(2L);
        when(productsListRepositoryMock.save(any())).thenReturn(productsList2);

        when(productsListMapperMock.entityToDto(any()))
                .thenReturn(productsListDtoNew);

        // Run the test
        final ProductsListDto result = productsListServiceImplUnderTest.updateProductsList(1L, productsListDtoNew);

        // Verify the results
        assertThat(result).isEqualTo(productsListDtoNew);
    }

    @Test
    void testUpdateProductsList_ProductsListRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(productsListRepositoryMock.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> productsListServiceImplUnderTest.updateProductsList(0L, new ProductsListDto()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDeleteProductsList() {
        // Setup
        when(productsListRepositoryMock.existsById(1L)).thenReturn(true);

        // Run the test
        productsListServiceImplUnderTest.deleteProductsList(1L);

        // Verify the results
        verify(productsListRepositoryMock).deleteById(1L);
    }

    @Test
    void testDeleteProductsList_NotFound() {
        // Setup
        when(productsListRepositoryMock.existsById(0L)).thenReturn(false);

        // Run the test
        // Verify the results
        assertThatThrownBy(() -> productsListServiceImplUnderTest.deleteProductsList(0L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource PRODUCTS LIST not found with id 0");
    }
}
