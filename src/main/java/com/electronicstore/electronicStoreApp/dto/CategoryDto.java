package com.electronicstore.electronicStoreApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.ErrorResponse;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 4,message = "title must be of minimun 4chars")
    private String title;

    @NotBlank(message = "Description Required !!!")
    private String description;

    private String coverImage;


}
