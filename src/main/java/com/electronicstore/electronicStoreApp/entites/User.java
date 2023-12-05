package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String name;

    private String password;

    private String email;

    private String imgname;

    private String gender;

    private String about;

}
