package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
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
import java.util.Arrays;
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

    @Test
    public void updateUserTest() throws Exception {
        String id="12";
        UserDto dto=this.modelMapper.map(user,UserDto.class);
        Mockito.when(userServiceI.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(dto);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/user/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJsonString(user))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").exists());
    }

    @Test
    public void getAllUserTest() throws Exception {
        UserDto userDto= UserDto.builder().name("vivek").about("This is update data").gender("Male").imgname("abc.png").build();
        UserDto userDto1= UserDto.builder().name("Nilu").about("This is update data").gender("Female").imgname("gef.png").build();
        UserDto userDto2= UserDto.builder().name("Ashu").about("This is update data").gender("Female").imgname("avc.png").build();
        UserDto userDto3= UserDto.builder().name("Siya").about("This is update data").gender("Female").imgname("si.png").build();

        PageableResponse<UserDto> pageableResponse=new PageableResponse<>();
        pageableResponse.setContent(Arrays.asList(userDto,userDto1,userDto2,userDto3));

        pageableResponse.setLastPage(false);
        pageableResponse.setPageSize(10);
        pageableResponse.setPageNumber(20);
        pageableResponse.setTotalElements(100);

        Mockito.when(userServiceI.getAllUsers(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                      .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }



}
