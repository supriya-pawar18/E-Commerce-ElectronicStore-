package com.electronicstore.electronicStoreApp.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponse {

        private String imgname;
        private String message;
        private boolean success;
        private HttpStatus status;

    }
