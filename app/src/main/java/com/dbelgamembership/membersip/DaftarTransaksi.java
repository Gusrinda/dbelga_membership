package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.dbelgamembership.membersip.Adapter.AdapterListTransaksi;
import com.dbelgamembership.membersip.Adapter.AdapterListVoucher;

public class DaftarTransaksi extends AppCompatActivity {
    //TestData
    String detailTransaksi[], jumlahBarang[];

    RecyclerView rvTransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_transaksi);

        findID();
        getDataUser();
    }

    private void getDataUser() {
        getDataTransaksi();
    }

    private void getDataTransaksi() {

        detailTransaksi = getResources().getStringArray(R.array.detail_transaki);
        jumlahBarang = getResources().getStringArray(R.array.jumlah_beli);
        String tanggal = "10-10-2020";

        AdapterListTransaksi listTransaksi = new AdapterListTransaksi(this, jumlahBarang, detailTransaksi);
        rvTransaksi.setAdapter(listTransaksi);
        rvTransaksi.setLayoutManager(new LinearLayoutManager(this));
    }

    private void findID() {

        rvTransaksi = findViewById(R.id.rv_Transaksi);

    }
}