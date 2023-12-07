package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.management.relation.Role;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserServiceI userServiceI;

    private User user;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    private void init(){
      //  role= Role.builder().roleId("abc").roleName("NORMAL").build();

        user= User.builder()
                .name("supriya")
                .email("suppawar6@gmail.com")
                .about("This is testing controller ")
                .gender("Female")
                .imgname("abc.png")
                .password("abcd")
                .build();

    }
    @Test
    public void createUserTest() throws Exception{

       UserDto dto=modelMapper.map(user,UserDto.class);

        Mockito.when(userServiceI.createUser(Mockito.any())).thenReturn(dto);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());

    }

    private String convertObjectToJsonString(User user) {
        try{
             return new ObjectMapper().writeValueAsString(user);
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
