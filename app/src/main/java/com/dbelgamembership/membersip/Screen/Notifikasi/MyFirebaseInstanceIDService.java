package com.dbelgamembership.membersip.Screen.Notifikasi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.Notifikasi.Model.DataNotifikasi;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    public String TAG = "FIREBASE MESSAGING";
    private SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sessionManager = new SessionManager(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        Log.e(TAG, "DATA " + remoteMessage.getData().toString());

        Gson gson = new Gson();
        DataNotifikasi object = gson.fromJson(remoteMessage.getData().toString(), DataNotifikasi.class);

        Log.e(TAG, "onMessageReceived: TYPE : " + object.getTipe());
        Log.e(TAG, "onMessageReceived: CONTEXT : " + object.getContext());
        Log.e(TAG, "onMessageReceived: ID CONTEXT : " + object.getId());
        Log.e(TAG, "onMessageReceived: CODE CONTEXT : " + object.getCode());


        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            sendNotificationTest(remoteMessage, object);
        }

    }

    private void sendNotificationTest(RemoteMessage remoteMessage, DataNotifikasi object) {


        Intent intent = new Intent(this, SplashActivity.class);
        intent.putExtra("has_extra", true);
        intent.putExtra("data_notifikasi", object);

//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder;
        notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.dbelga)
                        .setContentTitle(Objects.requireNonNull(remoteMessage.getNotification()).getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(1, notificationBuilder.build());

    }


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e(TAG, "onNewToken: " + s);
        setUserToken(s);
    }

    private void setUserToken(String s) {
      APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSetTokenCustomer(
                sessionManager.getPID(),
                s
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.body() != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body());
                        JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                        boolean check = root.get("success").getAsBoolean();
                        if (!check) {
                            Toast.makeText(MyFirebaseInstanceIDService.this, jsonObject.getString("msgServer"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
