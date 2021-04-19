package com.dbelgamembership.membersip.Helper.API;

import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    @Headers("Content-Type: application/json")
    @GET("search-kategori")
    Call<ModelGetKategori> doGetDataKategori();

    @Headers("Content-Type: application/json")
    @GET("list-slider")
    Call<ModelGetSlider> doGetDataSlider();


}
