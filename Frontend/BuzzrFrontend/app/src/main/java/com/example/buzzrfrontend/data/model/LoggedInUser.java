package com.example.buzzrfrontend.data.model;

import com.example.buzzrfrontend.data.Navigation;


public class LoggedInUser extends UserData {
    boolean isLoggedIn;
    Navigation nav;
    public LoggedInUser(int id, String name, String userName, String email, String phoneNumber, String password, UserType userType) {
        super(id, name, userName, email, phoneNumber, userType);
        isLoggedIn = true;
    }
    public LoggedInUser(UserData user) {
        super(user.getId(), user.getName(), user.getUserName(), user.getEmail(), user.getPhoneNumber(), user.getUserType());
        isLoggedIn = true;
    }
    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }

    //@TODO add bounds to parameters ex. No blank username/name/email etc
    // Should we handle this via springboot?
    public void logout()
    {
        //@TODO add post/put for last time account used
        this.setNull();
        isLoggedIn = false;
    }


}