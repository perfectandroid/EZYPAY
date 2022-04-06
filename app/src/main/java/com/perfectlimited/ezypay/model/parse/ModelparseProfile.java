package com.perfectlimited.ezypay.model.parse;

/**
 * Created by akash on 12/9/2016.
 */

public class ModelparseProfile {

    String customerName="";
    String customerAddress1="";
    String customerAddress2="";
    String customerAddress3="";
    String mobileNo="";

     String acno="";
    String lastacessdate="";
    String acType="";
    String branchCode="";
    String branchName="";
    String AvailableBal="";
    String UnClrBal="";

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAddress2() {
        return customerAddress2;
    }

    public void setCustomerAddress2(String customerAddress2) {
        this.customerAddress2 = customerAddress2;
    }

    public String getCustomerAddress3() {
        return customerAddress3;
    }

    public void setCustomerAddress3(String customerAddress3) {
        this.customerAddress3 = customerAddress3;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAcno() {
        return acno;
    }

    public void setAcno(String acno) {
        this.acno = acno;
    }

    public String getLastacessdate() {
        return lastacessdate;
    }

    public void setLastacessdate(String lastacessdate) {
        this.lastacessdate = lastacessdate;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAvailableBal() {
        return AvailableBal;
    }

    public void setAvailableBal(String availableBal) {
        AvailableBal = availableBal;
    }

    public String getUnClrBal() {
        return UnClrBal;
    }

    public void setUnClrBal(String unClrBal) {
        UnClrBal = unClrBal;
    }
}
