package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.management.relation.Role;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

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
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto user1 = userServiceI.CreateUser(modelMapper.map(user, UserDto.class));
        System.out.println(user1.getName());
        Assertions.assertNotNull(user1);

        Assertions.assertEquals("Supriya", user1.getName());
    }

    @Test
    public void updateUserTest() {

        String userID = "qazxswedc";

        UserDto userDto = UserDto.builder()
                .name("Priya Kalekar")
                .about("This is update data")
                .gender("Female")
                .imgname("abc.jpg")
                .build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto updateUser = userServiceI.updateUser(userDto, userID);
        System.out.println(updateUser.getName());
        Assertions.assertNotNull(userDto);

        Assertions.assertEquals(userDto.getName(), updateUser.getName(), "Name is not equal");

    }

    @Test
    public void deleteUserTest() {

        String id = "abcdefghijklm";

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userServiceI.deleteUser(id);

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);

    }
    @Test
    public void getAllUserTest(){

        User user1 = User.builder()
                .name("Rajvi")
                .email("rajvi@gmail.com")
                .about("this is getall method")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

        User user2 = User.builder()
                .name("Reva")
                .email("reva@gmail.com")
                .about("this is Create method")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

        List<User> userList= Arrays.asList(user,user1,user2);

        Page<User> page= new PageImpl<>(userList);

        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        Sort sort= Sort.by("name").ascending();

        Pageable pageable = PageRequest.of(1,2,sort);

        PageableResponse<UserDto> allUser= userServiceI.getAllUsers(1,2,"name","asc");
        Assertions.assertEquals(3,allUser.getContent().size());

    }

    @Test
    public void getUserByIdTest(){
        String id="abcdesx";
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserDto userDto= userServiceI.getUserById(id);

        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getName(),userDto.getName(),"Name not matched");
    }

    @Test
    public void getUserByEmailTest(){
        String emailId="suppawar6@gmail.com";

        Mockito.when(userRepository.findUserByEmail(emailId)).thenReturn(Optional.of(user));

        UserDto userDto = userServiceI.findUserByEmail(emailId);
        Assertions.assertNotNull(userDto);
        Assertions.assertEquals(user.getEmail(),userDto.getEmail(),"Email not matched");

    }
}