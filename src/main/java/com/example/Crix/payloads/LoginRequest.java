package com.example.Crix.payloads;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    private String password;
    private String otp;

}
