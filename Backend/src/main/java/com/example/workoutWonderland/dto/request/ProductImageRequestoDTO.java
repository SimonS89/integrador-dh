package com.example.workoutWonderland.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class ProductImageRequestoDTO {

    @NotNull(message = "id should not be null")
    @Positive(message = "invalid id")
    private Long productId;
    @NotEmpty(message = "Please send one image at least")
    private List<MultipartFile> images;
}
