package com.perfectlimited.ezypay.model;

/**
 * Created by akash on 7/25/2016.
 */
public class ModelUserDetails {
    String nameUser="";
    String lastLogin="";
    String lastLoginDevice="";
    String userDesciption="";

    public String getUserDesciption() {
        return userDesciption;
    }

    public void setUserDesciption(String userDesciption) {
        this.userDesciption = userDesciption;
    }

    public String getLastLoginDevice() {
        return lastLoginDevice;
    }

    public void setLastLoginDevice(String lastLoginDevice) {
        this.lastLoginDevice = lastLoginDevice;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
