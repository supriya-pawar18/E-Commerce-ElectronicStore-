package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;
import java.util.Set;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserServiceI userServiceI;

    private User user;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init() {

        user = User.builder()
                .name("supriya")
                .email("suppawar6@gmail.com")
                .about("This is testing create method")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();
    }

    @Test
    public void createUser() {

        UserDto dto = modelMapper.map(user, UserDto.class);

        //   Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);


    }
}