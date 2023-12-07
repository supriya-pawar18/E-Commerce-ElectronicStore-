package com.electronicstore.electronicStoreApp.ServiceImpl;

import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.helper.AppContants;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.entites.User;
import com.electronicstore.electronicStoreApp.exception.ResourceNotFoundException;
import com.electronicstore.electronicStoreApp.helper.Helper;
import com.electronicstore.electronicStoreApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserServiceI {
   @Autowired
    private UserRepository userRepo;
   @Autowired
    private ModelMapper modelMapper;
   @Value("{@user.profile.image.path}")
   private String imagePath;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserDto createUser(UserDto userDto) {
        logger.info("Initiating dao request for creating user record");
        String string = UUID.randomUUID().toString();
        userDto.setId(string);
        User user1 = this.dtoToUser(userDto);
        User saveuser = this.userRepo.save(user1);
        logger.info("Completed dao request for creating user record");
        return this.userToDto(saveuser);
    }

    @Override
    public PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info("Initiating dao request for getting user record");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> page = this.userRepo.findAll(pageable);
        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        logger.info("Completed dao request for getting user record");
        return response;
    }

    @Override
    public UserDto getUserById(String id) {
        logger.info("Initiating dao request for getting user record with user id");
        User user = this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException(AppContants.NOT_FOUND));
        logger.info("Completed dao request for getting user record with user id");
        return this.userToDto(user);
    }

    @Override
    public void deleteUser(String id) {
        logger.info("Initiating dao request for deleting user record");
        User users = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found"));
        userRepo.delete(users);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        logger.info("Initiating dao request for getting user record with user email");
        User user = userRepo.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException(" Email not found "));
        logger.info("Completed dao request for getting user record with user email");
        return userToDto(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        logger.info("Initiating dao request for creating user record with user id{}:",id);
        User user = userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with gievn id"));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setPassword(userDto.getPassword());
        user.setImgname(userDto.getImgname());

        User updatedDto = userRepo.save(user);
        UserDto userDto1 = userToDto(updatedDto);
        logger.info("Completed dao request for getting user record with user id");
        return userDto1;
    }


//    @Override
//    public List<User> searchuser(String keyword) {
//        List<User> byNameContaining = userRepo.findByNameContaining(keyword);
//        return byNameContaining;
//    }


    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto=this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
