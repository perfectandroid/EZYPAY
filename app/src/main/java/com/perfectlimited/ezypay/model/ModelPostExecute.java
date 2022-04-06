package com.perfectlimited.ezypay.model;

/**
 * Created by akash on 7/25/2016.
 */
public class ModelPostExecute {
    boolean sucess;
    Exception exception;

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
