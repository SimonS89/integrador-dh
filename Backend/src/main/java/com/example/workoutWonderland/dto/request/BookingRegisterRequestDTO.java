package com.example.workoutWonderland.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRegisterRequestDTO {
    @NotNull(message = "User id should not be empty")
    @Positive(message = "invalid id")
    private Long userId;
    @NotNull(message = "Product id should not be empty")
    @Positive(message = "invalid id")
    private Long productId;
//    @NotBlank(message = "Start Date should not be empty")
    private LocalDate startDate;
//    @NotBlank(message = "End Date should not be empty")
    private LocalDate endDate;
}