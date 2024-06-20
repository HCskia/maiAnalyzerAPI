package com.hcskia.maianalyzerapi.controller;

import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/test",consumes = "application/json")
    public User postTest(@RequestBody User user){
        return user;
    }
    //User user = userRepository.findByUserId(loginUser.getUserId());
    @PostMapping(value = "/login",consumes = "application/json")
    public User UserLogin(@RequestBody User loginUser) throws JoseException {
        if ((Objects.equals(loginUser.getUserId(), ""))||(Objects.equals(loginUser.getQq(), ""))){
            User user = new User();
            user.setUserId(null);
            user.setQq(null);
            return user;
        }

        User user = loginUser;
        return user;
    }
}
