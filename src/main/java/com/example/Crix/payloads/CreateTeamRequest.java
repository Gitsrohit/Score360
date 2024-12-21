package com.example.Crix.payloads;



import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class CreateTeamRequest {
    @NotBlank
    private String name;
}

