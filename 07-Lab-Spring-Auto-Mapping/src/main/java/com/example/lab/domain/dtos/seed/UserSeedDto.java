package com.example.lab.domain.dtos.seed;

public class UserSeedDto {
    private String userName;
    private String password;
    private int age;
    private String email;

    public UserSeedDto() {
    }

    public UserSeedDto(String userName, String password, int age, String email) {
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
