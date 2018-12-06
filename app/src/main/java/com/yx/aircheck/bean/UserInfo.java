package com.yx.aircheck.bean;

import org.litepal.crud.DataSupport;

public class UserInfo extends DataSupport {
    private Integer id;
    private String username;
    private String password;
    private String gender;
    private Integer age;
    private String email;
    private String phone;
    private String isVIP;
    private Integer rewardpoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsVIP() {
        return isVIP;
    }

    public void setIsVIP(String isVIP) {
        this.isVIP = isVIP;
    }

    public Integer getRewardpoint() {
        return rewardpoint;
    }

    public void setRewardpoint(Integer rewardpoint) {
        this.rewardpoint = rewardpoint;
    }
}
