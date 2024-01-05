package com.electronicstore.electronicStoreApp.dto;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
//import org.springframework.web.ErrorResponse;

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
