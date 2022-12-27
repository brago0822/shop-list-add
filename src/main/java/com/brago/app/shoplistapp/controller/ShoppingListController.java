package com.brago.app.shoplistapp.controller;

import com.brago.app.shoplistapp.dto.ShoppingListDto;
import com.brago.app.shoplistapp.service.interfaces.ShoppingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/shopping-list")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingListDto> getAllShoppingList() {
        return shoppingListService.getAllShoppingLists();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDto getShoppingList(@PathVariable("id") Long shoppingListId) {
        return shoppingListService.getShoppingList(shoppingListId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListDto createShoppingList(@RequestBody @Validated ShoppingListDto shoppingListDto) {
        return shoppingListService.createShoppingList(shoppingListDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListDto updateShoppingList(@PathVariable("id") Long shoppingListId,
                                              @RequestBody @Validated ShoppingListDto shoppingListDto) {
        return shoppingListService.updateShoppingList(shoppingListId, shoppingListDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteShoppingList(@PathVariable("id") Long shoppingListId) {
        shoppingListService.deleteShoppingList(shoppingListId);
    }

}
