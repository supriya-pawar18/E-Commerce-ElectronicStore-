package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.Security.JwTHelper;
import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.JwtRequest;
import com.electronicstore.electronicStoreApp.dto.JwtResponse;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceI userService;

    @Autowired
    private JwTHelper jwtHelper;

    @Autowired
    private ModelMapper mapper;

    private Logger logger= LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
        logger.info("Entering dao request for login using JwtRequest");

        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtHelper.generateToken(userDetails);
        UserDto userDto = mapper.map(userDetails, UserDto.class);

        JwtResponse response = JwtResponse.builder().jwtToken(token).user(userDto).build();
        logger.info("Completed dao call to login using JwtRequest");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new BadApiRequestException("Invalid username and Password !!");
        }
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        logger.info("Entering dao request for getCurrentUser using principal");
        String name = principal.getName();
        logger.info("Completed dao call to get Current User using principal");
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

    //lohin with google
//    @PostMapping("/google")
//    public ResponseEntity<JwtResponse> loginWithGoogle(@RequestBody Map<String,Object> data){
//         //get the id token from request
//         String idToke=data.get("idToken").toString();
//
//        NetHttpTransport netHttpTransport = new NetHttpTransport();
//
//        return null;
//       // Jackson
//    }

}
