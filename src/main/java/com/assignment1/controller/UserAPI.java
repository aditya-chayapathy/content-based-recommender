package com.assignment1.controller;

import com.assignment1.pojo.User;
import com.assignment1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    UserService userService;

    @RequestMapping("/test")
    @CrossOrigin
    public String test() {
        return "Test";
    }

    @RequestMapping(value = "/addUser", method = POST)
    @CrossOrigin
    public User addUser(@RequestParam(value = "name") String name,
                        @RequestParam(value = "password") String password,
                        @RequestParam(value = "address") String address) {
        return userService.addUser(name, password, address);
    }

    @RequestMapping(value = "/removeUser", method = POST)
    @CrossOrigin
    public User removeUser(@RequestParam(value = "name") String name,
                           @RequestParam(value = "password") String password) {
        return userService.removeUser(name, password);
    }

    @RequestMapping(value = "/checkIfUserExists")
    @CrossOrigin
    public Boolean checkIfUserExists(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "password") String password) {
        return userService.checkIfUserExists(name, password);
    }

    @RequestMapping(value = "/getUserDetails")
    @CrossOrigin
    public User getUserDetails(@RequestParam(value = "name") String name,
                               @RequestParam(value = "password") String password) {
        return userService.getUserDetails(name, password);
    }

    @RequestMapping(value = "/getAllUsers")
    @CrossOrigin
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/logUserIn", method = POST)
    @CrossOrigin
    public User logUserIn(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password) {
        return userService.logUserIn(username, password);
    }

    @RequestMapping(value = "/logUserOut", method = POST)
    @CrossOrigin
    public Boolean logUserOut(@RequestParam(value = "userId") Long userId) {
        return userService.logUserOut(userId);
    }

}
