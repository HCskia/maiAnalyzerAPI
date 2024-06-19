package com.hcskia.maianalyzerapi.controller;

import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import com.hcskia.maianalyzerapi.service.JWT;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@RestController
@RequestMapping("/mai")
public class MaiController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/datarefresh",consumes = "application/json")
    public User UserLogin(@RequestBody User loginUser) throws JoseException {
        if ((Objects.equals(loginUser.getUserId(), ""))||(Objects.equals(loginUser.getQq(), ""))){
            User user = new User();
            user.setUserId(null);
            user.setJWTtoken(null);
            user.setQq(null);
            return user;
        }

        User user = loginUser;
        if ((Objects.equals(user.getJWTtoken(), ""))||(user.getJWTtoken() == null)) {
            user.setJWTtoken(JWT.CreatJWT(String.valueOf(Instant.now())));
            if (userRepository.existsByUserId(user.getUserId())) {
                userRepository.deleteByUserId(user.getUserId());
            }
            userRepository.save(user);
        } else {
            Instant now = Instant.now();
            Instant theTime = Instant.parse(JWT.getJWTPayLoad(user.getJWTtoken()));
            Duration duration = Duration.between(theTime, now);
            if (duration.toDays() >= 14){
                user.setJWTtoken(JWT.CreatJWT(String.valueOf(Instant.now())));
                userRepository.deleteByUserId(user.getUserId());
                userRepository.save(user);
            }
        }
        return user;
    }
}
