package com.example.workoutWonderland.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssessmentRequestDTO {
    @NotNull(message = "score should not be null")
    @Positive(message = "invalid score")
    @Max(value = 5, message = "Score shouldn't be more than 5")
    private Long score;
    private String review;
    @NotNull(message = "id should not be null")
    @Positive(message = "invalid id")
    private Long userId;
    @NotNull(message = "id should not be null")
    @Positive(message = "invalid id")
    private Long productId;
}
