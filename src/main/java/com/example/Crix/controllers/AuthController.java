package com.example.Crix.controllers;

import com.example.Crix.entities.User;

import com.example.Crix.exceptions.ApiException;
import com.example.Crix.payloads.*;
import com.example.Crix.repository.UserRepo;
import com.example.Crix.response.BaseApiResponse;
import com.example.Crix.response.LoginResponse;
import com.example.Crix.security.CustomUserDetailService;
import com.example.Crix.security.JwtTokenHelper;
import com.example.Crix.services.EmailService;
import com.example.Crix.services.OtpService;
import com.example.Crix.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    EmailService emailService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;
    @Autowired
    private OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<BaseApiResponse<LoginResponse>> createToken(@RequestBody LoginRequest request) throws Exception {
        if (isAdminCredentials(request.getUsername(), request.getPassword())) {

            System.out.println(request.getOtp()+"DEVIHO ERBIOV;HIBVOFVHFO IFBEGIOBVFVJFVBGB FBIJ GVNOFUBNFBEFGB FVBJG GF");
            boolean isOtpValid = otpService.verifyOtp(request.getUsername(), String.valueOf(request.getOtp()));
            System.out.println(request.getOtp()+"DEVIHO ERBIOV;HIBVOFVHFO IFBEGIOBVFVJFVBGB FBIJ GVNOFUBNFBEFGB FVBJG GF");

            if(isOtpValid){
                String token = jwtTokenHelper.generateAdminToken(request.getUsername());

                LoginResponse response = new LoginResponse();
                response.setToken(token);
                response.setUser(new UserDDto());
                return ResponseEntity.ok(
                        BaseApiResponse.<LoginResponse>builder()
                                .success(true)
                                .message("Successful Login")
                                .data(response)
                                .build()
                );

            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseApiResponse.<LoginResponse>builder()
                                .success(false)
                                .message("Invalid OTP!!")
                                .data(null)  // You can set this to null or any relevant error object
                                .build());
            }



        } else {
            boolean isOtpValid = otpService.verifyOtp(request.getUsername(), String.valueOf(request.getOtp()));
            if(isOtpValid){
                authenticate(request.getUsername(), request.getPassword());
                UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
                String token = jwtTokenHelper.generateToken(userDetails);

                LoginResponse response = new LoginResponse();
                response.setToken(token);
                response.setUser(mapper.map(userDetails, UserDDto.class));
                return ResponseEntity.ok(
                        BaseApiResponse.<LoginResponse>builder()
                                .success(true)
                                .message("Successful Login")
                                .data(response)
                                .build()
                );

            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(BaseApiResponse.<LoginResponse>builder()
                                .success(false)
                                .message("Invalid OTP!!")
                                .data(null)  // You can set this to null or any relevant error object
                                .build());
            }


        }
    }

    private boolean isAdminCredentials(String username, String password) {
        // Replace with your actual admin credentials logic
        String adminUsername = "sanskarbhadani11@gmail.com";
        String adminPassword = "Sanskar12345@#$@"; // Replace with your actual admin password

        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    private void authenticate(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            throw new ApiException("Invalid username or password");
        }
    }


    // register new user api

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<BaseApiResponse<UserDto>> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserDto registeredUser = this.userService.registerNewUser(registerRequest);

        return ResponseEntity.ok(
                BaseApiResponse.<UserDto>builder()
                        .success(true)
                        .message("User successfully registered")
                        .data(registeredUser)
                        .build()
        );
    }

    @PostMapping("/sendOtp")
    public ResponseEntity<BaseApiResponse<String>> sendOtp(@RequestParam String email) {
        String email2 = email.trim();

        String otp = otpService.generateOtp(email2);
        emailService.sendSimpleMessage(email2,"Your OTP ","OTP is "+otp);

        return ResponseEntity.ok(
                BaseApiResponse.<String>builder()
                        .success(true)
                        .message("OTP Succesfully Sent")
                        .data(otp)
                        .build()
        );
    }

    // get loggedin user data
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper mapper;

    @GetMapping("/current-user/")
    public ResponseEntity<UserDto> getUser(Principal principal) {
        User user = this.userRepo.findByEmail(principal.getName()).get();
        return new ResponseEntity<UserDto>(this.mapper.map(user, UserDto.class), HttpStatus.OK);
    }
    private boolean isValidEmailFormat(String email) {
        // Implement your email validation logic here (e.g., regex check)
        // Example regex for basic validation (not comprehensive)
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

}
