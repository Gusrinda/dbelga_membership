package com.dbelgamembership.membersip.Screen.Limit.ModelPelunasan;

public class DetailTagihan {

    private String id_transaksi;
    private String code_transaksi;
    private String total_transaksi;
    private String total_denda;
    private String grand_total;

    public DetailTagihan() {
    }

    public DetailTagihan(String id_transaksi, String code_transaksi, String total_transaksi, String total_denda, String grand_total) {
        this.id_transaksi = id_transaksi;
        this.code_transaksi = code_transaksi;
        this.total_transaksi = total_transaksi;
        this.total_denda = total_denda;
        this.grand_total = grand_total;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getCode_transaksi() {
        return code_transaksi;
    }

    public void setCode_transaksi(String code_transaksi) {
        this.code_transaksi = code_transaksi;
    }

    public String getTotal_transaksi() {
        return total_transaksi;
    }

    public void setTotal_transaksi(String total_transaksi) {
        this.total_transaksi = total_transaksi;
    }

    public String getTotal_denda() {
        return total_denda;
    }

    public void setTotal_denda(String total_denda) {
        this.total_denda = total_denda;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }
}
