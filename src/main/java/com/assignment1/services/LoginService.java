package com.assignment1.services;

import com.assignment1.pojo.Login;
import com.assignment1.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService {

    @Autowired
    LoginRepository loginRepository;

    public Login addLoginInfo(Long userId, Long timestamp, String loginType) {
        Login login = new Login(userId, timestamp, loginType);
        loginRepository.save(login);

        return login;
    }

    public Login removeLoginInfo(Long userId, Long timestamp, String loginType) {
        Login loginInfo = loginRepository.findByUserIdAndTimestampAndLoginType(userId, timestamp, loginType);
        loginRepository.delete(loginInfo);

        return loginInfo;
    }

    public List<Login> getLoginInfoForUser(Long userId, String loginType) {
        if (loginType == null) {
            return loginRepository.findTop8ByUserIdOrderByTimestampDesc(userId);
        }

        return loginRepository.findTop8ByUserIdAndLoginTypeOrderByTimestampDesc(userId, loginType);
    }
}
