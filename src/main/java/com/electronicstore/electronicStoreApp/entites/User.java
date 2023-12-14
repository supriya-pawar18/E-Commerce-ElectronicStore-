package com.electronicstore.electronicStoreApp.entites;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    private String name;

    private String password;

    private String email;

    private String imgname;

    private String gender;

    private String about;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders=new ArrayList<>();


}

