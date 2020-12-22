package com.dbelgamembership.membersip;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditAkun extends AppCompatActivity {

    SessionManager sessionManager;
    public String url = Http.server, jsonResult, type, user, pass;
    private static final int GalleryPick = 1;
    private Uri ImageUri;

    private static final String FILE_NAME = "example.txt";
    String x;
    String backslash;


    Boolean checkUbah;
    Bitmap bitmap;
    TextInputEditText namaPengguna, alamatPengguna, nomorPengguna, emailPengguna;
    CircleImageView imagePengguna;
    ImageView btnGantiFoto;
    LinearLayout layoutUbahAkun, layoutSimpanAkun, layoutGantiPassword, layoutBatalAkun, mainLayout;
    private String TAG = "";
    String name, address, phone, mail, urlImage, password, dateBirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        checkUbah = false;

        sessionManager = new SessionManager(this);
        urlImage = "";
        findID();
        getDataUser();

        layoutUbahAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaPengguna.setEnabled(true);
                emailPengguna.setEnabled(true);
                nomorPengguna.setEnabled(true);
                alamatPengguna.setEnabled(true);
                btnGantiFoto.setVisibility(View.VISIBLE);
                layoutUbahAkun.setVisibility(View.GONE);
                layoutGantiPassword.setVisibility(View.GONE);
                layoutSimpanAkun.setVisibility(View.VISIBLE);
                layoutBatalAkun.setVisibility(View.VISIBLE);
            }
        });

        layoutSimpanAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = namaPengguna.getText().toString();
                phone = nomorPengguna.getText().toString();
                address = alamatPengguna.getText().toString();
                mail = emailPengguna.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(EditAkun.this, "Tolong isi nama anda . . .", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(EditAkun.this, "Tolong isi nomor anda . . .", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address)) {
                    Toast.makeText(EditAkun.this, "Tolong isi alamat anda . . .", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mail)) {
                    Toast.makeText(EditAkun.this, "Tolong isi email anda . . .", Toast.LENGTH_SHORT).show();
                } else {
                    updateAkunUser();
                    namaPengguna.setEnabled(false);
                    emailPengguna.setEnabled(false);
                    nomorPengguna.setEnabled(false);
                    alamatPengguna.setEnabled(false);
                    btnGantiFoto.setVisibility(View.GONE);
                    layoutUbahAkun.setVisibility(View.VISIBLE);
                    layoutGantiPassword.setVisibility(View.VISIBLE);
                    layoutSimpanAkun.setVisibility(View.GONE);
                    layoutBatalAkun.setVisibility(View.GONE);
                }

            }
        });

        layoutBatalAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!urlImage.equals("")) {
                    Glide.with(EditAkun.this).asBitmap().load(urlImage).centerCrop().into(imagePengguna);
                }
                namaPengguna.setEnabled(false);
                emailPengguna.setEnabled(false);
                nomorPengguna.setEnabled(false);
                alamatPengguna.setEnabled(false);
                btnGantiFoto.setVisibility(View.GONE);
                layoutUbahAkun.setVisibility(View.VISIBLE);
                layoutGantiPassword.setVisibility(View.VISIBLE);
                layoutSimpanAkun.setVisibility(View.GONE);
                layoutBatalAkun.setVisibility(View.GONE);
            }
        });

        layoutGantiPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditAkun.this, UbahPassword.class);
                startActivity(intent);
            }
        });

        btnGantiFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CropImage.activity(ImageUri)
                        .setAspectRatio(1, 1)
                        .start(EditAkun.this);

