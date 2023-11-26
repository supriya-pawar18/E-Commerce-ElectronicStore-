package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.Service.UserServiceI;
import com.electronicstore.electronicStoreApp.Service.UserServiceImpl;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.electronicstore.electronicStoreApp.config.AppContants;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Entering request for creating new user record");
        UserDto createUser = this.userServiceI.CreateUser(userDto);
        logger.info("Completed request for creating new user record");
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir

            ) {
        logger.info("Completes request for geting user record");
        return new ResponseEntity<>(userServiceI.getAllUsers(pageNumber,pageSize,sortBy,sortDir), HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UserDto id) {
        logger.info("Entering request for getting user record with user id");
        UserDto userById = this.userServiceI.getUserById(id);
        logger.info("completed request for getting user record with user id");
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    public ResponseEntity<UserDto> updateUser(UserDto userDto, Integer id) {
        logger.info("Entering request for updating user record with user id");
        UserDto userDto1 = this.userServiceI.updateUser(userDto, id);
        logger.info("Completed request for updating user record with user id");
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }
        @GetMapping("/delete")
    public void deleteUser(@PathVariable Integer id) {
            logger.info("Entering request for getting user record with user id");
            this.userServiceI.deleteUser(id);
           logger.info("completed request for deleting user record with user id");
           new ResponseEntity<>("delete successfully", HttpStatus.OK);
    }
    @GetMapping("/email/{email}")
    public UserDto getUserByEmail(@PathVariable String email) {
        logger.info("completed request for getting user record with user email");
        return new ResponseEntity<>(userServiceI.findUserByEmail(email),HttpStatus.OK).getBody();
    }
//    @GetMapping("/search/{keyword}")
//    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
//        List<UserDto> userDtos = this.userServiceI.searchUser(keyword);
//        return new ResponseEntity<>(userDtos,HttpStatus.OK);
//    }

    //upload user img


    //server user img

    }
