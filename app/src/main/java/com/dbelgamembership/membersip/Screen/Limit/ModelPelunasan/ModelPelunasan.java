package com.dbelgamembership.membersip.Screen.Limit.ModelPelunasan;

import java.util.List;

public class ModelPelunasan {

    private String id_customer;
    private String tipe_payment;
    private String bank_payment;
    private String tipe_pelunasan;
    private String id_bank;
    private String nama_bank;
    private String coa_bank;
    private String string_bukti;
    private List<DetailTagihan> detail;
    private double total_tagihan;
    private double total_denda;
    private double grand_total;

    public ModelPelunasan() {
    }

    public ModelPelunasan(String id_customer, String tipe_payment, String bank_payment, String tipe_pelunasan, String id_bank, String nama_bank, String coa_bank, String string_bukti, List<DetailTagihan> detail, double total_tagihan, double total_denda, double grand_total) {
        this.id_customer = id_customer;
        this.tipe_payment = tipe_payment;
        this.bank_payment = bank_payment;
        this.tipe_pelunasan = tipe_pelunasan;
        this.id_bank = id_bank;
        this.nama_bank = nama_bank;
        this.coa_bank = coa_bank;
        this.string_bukti = string_bukti;
        this.detail = detail;
        this.total_tagihan = total_tagihan;
        this.total_denda = total_denda;
        this.grand_total = grand_total;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getTipe_payment() {
        return tipe_payment;
    }

    public void setTipe_payment(String tipe_payment) {
        this.tipe_payment = tipe_payment;
    }

    public String getTipe_pelunasan() {
        return tipe_pelunasan;
    }

    public void setTipe_pelunasan(String tipe_pelunasan) {
        this.tipe_pelunasan = tipe_pelunasan;
    }

    public String getId_bank() {
        return id_bank;
    }

    public void setId_bank(String id_bank) {
        this.id_bank = id_bank;
    }

    public String getNama_bank() {
        return nama_bank;
    }

    public void setNama_bank(String nama_bank) {
        this.nama_bank = nama_bank;
    }

    public String getCoa_bank() {
        return coa_bank;
    }

    public void setCoa_bank(String coa_bank) {
        this.coa_bank = coa_bank;
    }

    public String getString_bukti() {
        return string_bukti;
    }

    public void setString_bukti(String string_bukti) {
        this.string_bukti = string_bukti;
    }

    public List<DetailTagihan> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailTagihan> detail) {
        this.detail = detail;
    }

    public double getTotal_tagihan() {
        return total_tagihan;
    }

    public void setTotal_tagihan(double total_tagihan) {
        this.total_tagihan = total_tagihan;
    }

    public double getTotal_denda() {
        return total_denda;
    }

    public void setTotal_denda(double total_denda) {
        this.total_denda = total_denda;
    }

    public double getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public String getBank_payment() {
        return bank_payment;
    }

    public void setBank_payment(String bank_payment) {
        this.bank_payment = bank_payment;
    }
}
