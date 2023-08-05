package com.example.frontend.model;

public class User {
    private String id;
    private String email;
    private String fullName;
    private String nickName;
    private String phone;
    private String birthDay;
    private String image;

    public User() {
    }

    public User(String id) {
        this.id = id;
    }

    public User(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }

    public User(String id, String nickName, String image) {
        this.id = id;
        this.nickName = nickName;
        this.image = image;
    }

    public User(String id, String email, String fullName, String nickName, String phone, String birthDay, String image) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.nickName = nickName;
        this.phone = phone;
        this.birthDay = birthDay;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
