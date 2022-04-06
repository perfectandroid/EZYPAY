package com.perfectlimited.ezypay.model;

import com.perfectlimited.ezypay.model.parse.ModelParseOtp;
import com.perfectlimited.ezypay.model.parse.ModelParseReceive;
import com.perfectlimited.ezypay.model.parse.ModelTransactionList;
import com.perfectlimited.ezypay.model.parse.ModelparseProfile;

/**
 * Created by akash on 12/2/2016.
 */

public class ModelServerResponse {

    ModelParseReceive modelParseReceive;
    ModelParseOtp modelParseOtp;
    ModelparseProfile modelparseProfile;
    ModelTransactionList modelTransactionList;

    String response="";
    Exception exception;

    public ModelTransactionList getModelTransactionList() {
        return modelTransactionList;
    }

    public void setModelTransactionList(ModelTransactionList modelTransactionList) {
        this.modelTransactionList = modelTransactionList;
    }

    public ModelparseProfile getModelparseProfile() {
        return modelparseProfile;
    }

    public void setModelparseProfile(ModelparseProfile modelparseProfile) {
        this.modelparseProfile = modelparseProfile;
    }

    public ModelParseOtp getModelParseOtp() {
        return modelParseOtp;
    }

    public void setModelParseOtp(ModelParseOtp modelParseOtp) {
        this.modelParseOtp = modelParseOtp;
    }

    public ModelParseReceive getModelParseReceive() {
        return modelParseReceive;
    }

    public void setModelParseReceive(ModelParseReceive modelParseReceive) {
        this.modelParseReceive = modelParseReceive;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
