package com.dbelgamembership.membersip.Screen.Katalog.Model;

public class PostBRI {

    String institutionCode;
    String brivaNo;
    String custCode;
    String nama;
    String amount;
    String keterangan;
    String expiredDate;

    public PostBRI(String institutionCode, String brivaNo, String custCode, String nama, String amount, String keterangan, String expiredDate) {
        this.institutionCode = institutionCode;
        this.brivaNo = brivaNo;
        this.custCode = custCode;
        this.nama = nama;
        this.amount = amount;
        this.keterangan = keterangan;
        this.expiredDate = expiredDate;
    }

    public String getInstitutionCode() {
        return institutionCode;
    }

    public void setInstitutionCode(String institutionCode) {
        this.institutionCode = institutionCode;
    }

    public String getBrivaNo() {
        return brivaNo;
    }

    public void setBrivaNo(String brivaNo) {
        this.brivaNo = brivaNo;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
