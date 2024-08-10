package br.com.raaydesenvolvimento.managerproducts.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {

    private Long id;

    @NotNull(message = "The product code cannot be null")
    @NotBlank(message = "The product code cannot be blank")
    @Size(min = 1, max = 50, message = "The product code must have between 1 and 50 characters")
    private String code;

    @NotNull(message = "The product description cannot be null")
    @NotBlank(message = "The product description cannot be blank")
    @Size(min = 1, max = 255, message = "The product description must have between 1 and 255 characters")
    private String description;

    @NotNull(message = "The product price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "The product price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The product price must have up to 10 digits and 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "The product category cannot be null")
    private Long categoryId;
}
