package com.example.test_model_mapper.controller;

import com.example.test_model_mapper.model.UserApp;
import com.example.test_model_mapper.model.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class TestController {

    List<UserApp> users = new ArrayList<>();

    private final ModelMapper modelMapper;

    public TestController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @PostMapping("/register")
    public ResponseEntity register(
            @RequestBody UserApp userApp
    ) {
        UserApp user = new UserApp(userApp.getEmail(), userApp.getPassword(), userApp.getName());
        users.add(user);

        modelMapper.typeMap(UserApp.class, UserDTO.class).addMappings(mapper -> {
            mapper.map(UserApp::getEmail, UserDTO::setEmailAddress);
            mapper.map(UserApp::getName, UserDTO::setFullName);
        });

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return ResponseEntity.ok().body(userDTO);
    }
}