//                OpenGallery();
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            checkUbah = true;
            ImageUri = result.getUri();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imagePengguna.setImageURI(ImageUri);
        } else {
            Toast.makeText(this, "ERROR : Try Again !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditAkun.this, EditAkun.class));
            finish();
        }

//        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
//            ImageUri = data.getData();
//            try {
//                checkUbah = true;
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
//                imagePengguna.setImageURI(ImageUri);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }

    private void getDataUser() {
        url = Http.server;
        url = url + "search-customer/" + sessionManager.getPID();
        final ProgressDialog dialog1 = new ProgressDialog(EditAkun.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog1.dismiss();
                        if (response != null) {
                            Log.e("", "onResponse: " + response);
                            try {
//                                String responseX = String.valueOf(response);
//                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
//                                boolean success = root.get("success").getAsBoolean();
//                                Log.e("", "Test : " + success);

                                JSONObject jsonObject = response.getJSONObject("msgServer");
                                name = jsonObject.getString("name");
                                address = jsonObject.getString("main_address");
                                phone = jsonObject.getString("main_phone_1");
                                mail = jsonObject.getString("main_email");
                                urlImage = jsonObject.getString("image_customer");

                                password = jsonObject.getString("password");
                                dateBirth = jsonObject.getString("date_birth");

                                Log.e(TAG, "checkUbah: " + checkUbah);
                                if (checkUbah == false) {
                                    if (urlImage.equals("http://54.254.194.122/upload/customer-photo/")) {
                                        urlImage = "";
                                    } else {
                                        urlImage = jsonObject.getString("image_customer");
                                    }
                                    Log.e(TAG, "url Image: " + urlImage);
                                }

                                taruhDataUser();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(EditAkun.this, "Tidak ada response", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        dialog1.dismiss();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void taruhDataUser() {
        namaPengguna.setText(name);
        alamatPengguna.setText(address);
        nomorPengguna.setText(phone);
        emailPengguna.setText(mail);
        if (!urlImage.equals("")) {
            Glide.with(this).asBitmap().load(urlImage).centerCrop().into(imagePengguna);
        }

    }

    private void updateAkunUser() {
//        x = imageToString(bitmap);
//        int maxLogStringSize = x.length();
//        for (int i = 0; i <= x.length() / maxLogStringSize; i++) {
//            int start = i * maxLogStringSize;
//            int end = (i + 1) * maxLogStringSize;
//            end = end > x.length() ? x.length() : end;
//            Log.e(TAG, x.substring(start, end));
//        }


        AlertDialog.Builder builder1 = new AlertDialog.Builder(EditAkun.this);
        builder1.setTitle("Konfirmasi");
        builder1.setMessage("Simpan perubahan akun anda ?");
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                "Ya",
                new DialogInterface.OnClickListener() {
                    @SuppressLint("NewApi")
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if (isOnline()) {
                            url = Http.server;
                            url = url + "edit-customer/" + sessionManager.getPID();
                            type = "post";
                            JSONObject postData = new JSONObject();
                            String gambarPayment;
                            String kodeReferal;

                            if (bitmap == null) {
                                gambarPayment = "";
                            } else {
                                gambarPayment = imageToString(bitmap);
                            }


                            try {
                                postData.put("name", name);
                                postData.put("main_phone_1", phone);
                                postData.put("main_email", mail);
                                postData.put("main_address", address);
                                postData.put("date_birth", dateBirth);
                                postData.put("password", password);
                                postData.put("image_customer", gambarPayment);
                                Log.e(TAG, "imageToString: \n" + x);
                            } catch (Exception e) {
                                e.getMessage();
                            }
                            if (isOnline()) {
                                Log.e(TAG, "onClickSubmit: " + postData.toString());
                                simpanAkun(postData);
                            }
                        } else {
                            Snack("Cek Koneksi Internet Anda");
                        }
                    }
                });

        builder1.setNegativeButton(
                "Tidak",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert11 = builder1.create();
        alert11.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert11.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert11.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert11.show();
    }

    public void save() {
//        String text = x;

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(backslash.getBytes());
//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,
//                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void simpanAkun(JSONObject postData) {
        final ProgressDialog dialog1 = new ProgressDialog(EditAkun.this);
        dialog1.setCancelable(false);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.setMessage("Harap Menunggu...");
        dialog1.show();
        RequestQueue mQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dialog1.dismiss();
                            if (response != null) {
                                Log.e(TAG, "URL " + url);
                                Log.e(TAG, "onResponseSimpan: " + response);
                                String responseX = String.valueOf(response);
                                JsonObject root = new JsonParser().parse(responseX).getAsJsonObject();
                                boolean success = root.get("success").getAsBoolean();
                                Log.e("", "Test : " + success);
                                if (success == false) {
                                    Snack(response.getJSONArray("msgServer").toString());
                                } else {
                                    JSONObject dataPengguna = response.getJSONObject("msgServer");
                                    String id = dataPengguna.getString("id");
                                    String name = dataPengguna.getString("name");
                                    String email = dataPengguna.getString("main_email");
                                    String membership = dataPengguna.getString("status_member");
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    sessionManager.setLogin(true, id, name, email, membership);
                                    if (dataPengguna.getString("image_customer") != null) {
                                        sessionManager.setImage("http://54.254.194.122/upload/customer-photo/" + dataPengguna.getString("image_customer"));
                                    }
                                    Intent intent = new Intent(EditAkun.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            Snack(e.getMessage());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
                dialog1.dismiss();
                if (error instanceof AuthFailureError) {
                    sessionManager.destroySession();
                    Intent intent = new Intent(getApplicationContext(), EditAkun.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (error instanceof ServerError) {
                    Snack("Terjadi Kesalahan.");
                } else if (error instanceof NetworkError) {
                    Snack("Tidak Ada Koneksi Internet");
                } else if (error instanceof ParseError) {
                    Snack(error.getMessage());
                } else {
                    Snack(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
//                params.put("Content-Type", "application/json");
                params.put("Content-Type", "application/json; charset=utf-8");
//                params.put("type", "create");
                params.put("Authorization", "Bearer " + sessionManager.getKeyToken());
                return params;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.e(TAG, "parseNetworkResponse: " + response.statusCode);
                return super.parseNetworkResponse(response);
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void findID() {
        namaPengguna = findViewById(R.id.namaPengguna);
        alamatPengguna = findViewById(R.id.alamatPengguna);
        nomorPengguna = findViewById(R.id.teleponPengguna);
        emailPengguna = findViewById(R.id.emailPengguna);
        imagePengguna = findViewById(R.id.imgPengguna);
        btnGantiFoto = findViewById(R.id.ubahImage);
        layoutUbahAkun = findViewById(R.id.layoutUbahAkun);
        layoutSimpanAkun = findViewById(R.id.layoutSimpanAkun);
        layoutGantiPassword = findViewById(R.id.layoutUbahPassword);
        layoutBatalAkun = findViewById(R.id.layoutBatalSimpan);
        mainLayout = findViewById(R.id.mainLayout);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);

    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(mainLayout, string, Snackbar.LENGTH_LONG)
                .setAction("Action", null);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(getResources().getColor(R.color.merahBelga));
        snackbar.show();
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}