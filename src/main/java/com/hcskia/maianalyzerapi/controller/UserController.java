package com.hcskia.maianalyzerapi.controller;

import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/test",consumes = "application/json")
    public User postTest(@RequestBody User user){
        return user;
    }

    @PostMapping(value = "/login",consumes = "application/json")
    public User UserLogin(@RequestBody User loginUser){
        User user = userRepository.findByUserId(loginUser.getUserId());
        return user;
    }
}
