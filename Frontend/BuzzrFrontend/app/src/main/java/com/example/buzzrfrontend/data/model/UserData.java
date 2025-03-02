package com.example.buzzrfrontend.data.model;


public class UserData {

    private String name, userName, email, phoneNumber, password;
    private int id;
    private UserType userType;

    public UserData(int id, String name, String userName, String email, String phoneNumber, UserType userType) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }
    public UserData() {
        this.name = "DEFAULT NAME";
        this.userName = "DEFAULT USERNAME";
        this.email = "DEFAULT EMAIL";
        this.phoneNumber = "5555555555";
        this.password = "DEFAULT PW";
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public int getId()
    {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return password;
    }

    public void setUserType(UserType userType)
    {
        this.userType = userType;
    }
    public UserType getUserType()
    {
        return userType;
    }

    public void setNull(){
        this.name = null;
        this.userName = null;
        this.email = null;
        this.phoneNumber = null;
        this.userType = null;
    }

}
