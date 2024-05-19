package com.example.workoutWonderland.dto.response;

import com.example.workoutWonderland.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ProductsByCategoriesDTO {

    private Long totalProducts;
    private int totalFilteredProducts;
    private List<Product> filteredProducts;
}
