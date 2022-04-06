package com.perfectlimited.ezypay.model.parse;

/**
 * Created by akash on 12/8/2016.
 */

public class ModelParseOtp {
    String balance="";
    String message="";
    String reponseCode="";

    public String getReponseCode() {
        return reponseCode;
    }

    public void setReponseCode(String reponseCode) {
        this.reponseCode = reponseCode;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
