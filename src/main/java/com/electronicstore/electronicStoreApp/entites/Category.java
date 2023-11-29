package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Categories")
public class Category {

    @Id
    private String categoryId;

    @Column(length = 100)
    private String title;

    @Column(length = 500)
    private String description;

    private String coverImage;


}
