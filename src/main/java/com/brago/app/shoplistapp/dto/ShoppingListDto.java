package com.brago.app.shoplistapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShoppingListDto {
    private Long id;

    @NotBlank(message = "Name of the list must not be blank")
    private String name;
    @NotBlank(message = "Description of the list must not be blank")
    private String description;
}
