package com.assignment1.controller;

import com.assignment1.pojo.Login;
import com.assignment1.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginAPI {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/addLoginInfo", method = RequestMethod.POST)
    public Login addLoginInfo(@RequestParam(value = "userId") Long userId,
                              @RequestParam(value = "timestamp") Long timestamp,
                              @RequestParam(value = "loginType") String loginType) {
        return loginService.addLoginInfo(userId, timestamp, loginType);
    }

    @RequestMapping(value = "/removeLoginInfo", method = RequestMethod.POST)
    public Login removeLoginInfo(@RequestParam(value = "userId") Long userId,
                                 @RequestParam(value = "timestamp") Long timestamp,
                                 @RequestParam(value = "loginType") String loginType) {
        return loginService.removeLoginInfo(userId, timestamp, loginType);
    }

    @RequestMapping(value = "/getLoginInfoForUser", method = RequestMethod.GET)
    public List<Login> getLoginInfoForUser(@RequestParam(value = "userId") Long userId,
                                           @RequestParam(value = "loginType", required = false) String loginType) {
        return loginService.getLoginInfoForUser(userId, loginType);
    }

}
