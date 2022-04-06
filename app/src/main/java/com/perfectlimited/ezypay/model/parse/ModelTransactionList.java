package com.perfectlimited.ezypay.model.parse;

import java.util.ArrayList;

/**
 * Created by akash on 12/11/2016.
 */

public class ModelTransactionList {

    String asOnDate="";
        String acno="";
     String OpeningBal="";
    ArrayList<ModelArrayListTransaction> modelArrayListTransactions;

    public String getAsOnDate() {
        return asOnDate;
    }

    public void setAsOnDate(String asOnDate) {
        this.asOnDate = asOnDate;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }



    public String getOpeningBal() {
        return OpeningBal;
    }

    public void setOpeningBal(String openingBal) {
        OpeningBal = openingBal;
    }

    public ArrayList<ModelArrayListTransaction> getModelArrayListTransactions() {
        return modelArrayListTransactions;
    }

    public void setModelArrayListTransactions(ArrayList<ModelArrayListTransaction> modelArrayListTransactions) {
        this.modelArrayListTransactions = modelArrayListTransactions;
    }
}
