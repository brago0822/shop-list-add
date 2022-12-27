package com.brago.app.shoplistapp.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "Name of the product must not be blank")
    private String name;
    private String notes;
}
