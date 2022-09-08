package com.dbelgamembership.membersip.Screen.User.Verifikasi.model;

public class PostCreateMembership {

//- id_customer
//- status_membership
//- amount
//- tipe_payment
//- bank_payment

    String id_customer;
    String status_membership;
    String amount;
    String tipe_payment;
    String bank_payment;

    public PostCreateMembership() {
    }

    public PostCreateMembership(String id_customer, String status_membership, String amount, String tipe_payment, String bank_payment) {
        this.id_customer = id_customer;
        this.status_membership = status_membership;
        this.amount = amount;
        this.tipe_payment = tipe_payment;
        this.bank_payment = bank_payment;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getStatus_membership() {
        return status_membership;
    }

    public void setStatus_membership(String status_membership) {
        this.status_membership = status_membership;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTipe_payment() {
        return tipe_payment;
    }

    public void setTipe_payment(String tipe_payment) {
        this.tipe_payment = tipe_payment;
    }

    public String getBank_payment() {
        return bank_payment;
    }

    public void setBank_payment(String bank_payment) {
        this.bank_payment = bank_payment;
    }
}
