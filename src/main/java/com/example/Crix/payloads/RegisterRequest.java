package com.example.Crix.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String name;


    private String password;
    private String phone;


}
