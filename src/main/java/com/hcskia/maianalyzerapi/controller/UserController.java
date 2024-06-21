package com.hcskia.maianalyzerapi.controller;

import com.auth0.jwt.interfaces.Claim;
import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import com.hcskia.maianalyzerapi.service.TokenUtli;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
    @PostMapping(value = "/login")//,consumes = "application/json"
    public String UserLogin(@RequestBody User loginUser) throws JoseException {
        if ((Objects.equals(loginUser.getUserId(), ""))||(Objects.equals(loginUser.getQq(), ""))){
            return "";
        }
        User user = loginUser;
        if (!userRepository.existsByUserId(user.getUserId())){
            userRepository.save(user);
        } else {
            userRepository.deleteByUserId(user.getUserId());
            userRepository.save(user);
        }
        Map<String, String> claimMap = new HashMap<>();
        claimMap.put("userid", user.getUserId());

        return TokenUtli.GenerateToken(claimMap);
    }

    @GetMapping(value = "/profile")//,consumes = "application/json"
    public User UserProfile(@RequestHeader(value = "Authorization") String token) throws Exception {
        Map<String, Claim> claimMap = TokenUtli.VerifyJWTToken(token);
        String userID = null;
        for (Map.Entry<String, Claim> entry : claimMap.entrySet()) {
            if (Objects.equals(entry.getKey(), "userid")){
                userID = String.valueOf(entry.getValue());
                break;
            }
        }
        assert userID != null;//因为token生成后用户名不可能为null
        User user = userRepository.findByUserId(userID.replace("\"",""));//去除引号
        return user;
    }
}
