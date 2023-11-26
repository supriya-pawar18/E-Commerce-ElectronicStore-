package com.electronicstore.electronicStoreApp.dto;

import com.electronicstore.electronicStoreApp.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ImageNameValid
    private String imgname;

    @Size(min = 3,max = 6,message = "Invalid Gender")
    private String gender;

    @NotBlank(message = "Write something")
    private String about;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", imgname='" + imgname + '\'' +
                ", gender='" + gender + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
