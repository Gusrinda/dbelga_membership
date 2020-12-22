package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dbelgamembership.membersip.Adapter.AdapterListBarang;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelKatalog;

import java.util.ArrayList;
import java.util.List;

public class WishlishActivity extends AppCompatActivity {

    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    private String TAG = "";
    String cariBarang;
    LinearLayout mainLayout, btnSortFilter, layoutSpinner, layoutContentFilter, btnHapusFilter;
    TextView judulAppBar, totalWishlist;
    EditText textCariBarang;
    ImageView btnCari;
    RecyclerView rvBarang;
    SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager layoutManager;
    int checker = 0;
    Spinner spinnerSort, spinnerFilter, spinnerContent;
    String sortData, filterData;
    Boolean filter;
    RelativeLayout layoutWishList, layoutTotalWishlist;

    AdapterListBarang adapterListSearchBarang;
    ArrayList<ModelKatalog> arrayBarang = new ArrayList<ModelKatalog>();
    List<String> arrayKategori = new ArrayList<String>();
    public static String[] stockArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        sessionManager = new SessionManager(this);
        findID();

        rvBarang.setHasFixedSize(false);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
        rvBarang.setLayoutManager(layoutManager);

    }

    private void findID() {

        rvBarang = findViewById(R.id.gridview);

    }
}