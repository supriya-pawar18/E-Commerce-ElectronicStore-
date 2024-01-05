package com.electronicstore.electronicStoreApp.Controller;

import com.electronicstore.electronicStoreApp.Security.JwTHelper;
import com.electronicstore.electronicStoreApp.ServiceI.UserServiceI;
import com.electronicstore.electronicStoreApp.dto.JwtRequest;
import com.electronicstore.electronicStoreApp.dto.UserDto;
import com.electronicstore.electronicStoreApp.exception.BadApiRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

//    @PostMapping("/login")
//    public ResponseEntity<JwTHelper> login(@RequestBody JwtRequest request){
//         this.doAuthenticate(request.getEmail(),request.getPassword());
//    }

//    private void doAuthenticate(String email, String password) {
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
//        try {
//            authenticationManager.authenticate(token);
//        } catch (BadCredentialsException e) {
//            throw new BadApiRequestException("Invalid username and Password");
//        }
//    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        String name = principal.getName();
        return new ResponseEntity<>(mapper.map(userDetailsService.loadUserByUsername(name), UserDto.class), HttpStatus.OK);
    }

}
