package com.dbelgamembership.membersip.Screen.Katalog.Model;

import java.util.Objects;

public class modelArrayBarangSuplier {

    private String supplier;
    private double qty;
    private double total;

    public modelArrayBarangSuplier() {
    }

    public modelArrayBarangSuplier(String supplier, double qty, double total) {

        this.supplier = supplier;
        this.qty = qty;
        this.total = total;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        modelArrayBarangSuplier that = (modelArrayBarangSuplier) o;
        return Objects.equals(supplier, that.supplier);
    }


    @Override
    public int hashCode() {
        return Objects.hash(supplier);
    }

    public modelArrayBarangSuplier merge(modelArrayBarangSuplier other) {
        assert (this.equals(other));
        return new modelArrayBarangSuplier(this.supplier, this.qty + other.qty , this.total + other.total);
    }


    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
