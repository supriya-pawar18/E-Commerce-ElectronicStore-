package com.electronicstore.electronicStoreApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Min(value = 4,message = "title must be of minimun 4chars")
    private String title;

    @NotBlank(message = "Description Required !!!")
    private String description;

    private String coverImage;
}
