package com.assignment1.services;

import com.assignment1.pojo.User;
import com.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    public User addUser(String name, String password, String address) {
        User newUser = new User(name, password, address);
        userRepository.save(newUser);

        return newUser;
    }

    public User removeUser(String name, String password) {
        User currentUser = userRepository.findByNameAndPassword(name, password);
        userRepository.delete(currentUser);

        return currentUser;
    }

    public Boolean checkIfUserExists(String name, String password) {
        User user = userRepository.findByNameAndPassword(name, password);
        if (user != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public User getUserDetails(String name, String password) {
        return userRepository.findByNameAndPassword(name, password);

    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User logUserIn(String username, String password) {
        if (checkIfUserExists(username, password)) {
            User user = getUserDetails(username, password);
            loginService.addLoginInfo(user.getId(), System.currentTimeMillis(), "LOG_IN");

            return user;
        } else {
            return null;
        }
    }

    public Boolean logUserOut(Long userId) {
        loginService.addLoginInfo(userId, System.currentTimeMillis(), "LOG_OUT");

        return Boolean.TRUE;
    }

}
