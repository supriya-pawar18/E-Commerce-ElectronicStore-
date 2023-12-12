package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.ServiceI.FileService;
import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.ApiResponse;
import com.electronicstore.electronicStoreApp.dto.ImageResponse;
import com.electronicstore.electronicStoreApp.dto.PageableResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static com.electronicstore.electronicStoreApp.helper.AppContants.CATEGORY_DELETED;
import static com.electronicstore.electronicStoreApp.helper.AppContants.USER_DELETED;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceI userServiceI;
    @Autowired
    private FileService fileService;
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @Value("${user.profile.image.path}")
    private String imageUploadPath;

    /**
     * @param userDto
     * @return http status for save data
     * @apiNote This Api is used to create new user in databased
     */
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Entering request for creating new user record");
        UserDto createUser = this.userServiceI.createUser(userDto);
        logger.info("Completed request for creating new user record");
        return new ResponseEntity<>(createUser, HttpStatus.CREATED);
    }

    /**
     * @return http status for getting data
     * @paramuserDto used to pass to create new create
     * @apiNote To get all user data from database
     */
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

    /**
     * @return http status for get single data from database
     * * @param userDto UserDto Object
     * @apiNote To get single user data from database using id
     */
     @GetMapping("/getById/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable UserDto id) {
        logger.info("Entering request for getting user record with user id {}:",id);
        UserDto userById = this.userServiceI.getUserById(id.getId());
        logger.info("completed request for getting user record with user id {}:" ,id);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    /**
     * @param userDto UserDto Object
     * @return userDto
     * @apiNote This Api is used to update user data with id in  database
     */
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<UserDto> updateUser(UserDto userDto, String id) {
        logger.info("Entering request for updating user record with user id {}:",id);
        UserDto userDto1 = this.userServiceI.updateUser(userDto, id);
        logger.info("Completed request for updating user record with user id {}:",id);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    /**
     * *@param userDto UserDto Object
     * @return userDto
     * @apiNote This Api is used to delete user data with id in  database
     */
    @GetMapping("/delete/{id}")
    public void deleteUser(@PathVariable String id) {
            logger.info("Entering request for getting user record with user id {}:",id);
            this.userServiceI.deleteUser(id);
            ApiResponse response = ApiResponse.builder().message(USER_DELETED).status(HttpStatus.OK).build();
            logger.info("completed request for deleting user record with user id {}:",id);
           new ResponseEntity<>("delete successfully", HttpStatus.OK);
    }

    /**
     * @paramuserDto UserDto Object
     * @return userDto
     * @apiNote This Api is used to get user data with email in database
     */
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

   // upload user img

    /**
     * @paramuserDto UserDto Object
     * @return userDto
     * @apiNote This Api is used to update image  with id in database
     */
    @PostMapping("/image/{id}")
    public  ResponseEntity<ImageResponse> uploadingImage(@RequestParam("userImage")MultipartFile imgname,@PathVariable String id) throws IOException {

        String imageName = fileService.upLoadFile(imgname, imageUploadPath);

        UserDto user = userServiceI.getUserById(id);
        user.setImgname(imageName);
        UserDto user1 = userServiceI.updateUser(user,id);

        ImageResponse imageResponse=ImageResponse.builder().imgname(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);

    }

    //server user img

    /**
     * @paramuserDto UserDto Object
     * @return userDto
     * @apiNote This Api is used to Get image with id from database
     */
    @GetMapping(value = "/getImage/{id}")
    public void serveUserImage(@PathVariable String id, HttpServletResponse response) throws IOException {
      UserDto user = this.userServiceI.getUserById(id);
      InputStream inputStream = fileService.getResource(imageUploadPath, user.getImgname());
      response.setContentType(MediaType.IMAGE_PNG_VALUE);
      StreamUtils.copy(inputStream,response.getOutputStream());
   }

    }


