package com.hcskia.maianalyzerapi.controller;

import com.hcskia.maianalyzerapi.pojo.MaiData;
import com.hcskia.maianalyzerapi.pojo.User;
import com.hcskia.maianalyzerapi.repository.UserRepository;
import com.hcskia.maianalyzerapi.service.TokenUtli;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@RestController
@RequestMapping("/mai")
public class MaiController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/datarefresh",consumes = "application/json")
    public String DataRefresh(@CookieValue(value = "jwt_token", required = true) String cookie) throws JoseException {

        return "";
    }
}
