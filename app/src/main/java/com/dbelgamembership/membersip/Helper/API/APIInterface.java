package com.dbelgamembership.membersip.Helper.API;

import com.dbelgamembership.membersip.Model.ModelBannerPromo.ModelBannerPromo;
import com.dbelgamembership.membersip.Model.ModelDataLimit.DetailLimitUser;
import com.dbelgamembership.membersip.Model.ModelGetKategori.ModelGetKategori;
import com.dbelgamembership.membersip.Model.ModelGetSlider.ModelGetSlider;
import com.dbelgamembership.membersip.Model.ModelListBank.ModelListBank;
import com.dbelgamembership.membersip.Model.ModelListTagihan.ModelListTagihan;
import com.dbelgamembership.membersip.Model.ModelResponseCS.ModelResponseCS;
import com.dbelgamembership.membersip.Model.ModelResponseCart.ModelResponseCart;
import com.dbelgamembership.membersip.Model.ModelResponseDistance.ModelResponseDistance;
import com.dbelgamembership.membersip.Model.ModelSearchVoucher.ModelSearchVoucher;
import com.dbelgamembership.membersip.Model.ModelTagihanUser.ModelTagihanUser;
import com.dbelgamembership.membersip.Model.ModelToko.ModelToko;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.Model.ModelVoucherCustomer.ModelVoucherCustomer;
import com.dbelgamembership.membersip.Model.ModelWish.ModelWish;
import com.dbelgamembership.membersip.Model.ResponseCekVerifikasi.ResponseCekVerifikasi;
import com.dbelgamembership.membersip.Model.ResponseUser.ResponseUser;
import com.dbelgamembership.membersip.Model.responseCancel.ResponseCancel;
import com.dbelgamembership.membersip.Screen.Katalog.Model.ModelPostSetPayment;
import com.dbelgamembership.membersip.Screen.Limit.ModelPelunasan.ModelPelunasan;
import com.google.gson.JsonElement;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface APIInterface {

    @FormUrlEncoded
    @POST("set-token-user")
    Call<String> doSetTokenUser(
            @Field("user_id") String userID,
            @Field("firebase_token") String firebaseToken
    );

    @FormUrlEncoded
    @POST("set-token-customer")
    Call<String> doSetTokenCustomer(
            @Field("user_id") String userID,
            @Field("firebase_token") String firebaseToken
    );


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

    @GET("get-data-limit")
    Call<DetailLimitUser> doGetDetailLimitUser(
            @Query("user_id") String idMember
    );

    @GET("get-tagihan")
    Call<ModelTagihanUser> doGetTagihanPeriodeIni(
            @Query("user_id") String idMember
    );

    @GET("get-list-tagihan")
    Call<ModelListTagihan> doGetListTagihanUser(
            @Query("user_id") String idMember,
            @Query("periode") String periode
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
//            @Field("type_diskon") String tipeDiskon,
//            @Field("qty_diskon") double qtyDiskon
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

    @GET("transaction/cancel")
    Call<ResponseCancel> doCancelTransaksi(
            @Query("code") String codeTransaksi,
            @Query("faktur") String kodeFaktur
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
            @Query("name") String textPencarian,
            @Query("kode_promo") String promoCode
    );

    @GET("list-slider-promo")
    Call<ModelBannerPromo> doGetBannerPromo();

    @GET("list-voucher")
    Call<ModelSearchVoucher> doGetListVoucher();

    @GET("customer-voucher")
    Call<ModelVoucherCustomer> doGetVoucherMember(
            @Query("customer") String idCustomer
    );

    @FormUrlEncoded
    @POST
    Call<JsonElement> doClaimVoucher(
            @Url String url,
            @Field("id_member") String idMember,
            @Field("code_voucher") String kodeVoucher
    );

    @FormUrlEncoded
    @POST
    Call<JsonElement> doRedeemVoucher(
            @Url String url,
            @Field("id_member") String idMember,
            @Field("unique_code") String uniqueKode,
            @Field("voucher_code") String voucherKode
    );

    @GET("list-detail-voucher")
    Call<JsonElement> doGetVoucherCustomer(
            @Query("unik_code") String code
    );

    @GET("banks")
    Call<ModelListBank> doGetListBankTagihan();

    @POST("transaction/set-payment")
    Call<String> doSetPayment(
            @Body ModelPostSetPayment json
    );

    @POST("pelunasan-debet/store")
    Call<String> doPelunasanTagihan(
            @Body ModelPelunasan json
    );

    @GET("search-katalog-terlaris")
    Call<JsonElement> doSearchBarangTerlaris(
            @Query("id_gudang") String idGudang
    );

    @GET("list-tagihan-user/{id}")
    Call<JsonElement> doDaftarTagihanUser(
            @Path("id") String id
    );

    @GET("riwayat-pelunasan-tagihan/{id}")
    Call<JsonElement> doRiwayatPelunasanTagihan(
            @Path("id") String id
    );


    @FormUrlEncoded
    @POST("update-status/{id}")
    Call<JsonElement> doUpdateStatusMembership(
            @Path("id") String id,
            @Field("status_member") String statusMember,
            @Field("pay_date") String payDate
    );

    @FormUrlEncoded
    @POST("upload-payment/{id}")
    Call<JsonElement> doUploadPayment(
            @Path("id") String id,
            @Field("image_pay") String image_pay,
            @Field("code_refferal") String code_referral
    );

}