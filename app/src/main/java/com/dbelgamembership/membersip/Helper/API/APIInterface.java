package com.dbelgamembership.membersip.Helper.API;

import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;
import com.dbelgamembership.membersip.Model.ModelResponseCS.ModelResponseCS;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelResponseDistance.ModelResponseDistance;
import com.dbelgamembership.membersip.Model.ModelToko.ModelToko;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelWish.ModelWish;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.Screen.Katalog.Model.ModelPostSetPayment;
import com.google.gson.JsonElement;

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
    @GET("gudang-list")
    Call<ModelToko> doGetToko();

    @Headers("Content-Type: application/json")
    @GET("get-date")
    Call<JsonElement> doGetDateServer();

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

    @GET("check-phone-email")
    Call<JsonElement> doCheckEmaildanHp(
            @Query("phone") String phone,
            @Query("email") String email
    );

    @FormUrlEncoded
    @POST("verifikasi-member")
    Call<String> doSendVerification(
            @Field("id_member") String idMember,
            @Field("img_identitas") String imgIdentitas,
            @Field("img_wajah") String imgWajah,
            @Field("img_full") String imgFull,
            @Field("img_rumah") String imgRumah,
            @Field("lat_rumah") String latRumah,
            @Field("lon_rumah") String lonRumah,
            @Field("address_rumah") String addressRumah
    );

    @GET
    Call<ModelUser> doLoopCustomer(
           @Url String url
    );

    @GET("search-customer")
    Call<JsonElement> doGetUser();

    @FormUrlEncoded
    @POST("cart-add")
    Call<String> doAddCart(
            @Field("id_member") String idMember,
            @Field("id_gudang") String idGudang,
            @Field("id_item") String idItem,
            @Field("barcode") String barcodeItem,
            @Field("qty_item") double qtyItem
    );

    @FormUrlEncoded
    @POST("cart-delete")
    Call<String> doDeleteCart(
            @Field("id_member") String idMember,
            @Field("id_gudang") String idGudang,
            @Field("id_item") String idItem,
            @Field("barcode") String barcodeItem
    );

    @GET("cart-detail")
    Call<String> doDetailCart(
            @Query("id_member") String idMember);

    @GET("wishlist-search?")
    Call<ModelWish> doDetailWishlistCustomer(
            @Query("customer") String idMember);

    @GET("cart-empty")
    Call<String> doEmptyCart(
            @Query("id_member") String idMember);



    //https://maps.googleapis.com/maps/api/distancematrix/json?
    // origins=-8.054128666081018,%20111.88958097097624
    // &destinations=-8.059757105603692,%20111.90123544028516|-8.06831360533196,%20111.90231540541888&mode=driving&key=AIzaSyC0NMGZYXcRkiWqPGU5hJZ2wOi4Vl7DtRY

    @GET("distancematrix/json?")
    Call<ModelResponseDistance> doGetDistance(
            @Query("origins") String origins,
            @Query("destinations") String destinations,
            @Query("mode") String mode,
            @Query("key") String apiKey);

    @GET("list-cs")
    Call<ModelResponseCS> doGetCs();

    @FormUrlEncoded
    @POST("transaction/update")
    Call<String> doUpdateSO(
            @Field("kode_transaksi") String kodeTransaksi,
            @Field("status_update") String statusUpdate
    );

    @POST("transaction/set-payment")
    Call<String> doSetPayment(
            @Body ModelPostSetPayment json
    );

    @FormUrlEncoded
    @POST("register-customer")
    Call<JsonElement> doRegistrasiMember(
            @Field("pelanggan_nama") String namaPelanggan,
            @Field("pelanggan_kelamin") String kelaminPelanggan,
            @Field("pelanggan_tanggalLahir") String tanggalLahirPelanggan,
            @Field("pelanggan_nomorHp") String hpPelanggan,
            @Field("pelanggan_email") String emailPelanggan,
            @Field("pelanggan_password") String passwordPelanggan,
            @Field("pelanggan_identitas") String nomorIdPelanggan,
            @Field("pelanggan_alamat_ktp") String alamatKTP,
            @Field("pelanggan_alamat") String alamatPelanggan,
            @Field("pelanggan_kota") String kotaPelanggan,
            @Field("pelanggan_kodePos") String kodePosPelanggan,
            @Field("status_member") String statusMember,
            @Field("expired_date") String expiredDateMember,
            @Field("pay_date") String expiredPaymentDate,
            @Field("otp") String OTP,
            @Field("exp_otp") String expOTP
    );

    @GET("check-verifikasi-user")
    Call<ResponseCekVerifikasi> doCekVerifikasiUser(
            @Query("id_user") String idMember);



    @GET("search-katalog")
    Call<JsonElement> doGetKatalogPromo(
            @Query("gudang") String idGudang,
            @Query("name") String textPencarian

    );




}