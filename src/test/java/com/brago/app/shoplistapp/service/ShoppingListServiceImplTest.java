package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.TestConfigurationUtil;
import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
import com.brago.app.shoplistapp.mapper.ShoppingListMapper;
import com.brago.app.shoplistapp.model.ShoppingList;
import com.brago.app.shoplistapp.repository.ShoppingListRepository;
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
class ShoppingListServiceImplTest {

    @Mock
    private ShoppingListRepository shoppingListRepositoryMock;

    @Mock
    private ShoppingListMapper shoppingListMapperMock;

    @InjectMocks
    private ShoppingListServiceImpl shoppingListServiceImplUnderTest;

    @BeforeEach
    void setUp() {}

    @Test
    void testGetAllShoppingLists() {
        // Setup
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);
        final ShoppingListDto shoppingListDto2 = TestConfigurationUtil.createSingleShoppingListDto(2L);

        final List<ShoppingListDto> expectedResult = List.of(shoppingListDto, shoppingListDto2);

        // Configure ShoppingListRepository.findAll(...).
        final ShoppingList shoppingList = TestConfigurationUtil.createSingleShoppingList(1L);
        final ShoppingList shoppingList2 = TestConfigurationUtil.createSingleShoppingList(2L);

        final List<ShoppingList> shoppingListsEntities = List.of(shoppingList, shoppingList2);

        when(shoppingListRepositoryMock.findAll()).thenReturn(shoppingListsEntities);

        // Configure ShoppingListMapper.entityToDto(...).
        when(shoppingListMapperMock.entityToDto(shoppingList)).thenReturn(shoppingListDto);
        when(shoppingListMapperMock.entityToDto(shoppingList2)).thenReturn(shoppingListDto2);

        // Run the test
        final List<ShoppingListDto> result = shoppingListServiceImplUnderTest.getAllShoppingLists();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAllShoppingLists_ShoppingListRepositoryReturnsNoItems() {
        // Setup
        when(shoppingListRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<ShoppingListDto> result = shoppingListServiceImplUnderTest.getAllShoppingLists();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetShoppingList() {
        // Setup
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);

        // Configure ShoppingListRepository.findAll(...).
        final ShoppingList shoppingList = TestConfigurationUtil.createSingleShoppingList(1L);

        when(shoppingListRepositoryMock.findById(1L)).thenReturn(Optional.of(shoppingList));
        when(shoppingListMapperMock.entityToDto(shoppingList)).thenReturn(shoppingListDto);

        // Run the test
        final ShoppingListDto result = shoppingListServiceImplUnderTest.getShoppingList(1L);

        // Verify the results
        assertThat(result).isEqualTo(shoppingListDto);
    }

    @Test
    void testGetShoppingList_ShoppingListRepositoryReturnsAbsent() {
        // Setup
        when(shoppingListRepositoryMock.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> shoppingListServiceImplUnderTest.getShoppingList(0L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource PRODUCTS LIST not found with id 0");
    }

    @Test
    void testCreateShoppingList() {
        // Setup
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);

        // Configure ShoppingListMapper.dtoToEntity(...).
        final ShoppingList shoppingList = TestConfigurationUtil.createSingleShoppingList(1L);

        when(shoppingListMapperMock.dtoToEntity(shoppingListDto)).thenReturn(shoppingList);
        when(shoppingListRepositoryMock.save(shoppingList)).thenReturn(shoppingList);
        when(shoppingListMapperMock.entityToDto(shoppingList)).thenReturn(shoppingListDto);

        // Run the test
        final ShoppingListDto result = shoppingListServiceImplUnderTest.createShoppingList(shoppingListDto);

        // Verify the results
        assertThat(result).isEqualTo(shoppingListDto);
    }

    @Test
    void testCreateShoppingList_ShoppingListRepositoryThrowsOptimisticLockingFailureException() {
        // Setup
        final ShoppingListDto shoppingListDto = TestConfigurationUtil.createSingleShoppingListDto(1L);

        // Configure ShoppingListMapper.dtoToEntity(...).
        final ShoppingList shoppingList = TestConfigurationUtil.createSingleShoppingList(1L);

        when(shoppingListMapperMock.dtoToEntity(any())).thenReturn(shoppingList);
        when(shoppingListRepositoryMock.save(any()))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> shoppingListServiceImplUnderTest.createShoppingList(shoppingListDto))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testUpdateShoppingList() {
        // Setup
        final ShoppingListDto shoppingListDtoNew = TestConfigurationUtil.createSingleShoppingListDto(2L);

        // Configure ShoppingListRepository.findById(...).
        final ShoppingList shoppingList1 = TestConfigurationUtil.createSingleShoppingList(1L);
        when(shoppingListRepositoryMock.findById(1L)).thenReturn(Optional.of(shoppingList1));

        // Configure ShoppingListRepository.save(...).
        final ShoppingList shoppingList2 = TestConfigurationUtil.createSingleShoppingList(2L);
        when(shoppingListRepositoryMock.save(any())).thenReturn(shoppingList2);

        when(shoppingListMapperMock.entityToDto(any()))
                .thenReturn(shoppingListDtoNew);

        // Run the test
        final ShoppingListDto result = shoppingListServiceImplUnderTest.updateShoppingList(1L, shoppingListDtoNew);

        // Verify the results
        assertThat(result).isEqualTo(shoppingListDtoNew);
    }

    @Test
    void testUpdateShoppingList_ShoppingListRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(shoppingListRepositoryMock.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> shoppingListServiceImplUnderTest.updateShoppingList(0L, new ShoppingListDto()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testDeleteShoppingList() {
        // Setup
        when(shoppingListRepositoryMock.existsById(1L)).thenReturn(true);

        // Run the test
        shoppingListServiceImplUnderTest.deleteShoppingList(1L);

        // Verify the results
        verify(shoppingListRepositoryMock).deleteById(1L);
    }

    @Test
    void testDeleteShoppingList_NotFound() {
        // Setup
        when(shoppingListRepositoryMock.existsById(0L)).thenReturn(false);

        // Run the test
        // Verify the results
        assertThatThrownBy(() -> shoppingListServiceImplUnderTest.deleteShoppingList(0L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Resource PRODUCTS LIST not found with id 0");
    }
}
