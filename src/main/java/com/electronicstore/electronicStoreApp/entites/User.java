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

    @Column(length = 10)
    private String password;

    @Column(unique = true)
    private String email;

    private String imgname;

    private String gender;

    @Column(length = 1000)
    private String about;

}
