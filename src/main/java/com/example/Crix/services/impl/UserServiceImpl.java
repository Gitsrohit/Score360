package com.example.Crix.services.impl;


import com.example.Crix.entities.User;
import com.example.Crix.exceptions.EmailAlreadyExistsException;
import com.example.Crix.exceptions.ResourceNotFoundException;
import com.example.Crix.payloads.*;

import com.example.Crix.repository.UserRepo;
import com.example.Crix.services.OtpService;
import com.example.Crix.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserRepo userRepo;
    @Autowired
    OtpService otpService;



    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDto registerNewUser(RegisterRequest registerRequest) {
        // Check if the email is already registered
        if (this.userRepo.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + registerRequest.getEmail() + " is already registered.");
        }
        log.info(registerRequest.getPassword()+registerRequest.getConfirmPassword());
        if(!Objects.equals(registerRequest.getPassword(), registerRequest.getConfirmPassword())){
            throw new EmailAlreadyExistsException("Paasword and Confirmpassword not same");

        }
        boolean isOtpValid = otpService.verifyOtp(registerRequest.getEmail(), String.valueOf(registerRequest.getOtp()));
        if(isOtpValid){
            User user = this.modelMapper.map(registerRequest, User.class);

            // Explicitly set fields with different names
            user.setPhone(String.valueOf(registerRequest.getPhone()));

            // Encode password
            user.setPassword(this.passwordEncoder.encode(registerRequest.getPassword()));

            // Save user
            User newUser = this.userRepo.save(user);

            // Map User to UserDto
            return this.modelMapper.map(newUser, UserDto.class);

        }
        else{
            throw new EmailAlreadyExistsException("Invalid Otp!!");

        }

        // Map RegisterRequest to User

    }

    @Override
    public UserDto createUser(UserDto userDto) {

        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) {
        return null;
    }

    //    @Override
//    public UserDto updateUser(UserDto userDto, Integer userId) {
//
//        User user = this.userRepo.findById(userId)
//                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
//
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setBatch(userDto.getBatch());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setRegdNo(userDto.getRegdNo());
//
//
//        User updatedUser = this.userRepo.save(user);
//        UserDto userDto1 = this.userToDto(updatedUser);
//        return userDto1;
//    }
    @Override


    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return this.userToDto(user);
    }

    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);

        Page<User> user = this.userRepo.findAll(p);

        List<User> allUsers = user.getContent();

        List<UserDto> userDtos = allUsers.stream().map((user1) -> this.modelMapper.map(user1, UserDto.class))
                .collect(Collectors.toList());

        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDtos);
        userResponse.setPageNumber(user.getNumber());
        userResponse.setPageSize(user.getSize());
        userResponse.setTotalElements(user.getTotalElements());

        userResponse.setTotalPages(user.getTotalPages());
        userResponse.setLastPage(user.isLast());

        return userResponse;



    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);

    }

    public User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        // user.setId(userDto.getId());
        // user.setName(userDto.getName());
        // user.setEmail(userDto.getEmail());
        // user.setAbout(userDto.getAbout());
        // user.setPassword(userDto.getPassword());
        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
