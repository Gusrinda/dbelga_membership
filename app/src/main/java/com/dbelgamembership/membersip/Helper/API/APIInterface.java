package com.dbelgamembership.membersip.Helper.API;

import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @Headers("Content-Type: application/json")
    @GET("search-kategori")
    Call<ModelGetKategori> doGetDataKategori();

    @Headers("Content-Type: application/json")
    @GET("list-slider")
    Call<ModelGetSlider> doGetDataSlider();

    @FormUrlEncoded
    @POST("otp-update")
    Call<String> doUpdateOTP(
            @Field("id_member") String idMember,
            @Field("new_otp") String newOTP,
            @Field("new_exp_otp") String newDateOTP
    );

    @FormUrlEncoded
    @POST("otp-verification")
    Call<String> doVerifikasiOTP(
            @Field("id_member") String idMember,
            @Field("otp") String otp
    );

    @GET
    Call<ModelUser> doLoopCustomer(
           @Url String url
    );

}
