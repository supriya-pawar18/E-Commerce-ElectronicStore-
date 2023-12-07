package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Categories")
public class Category {

    @Id
    private String categoryId;

    private String title;

    private String description;

    private String coverImage;


}
