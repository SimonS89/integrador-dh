package com.example.workoutWonderland.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Category title should not be empty")
    @Size(min = 4, message = "Category title should be more than 4 letters long")
    private String title;
    @NotEmpty(message = "Category description should not be empty")
    private String description;
    @NotEmpty(message = "Category icon should not be empty")
    private String icon;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
