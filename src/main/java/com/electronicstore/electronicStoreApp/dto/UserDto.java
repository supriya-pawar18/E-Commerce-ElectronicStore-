package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.validate.ImageNameValid;
import javax.persistence.*;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;

    @Size(min=3,max=15,message = "User name must be min 4chars & max 20chars")
    private String name;

    @NotBlank(message = "Password Required")
    @Size(min = 4,max = 40,message =" password must be min 4chars & max 40chars" )
    private String password;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email Requires")
    private String email;

    //@ImageNameValid
    private String imgname;

    @Size(min = 3,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write something")
    private String about;



}
