package com.dbelgamembership.membersip.Helper;


/**
 * Created by rizky on 03/09/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.dbelgamembership.membersip.Screen.Log.model.LogModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SessionManager {
    public static final String IS_LOGGEDIN = "isLoggedIn";
    public static final String MEMBERCODE = "MemberCode";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_POINBELANJA = "poinBelanja";
    public static final String KEY_TOTALTRANSAKSI = "transaksiBelanja";
    public static final String KEY_IMAGE = "urlImage";
    public static final String KEY_MEMBERSHIP = "membership";
    public static final String KEY_JATUH_TEMPO = "jatuh_tempo";
    public static final String KEY_EXPDATE = "expired";
    public static final String KEY_DEADLINE_PAYMENT = "expired";
    public static final String KEY_EXPOTP = "expOTP";
    public static final String KEY_ROLE = "role";
    public static final String KEY_TOKEN_GCM = "token_gcm";
    private static final String KEY_ID_USER = "key";
    private static final String KEY_GUDANG_PILIHAN = "gudang_pilihan";
    private static final String KEY_PID = "pid";
    private static final String KEY_USERIDENTITAS = "id_user";
    private static final String KEY_BANNER = "false";
    private static final String KEY_TOKEN = "Bearer";
    private static final String KEY_MEMBERID = "memberoid";
    private static final String KEY_ALAMAT_MEMBER = "alamatMember";
    private static final String KEY_TELEFON_MEMBER = "telfMember";
    private static final String KEY_ALAMAT_PENGIRIMAN = "alamatPengiriman";
    private static final String KEY_LAT = "alamatLat";
    private static final String KEY_LONG = "alamatLong";
    private static final String KEY_SET_GUDANG_PENCARIAN = "idGudang";
    private static final String KEY_SISA_PLAFON = "0";
    private static final String KEY_TOKEN_BRI_API = "Bearer";


    private static final String KEY_LIST_LOG = "KEY_LIST_LOG";

    private static String TAG = com.dbelgamembership.membersip.Helper.SessionManager.class.getSimpleName();
    SharedPreferences pref;
    Editor editor;
    Context _context;

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("user_details", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.apply();
    }

    public void setAwalListLogHistory() {
        editor.putString(KEY_LIST_LOG, "");
        editor.apply();
    }

    public void addLogHistory(LogModel logModel) {
        String serializedObject = pref.getString(KEY_LIST_LOG, null);
        Type listOfMyClassObject = new TypeToken<ArrayList<LogModel>>() {
        }.getType();
        Gson gson = new Gson();

        ArrayList<LogModel> daftarLog = new ArrayList<>();
        daftarLog = gson.fromJson(serializedObject, listOfMyClassObject);

        if (daftarLog != null) {

            Log.e(TAG, "addListUniqueKey: DAFTAR LOG SEKARANG : \n" + Arrays.toString(daftarLog.toArray()));
        } else {
            Log.e(TAG, "addListUniqueKey: DAFTAR LOG SEKARANG KOSONG ! : \n");

        }

        if (daftarLog == null) {
            daftarLog = new ArrayList<LogModel>();
            daftarLog.add(logModel);
        } else {
            daftarLog.add(logModel);
        }


        String json = gson.toJson(daftarLog);
        editor.putString(KEY_LIST_LOG, json);
        editor.apply();
        editor.commit();
    }


    public List<LogModel> getDaftarLogHistory() {

        String serializedObject = pref.getString(KEY_LIST_LOG, null);
        Log.e(TAG, "getDaftarLog :: " + serializedObject);

        Type listOfMyClassObject = new TypeToken<ArrayList<LogModel>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.fromJson(serializedObject, listOfMyClassObject);

    }


    public void setLogin(boolean isLoggedIn, String pid, String identitasPelanggan, String name, String email, String membership, String jatuhTempo, String gudangPilihan) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_PID, pid);
        editor.putString(KEY_USERIDENTITAS, identitasPelanggan);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MEMBERSHIP, membership);
        editor.putString(KEY_JATUH_TEMPO, jatuhTempo);
        editor.putString(KEY_GUDANG_PILIHAN, gudangPilihan);
        // commit changes
        editor.apply();

        Log.d(TAG, "User login session modified!");
    }

    public void setAccountUser(String name, String email, String alamat, String nomorTelp) {
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_ALAMAT_MEMBER, alamat);
        editor.putString(KEY_TELEFON_MEMBER, nomorTelp);
        editor.apply();
    }

    public void setAlamatPengiriman(String alamatPengiriman) {
        editor.putString(KEY_ALAMAT_PENGIRIMAN, alamatPengiriman);
        editor.apply();
    }

    public void setKeyTokenBriApi(String token) {
        editor.putString(KEY_TOKEN_BRI_API, token);
        editor.apply();
    }

    public void setKeyDeadlinePayment(String deadlinePayment) {
        editor.putString(KEY_DEADLINE_PAYMENT, deadlinePayment);
        editor.apply();
    }

    public void setLatLong(String lat, String lon) {
        editor.putString(KEY_LAT, lat);
        editor.putString(KEY_LONG, lon);
        editor.apply();
    }

    public void setRegister(boolean isLoggedIn, String pid, String identitasPelanggan, String name, String email, String membership, String expired) {
        editor.putBoolean(IS_LOGGEDIN, isLoggedIn);
        editor.putString(KEY_PID, pid);
        editor.putString(KEY_USERIDENTITAS, identitasPelanggan);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MEMBERSHIP, membership);
        editor.putString(KEY_EXPDATE, expired);
        // commit changes
        editor.apply();

        Log.d(TAG, "User login session modified!");
    }

    public void setMembership(String membership) {
        editor.putString(KEY_MEMBERSHIP, membership);
        editor.commit();
    }

    public void setKeyJatuhTempo(String jatuhTempo) {
        editor.putString(KEY_JATUH_TEMPO, jatuhTempo);
        editor.commit();
    }

    public void setExpiredDate(String expiredDate) {
        editor.putString(KEY_EXPDATE, expiredDate);
        editor.commit();
    }


    public void setKeyExpotp(String expiredOTP) {
        editor.putString(KEY_EXPOTP, expiredOTP);
        editor.commit();
    }


    public void setImage(String image) {
        editor.putString(KEY_IMAGE, image);
        editor.commit();
    }

    public void setKeyGudangPilihan(String gudangPencarian) {
        editor.putString(KEY_GUDANG_PILIHAN, gudangPencarian);
        editor.commit();
    }


    public void setKeySetGudangPencarian(String gudangPencarian) {
        editor.putString(KEY_SET_GUDANG_PENCARIAN, gudangPencarian);
        editor.commit();
    }

    public String getKeySetGudangPencarian() {
        return pref.getString(KEY_SET_GUDANG_PENCARIAN, "null");
    }

    public void setKeySisaPlafon(String sisaPlafon) {
        editor.putString(KEY_SISA_PLAFON, sisaPlafon);
        editor.commit();
    }

    public String getSisaPlafon() {
        return pref.getString(String.valueOf(KEY_SISA_PLAFON), "null");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGGEDIN, false);
    }

    public String getName() {
        return pref.getString(KEY_NAME, "null");
    }

    public String getPID() {
        return pref.getString(KEY_PID, "null");
    }

    public String getKeyUseridentitas() {
        return pref.getString(KEY_USERIDENTITAS, "null");
    }

    public String getKey() {
        return pref.getString(KEY_ID_USER, "null");
    }

    public String getEmail() {
        return pref.getString(KEY_EMAIL, "null");
    }

    public String getImage() {
        return pref.getString(KEY_IMAGE, "null");
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, "null");
    }

    public String getRole() {
        return pref.getString(KEY_ROLE, "null");
    }

    public String getKeyPoinbelanja() {
        return pref.getString(KEY_POINBELANJA, "null");
    }

    public String getKeyTotaltransaksi() {
        return pref.getString(KEY_TOTALTRANSAKSI, "null");
    }

    public String getBanner() {
        return pref.getString(KEY_BANNER, "true");
    }

    public String getMembership() {
        return pref.getString(KEY_MEMBERSHIP, "null");
    }

    public String getKeyJatuhTempo() {
        return pref.getString(KEY_JATUH_TEMPO, "null");
    }

    public String getExpiredDate() {
        return pref.getString(KEY_EXPDATE, "null");
    }

    public String getKeyExpotp() {
        return pref.getString(KEY_EXPOTP, "null");
    }

    public String getKeyMemberid() {
        return pref.getString(KEY_MEMBERID, "null");
    }

    public String getKeyAlamatMember() {
        return pref.getString(KEY_ALAMAT_MEMBER, "null");
    }

    public String getKeyAlamatPengiriman() {
        return pref.getString(KEY_ALAMAT_PENGIRIMAN, "null");
    }

    public String getKeyLat() {
        return pref.getString(KEY_LAT, "null");
    }

    public String getKeyLong() {
        return pref.getString(KEY_LONG, "null");
    }

    public String getKeyTelefonMember() {
        return pref.getString(KEY_TELEFON_MEMBER, "null");
    }

    public String getKeyDeadlinePayment() {
        return pref.getString(KEY_DEADLINE_PAYMENT, "null");
    }

    public String getKeyTokenBriApi() {
        return pref.getString(KEY_TOKEN_BRI_API, "null");
    }


    public String getKeyGudangPilihan() {
        return pref.getString(KEY_GUDANG_PILIHAN, "null");
    }

    public void destroySession() {
        editor.putString(KEY_PID, "");
        editor.putString(KEY_NAME, "");
        editor.putBoolean(IS_LOGGEDIN, false);
        editor.putString(MEMBERCODE, "");
        editor.putString(KEY_TOKEN_GCM, "");
        editor.putString(KEY_TOKEN, "");
        editor.putString(KEY_IMAGE, "");
        editor.putString(KEY_POINBELANJA, "");
        editor.putString(KEY_TOTALTRANSAKSI, "");
        editor.putString(KEY_MEMBERSHIP, "");
        editor.putString(KEY_EXPDATE, "");
        editor.putString(KEY_DEADLINE_PAYMENT, "");
        editor.putString(KEY_USERIDENTITAS, "");
        editor.putString(KEY_BANNER, "true");
        editor.putString(KEY_MEMBERID, "");
        editor.putString(KEY_EXPOTP, "");
        editor.putString(KEY_TELEFON_MEMBER, "");
        editor.putString(KEY_ALAMAT_MEMBER, "");
        editor.putString(KEY_ALAMAT_PENGIRIMAN, "");
        editor.putString(KEY_LAT, "");
        editor.putString(KEY_LONG, "");
        editor.putString(KEY_SET_GUDANG_PENCARIAN, "");
        editor.putString(KEY_SISA_PLAFON, "");
        editor.putString(KEY_TOKEN_BRI_API, "");
        editor.putString(KEY_JATUH_TEMPO, "");
        editor.putString(KEY_GUDANG_PILIHAN, "");
        editor.clear();
        editor.apply();
        Log.d(TAG, "User login session destroyed!");

    }


    public String getData(String key) {
        return pref.getString(key, "");
    }


    public String getKeyToken() {
        return pref.getString(KEY_TOKEN, "");
    }
}

