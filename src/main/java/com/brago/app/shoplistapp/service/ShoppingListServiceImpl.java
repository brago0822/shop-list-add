package com.brago.app.shoplistapp.service;

import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.exception.ErrorType;
import com.brago.app.shoplistapp.exception.ResourceNotFoundException;
import com.brago.app.shoplistapp.mapper.ShoppingListMapper;
import com.brago.app.shoplistapp.model.ShoppingList;
import com.brago.app.shoplistapp.repository.ShoppingListRepository;
import com.brago.app.shoplistapp.service.interfaces.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingListServiceImpl implements ShoppingListService {

    public static final String PRODUCTS_LIST = "PRODUCTS LIST";
    private final ShoppingListRepository shoppingListRepository;
    private final ShoppingListMapper shoppingListMapper;

    @Override
    public List<ShoppingListDto> getAllShoppingLists() {
        return shoppingListRepository.findAll()
                .stream()
                .map(shoppingListMapper::entityToDto)
                .toList();
    }

    @Override
    public ShoppingListDto getShoppingList(Long shoppingListId) {
        var shoppingList = shoppingListRepository
                .findById(shoppingListId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCTS_LIST, shoppingListId.toString()));
        return shoppingListMapper.entityToDto(shoppingList);
    }

    @Override
    public ShoppingListDto createShoppingList(ShoppingListDto shoppingListDto) {
        ShoppingList shoppingList = shoppingListMapper.dtoToEntity(shoppingListDto);
        return shoppingListMapper.entityToDto(
                shoppingListRepository.save(shoppingList)
        );
    }

    @Override
    public ShoppingListDto updateShoppingList(Long shoppingListId, ShoppingListDto shoppingListDto) {
        var shoppingListToUpdate = shoppingListRepository
                .findById(shoppingListId)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCTS_LIST, shoppingListId.toString()));

        shoppingListToUpdate.setName(shoppingListDto.getName());
        shoppingListToUpdate.setDescription(shoppingListDto.getDescription());

        return shoppingListMapper.entityToDto(
                shoppingListRepository.save(shoppingListToUpdate)
        );
    }

    @Override
    public void deleteShoppingList(Long shoppingListId) {
        if (!shoppingListRepository.existsById(shoppingListId)) {
            throw new ResourceNotFoundException(ErrorType.RESOURCE_NOT_FOUND_ERROR, PRODUCTS_LIST, shoppingListId.toString());
        }
        shoppingListRepository.deleteById(shoppingListId);
    }
}
