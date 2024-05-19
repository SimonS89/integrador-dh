package com.example.workoutWonderland.dto.request;

import com.example.workoutWonderland.utils.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRegisterRequestDTO {
    @NotBlank(message = "Product name should not be empty")
    @Size(min = 5, message = "Product name should be more than 4 letters long")
    private String name;
    @NotBlank(message = "Product description should not be empty")
    @Size(min = 5, message = "Product description should be more than 4 letters long")
    private String description;
    @Min(value = 1, message = "category id can't be less than 1")
    @NotNull(message = "Please add at least one category")
    private Long categoryId;
    @Min(value = 1, message = "price can't be less than 1")
    @NotNull(message = "stock can't be less than 1")
    private Double price;
    @Min(value = 1, message = "stock can't be less than 1")
    @NotNull(message = "stock can't be less than 1")
    private Integer stock;
    @NotNull(message = "Genre can't be null")
    private Genre genre;
//    @NotEmpty(message = "Please add one image at least")
//    private List<MultipartFile> images;
    @NotEmpty(message = "Please add one feature at least")
    private List<Long> featuresIds;
}
