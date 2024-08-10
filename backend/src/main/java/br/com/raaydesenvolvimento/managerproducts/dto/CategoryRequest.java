package br.com.raaydesenvolvimento.managerproducts.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    private Long id;

    @NotNull(message = "The category code cannot be null")
    @NotBlank(message = "The category code cannot be blank")
    @Size(min = 1, max = 50, message = "Code must have between 1 and 50 characters")
    private String code;

    @NotNull(message = "The category description cannot be null")
    @NotBlank(message = "The category description cannot be blank")
    @Size(min = 1, max = 255, message = "The category description must have between 1 and 255 characters")
    private String description;

}
