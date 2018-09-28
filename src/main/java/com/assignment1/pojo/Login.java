package com.assignment1.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOGIN_TBL")
public class Login {
    @Id
    @GeneratedValue
    private Long id;
    private Long userId;
    private Long timestamp;
    private String loginType;

    public Login(Long userId, Long timestamp, String loginType) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.loginType = loginType;
    }

    public Login() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
