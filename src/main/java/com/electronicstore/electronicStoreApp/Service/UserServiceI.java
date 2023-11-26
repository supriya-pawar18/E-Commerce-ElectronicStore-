package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;

import java.util.List;

public interface UserServiceI {

    public UserDto CreateUser(UserDto userDto);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    public UserDto getUserById(UserDto id);

    void deleteUser(Integer userId);

    UserDto findUserByEmail(String email);

    public UserDto updateUser(UserDto userDto, Integer id);


    // public List<User> searchuser(String keyword);


}
