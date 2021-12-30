package com.dbelgamembership.membersip.Screen.Katalog.Model;

public class ModelPostSetPayment {

    private String kode_transaksi;
    private String tipe_payment;
    private String bukti_payment;
    private ModelPayments payments;

    public ModelPostSetPayment() {
    }

    public ModelPostSetPayment(String kode_transaksi, String tipe_payment, String bukti_payment, ModelPayments payments) {
        this.kode_transaksi = kode_transaksi;
        this.tipe_payment = tipe_payment;
        this.bukti_payment = bukti_payment;
        this.payments = payments;
    }
}
