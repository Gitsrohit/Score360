package com.example.Crix.response;

import com.example.Crix.payloads.UserDDto;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;


    private UserDDto user;

}
