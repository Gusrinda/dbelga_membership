package com.dbelgamembership.membersip.Helper.API;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class APIClient {
    private static final String TAG = APIClient.class.getSimpleName();
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url_Base) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "log: OKHTTP:" + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url_Base)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                //.addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static Retrofit getClientXML(String url_Base) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "log: OKHTTP:" + message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(7000, TimeUnit.SECONDS)
                .readTimeout(7000, TimeUnit.SECONDS)
                .connectTimeout(7000, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(url_Base)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }
}
