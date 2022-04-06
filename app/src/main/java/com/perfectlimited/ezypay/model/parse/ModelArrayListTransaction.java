package com.perfectlimited.ezypay.model.parse;

/**
 * Created by akash on 12/11/2016.
 */
public class ModelArrayListTransaction {
    boolean showAvailable;
     String AvailableBal="";
    String transOpenBalance="";

    String transactions="";
    String effectDate="";
    String amount="";
    String narration="";
    String transType="";

    public String getTransOpenBalance() {
        return transOpenBalance;
    }

    public void setTransOpenBalance(String transOpenBalance) {
        this.transOpenBalance = transOpenBalance;
    }

    public String getAvailableBal() {
        return AvailableBal;
    }

    public void setAvailableBal(String availableBal) {
        AvailableBal = availableBal;
    }

    public boolean isShowAvailable() {
        return showAvailable;
    }

    public void setShowAvailable(boolean showAvailable) {
        this.showAvailable = showAvailable;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
