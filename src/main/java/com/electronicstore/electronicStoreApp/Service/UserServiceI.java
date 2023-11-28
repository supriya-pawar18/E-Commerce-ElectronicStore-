package com.electronicstore.electronicStoreApp.Service;

import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;

public interface UserServiceI {

    public UserDto CreateUser(UserDto userDto);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    public UserDto getUserById(String id);

    void deleteUser(String id);

    UserDto findUserByEmail(String email);

    public UserDto updateUser(UserDto userDto, String id);


    // public List<User> searchuser(String keyword);


}
