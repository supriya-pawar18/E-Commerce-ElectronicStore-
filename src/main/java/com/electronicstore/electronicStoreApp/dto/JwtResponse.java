package com.electronicstore.electronicStoreApp.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse {

    private String jwtToken;
    private UserDto user;
}
