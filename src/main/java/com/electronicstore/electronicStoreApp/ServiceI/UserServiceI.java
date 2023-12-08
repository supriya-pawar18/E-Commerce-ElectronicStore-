package com.electronicstore.electronicStoreApp.ServiceI;

import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;

public interface UserServiceI {

    public UserDto createUser(UserDto userDto);

    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    public UserDto getUserById(String id);

    void deleteUser(String id);

   // UserDto getUserByEmail(String email);

    UserDto findUserByEmail(String email);

    public UserDto updateUser(UserDto userDto, String id);



    // UserDto getUserByEmail(String emailId);


    // public List<User> searchuser(String keyword);


}
