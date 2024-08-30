package com.dbelgamembership.membersip.Model;

import com.google.android.gms.maps.model.LatLng;

public class ModelCompetitor {

    private String name;
    private LatLng lokasi;

    public ModelCompetitor() {
    }

    public ModelCompetitor(String name, LatLng lokasi) {
        this.name = name;
        this.lokasi = lokasi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLokasi() {
        return lokasi;
    }

    public void setLokasi(LatLng lokasi) {
        this.lokasi = lokasi;
    }
}
