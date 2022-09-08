package com.dbelgamembership.membersip.Screen.Katalog.Model;

public class PostBNI {

    String tipeTransaksi;
    String kodeTransaksi;
    String type;
    String client_id;
    String trx_id;
    String trx_amount;
    String billing_type;
    String customer_name;
    String customer_email;
    String customer_phone;
    String virtual_account;
    String datetime_expired;
    String description;

    public PostBNI(String tipeTransaksi, String kodeTransaksi, String type, String client_id, String trx_id, String trx_amount, String billing_type, String customer_name, String customer_email, String customer_phone, String virtual_account, String datetime_expired, String description) {
        this.tipeTransaksi = tipeTransaksi;
        this.kodeTransaksi = kodeTransaksi;
        this.type = type;
        this.client_id = client_id;
        this.trx_id = trx_id;
        this.trx_amount = trx_amount;
        this.billing_type = billing_type;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_phone = customer_phone;
        this.virtual_account = virtual_account;
        this.datetime_expired = datetime_expired;
        this.description = description;
    }

    public String getTipeTransaksi() {
        return tipeTransaksi;
    }

    public void setTipeTransaksi(String tipeTransaksi) {
        this.tipeTransaksi = tipeTransaksi;
    }

    public String getKodeTransaksi() {
        return kodeTransaksi;
    }

    public void setKodeTransaksi(String kodeTransaksi) {
        this.kodeTransaksi = kodeTransaksi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getTrx_id() {
        return trx_id;
    }

    public void setTrx_id(String trx_id) {
        this.trx_id = trx_id;
    }

    public String getTrx_amount() {
        return trx_amount;
    }

    public void setTrx_amount(String trx_amount) {
        this.trx_amount = trx_amount;
    }

    public String getBilling_type() {
        return billing_type;
    }

    public void setBilling_type(String billing_type) {
        this.billing_type = billing_type;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getVirtual_account() {
        return virtual_account;
    }

    public void setVirtual_account(String virtual_account) {
        this.virtual_account = virtual_account;
    }

    public String getDatetime_expired() {
        return datetime_expired;
    }

    public void setDatetime_expired(String datetime_expired) {
        this.datetime_expired = datetime_expired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
