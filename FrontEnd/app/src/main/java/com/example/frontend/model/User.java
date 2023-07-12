package com.example.frontend.model;

public class User {
    private String id;
    private String email;
    private String fullName;
    private String nickName;
    private String phone;
    private String birthDay;

    public User() {
    }

    public User(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public User(String id, String email, String fullName, String nickName, String phone, String birthDay) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.nickName = nickName;
        this.phone = phone;
        this.birthDay = birthDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
}
