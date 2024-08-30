package com.dbelgamembership.membersip.Screen.Katalog.Model;

public class modelPostLocation {

    private String idCustomer;
    private String nameCustomer;
    private String lattitude;
    private String longitude;
    private String address;
    private boolean on_area;
    private String competitor;

    public modelPostLocation() {
    }

    public modelPostLocation(String idCustomer, String nameCustomer, String lattitude, String longitude, String address, boolean on_area, String competitor) {
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.address = address;
        this.on_area = on_area;
        this.competitor = competitor;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isOn_area() {
        return on_area;
    }

    public void setOn_area(boolean on_area) {
        this.on_area = on_area;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }
}
