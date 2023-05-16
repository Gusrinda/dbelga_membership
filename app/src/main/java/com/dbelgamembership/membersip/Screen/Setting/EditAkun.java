package com.dbelgamembership.membersip.Screen.Setting;

import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.daftarGudang;
import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.daftarGudangToko;
import static com.dbelgamembership.membersip.Screen.Katalog.GudangActivity.modelGudangs;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.Model.ModelToko.ModelGudang;
import com.dbelgamembership.membersip.Model.ResponseLogin.ResponseLogin;
import com.dbelgamembership.membersip.Screen.Katalog.GudangActivity;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.Model.ModelUser.ModelUser;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.PembayaranTransfer.TransferTagihan;
import com.dbelgamembership.membersip.Screen.SplashActivity;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.PembayaranMembership;
import com.dbelgamembership.membersip.Screen.User.Verifikasi.VerificationActivity;
import com.dbelgamembership.membersip.databinding.ActivityEditAkunBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

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
    LinearLayout layoutUbahAkun, layoutSimpanAkun, layoutGantiPassword, layoutBatalAkun;
    private String TAG = "";
    String name, address, phone, mail, urlImage, password, dateBirth, namaGudang;

    private ActivityEditAkunBinding binding;

    String[] daftarGudang = new String[modelGudangs.size()];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAkunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkUbah = false;

        sessionManager = new SessionManager(this);
        urlImage = "";
        findID();


        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        for (int i = 0; i < modelGudangs.size(); i++) {
            daftarGudang[i] = modelGudangs.get(i).getNamaGudang();
        }

        Log.e(TAG, "onCreate: " + daftarGudang.toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, daftarGudang);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGudang.setAdapter(adapter);


        layoutUbahAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaPengguna.setEnabled(true);
                nomorPengguna.setEnabled(true);
                alamatPengguna.setEnabled(true);
                binding.namaPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.darkBiruBelga));
                binding.teleponPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.darkBiruBelga));
                binding.alamatPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.darkBiruBelga));

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
                    binding.namaPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.greyBelha));
                    binding.teleponPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.greyBelha));
                    binding.alamatPengguna.setTextColor(ContextCompat.getColor(EditAkun.this, R.color.greyBelha));
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
                android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(EditAkun.this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Batalkan ubah akun ?");
                builder1.setCancelable(false);
                builder1.setPositiveButton(
                        "Ya",
                        new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Drawable image;
                                if (!urlImage.equals("")) {
                                    Glide.with(EditAkun.this).asBitmap().load(urlImage).centerCrop().into(imagePengguna);
                                } else {
                                    image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
                                    imagePengguna.setImageDrawable(image);
                                }

                                namaPengguna.setEnabled(false);
                                emailPengguna.setEnabled(false);
                                nomorPengguna.setEnabled(false);
                                alamatPengguna.setEnabled(false);
                                namaPengguna.setText(name);
                                alamatPengguna.setText(address);
                                nomorPengguna.setText(phone);
                                emailPengguna.setText(mail);
                                btnGantiFoto.setVisibility(View.GONE);
                                layoutUbahAkun.setVisibility(View.VISIBLE);
                                layoutGantiPassword.setVisibility(View.VISIBLE);
                                layoutSimpanAkun.setVisibility(View.GONE);
                                layoutBatalAkun.setVisibility(View.GONE);
                            }
                        });
                builder1.setNegativeButton(
                        "Tidak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                final android.app.AlertDialog alert11 = builder1.create();
                alert11.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        alert11.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                        alert11.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                    }
                });
                alert11.show();
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

                ImagePicker.Companion.with(EditAkun.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });


        binding.spinnerGudang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != selectedIndexGudang) {
                    androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(EditAkun.this).create();
                    alertDialog.setTitle("Peringatan");
                    alertDialog.setMessage("Anda akan mengubah gudang pilihan anda ?");
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE, "YA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    updateGudangUser(i);

                                }
                            });
                    alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "TIDAK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    binding.spinnerGudang.setSelection(selectedIndexGudang);
                                }
                            });
                    alertDialog.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getDataUser();

    }

    private void updateGudangUser(int i) {

        selectedIndexGudang = i;
        String namaGudang = daftarGudang[i];
        String idGudang = "";

        for (int j = 0; j < daftarGudangToko.size(); j++) {
            if (namaGudang.equals(daftarGudangToko.get(j).getNamaGudang())){
                idGudang = daftarGudangToko.get(j).getIdGudang();
            }
        }

        final ProgressDialog progressDialog = ProgressDialog.show(EditAkun.this, "Loading", "Updating gudang ...");
        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<JsonElement> callUpdate = apiInterface.doUpdateGudangCustomer(sessionManager.getPID(), idGudang);

        String finalIdGudang = idGudang;
        callUpdate.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                progressDialog.dismiss();
                try {
                    if (response != null) {
                        sessionManager.setKeyGudangPilihan(finalIdGudang);
                        Toast.makeText(EditAkun.this, "Berhasil update gudang . . .", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onResponse: " + e.getMessage());
                    Toast.makeText(EditAkun.this, "Error Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditAkun.this, "Error Update", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    ModelGudang selectedGudang = new ModelGudang();

    int selectedIndexGudang = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
//            Log.e("TAG", "Path:" + ImagePicker.Companion.getFilePath(data));
            checkUbah = true;
            Uri uri = data.getData();
            ImageUri = uri;

            Log.e(TAG, "onActivityResult: INI IMAGE URI : " + ImageUri.toString() );

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                Log.e(TAG, "onActivityResult: " + bitmap);

//                imagePengguna.setImageBitmap(bitmap);

                Glide.with(EditAkun.this).asBitmap().load(bitmap).error(R.drawable.user_kosong).centerCrop().into(imagePengguna);

            } catch (IOException e) {
                Log.d(TAG, "onActivityResult: MASUK ERROR " + e.getMessage());
                e.printStackTrace();
            }
//            imagePengguna.setImageURI(ImageUri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
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
                            //                                Log.e("", "onResponse: " + response);
                            Gson gson = new Gson();
                            ModelUser modelListUser = gson.fromJson(String.valueOf(response), ModelUser.class);
                            com.dbelgamembership.membersip.Model.ModelUser.MsgServer dataUser = modelListUser.getMsgServer().get(0);

                            name = dataUser.getName();
                            address = dataUser.getMainAddress();
                            phone = dataUser.getMainPhone1();
                            mail = dataUser.getMainEmail();
                            urlImage = dataUser.getImageCustomer();
                            password = dataUser.getPassword();
                            dateBirth = dataUser.getDateBirth();

                            Log.e(TAG, "onResponse: " + dataUser.getImageCustomer() );
                            Log.e(TAG, "onResponse: " + dataUser.getImageCustomer() );


                            namaGudang = "Belum dipilih";

                            if (dataUser.getMainGudang() != null) {


                                for (int i = 0; i < daftarGudangToko.size(); i++) {

                                    if (daftarGudangToko.get(i).getIdGudang().equals(dataUser.getMainGudang())) {
                                        selectedGudang = daftarGudangToko.get(i);
                                    }
                                }



                                for (int i = 0; i < daftarGudang.length; i++) {

                                    if (daftarGudang[i].equals(selectedGudang.getNamaGudang())) {
                                        selectedIndexGudang = i;
                                    }

                                }


                                namaGudang = selectedGudang.getNamaGudang();
                                binding.spinnerGudang.setSelection(selectedIndexGudang);

                            }


                            Log.e(TAG, "checkUbah: " + checkUbah);
                            if (!checkUbah) {
                                if (urlImage.equals("http://149.129.235.50/upload/customer-photo/")) {
                                    urlImage = "";
                                } else {
                                    urlImage = dataUser.getImageCustomer();
                                }
                                Log.e(TAG, "url Image: " + urlImage);
                            }

                            taruhDataUser();

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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(jsonObjectRequest);
    }

    private void taruhDataUser() {
        Log.e(TAG, "taruhDataUser: " + sessionManager );
        namaPengguna.setText(name);
        alamatPengguna.setText(address);
        nomorPengguna.setText(phone);
        emailPengguna.setText(mail);

        Drawable image;
        if (!urlImage.equals("")) {
            Glide.with(EditAkun.this).asBitmap().load(urlImage).error(R.drawable.user_kosong).centerCrop().into(imagePengguna);
        } else {
            image = getApplicationContext().getResources().getDrawable(R.drawable.user_kosong);
            imagePengguna.setImageDrawable(image);
        }

    }

    private void updateAkunUser() {
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

                            bitmap = ((BitmapDrawable) imagePengguna.getDrawable()).getBitmap();

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
                                Log.e(TAG, "imageToString: \n" + gambarPayment);
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
        Log.e(TAG, "simpanAkun: " + postData );
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
//                                    Snack(response.getJSONArray("msgServer").toString());
                                    PeringatanDialog("Error" , root.get("msgServer").getAsString());
                                } else {
                                    JSONObject dataPengguna = response.getJSONObject("msgServer");

                                    Gson gson = new Gson();
                                    ResponseLogin modelUser = gson.fromJson(String.valueOf(response), ResponseLogin.class);

                                    String id = String.valueOf(modelUser.getMsgServer().getId());
                                    String name = modelUser.getMsgServer().getName();
                                    String email = modelUser.getMsgServer().getMainEmail();
                                    String membership = modelUser.getMsgServer().getStatusMember();
                                    String identitasPelanggan = modelUser.getMsgServer().getIdentitas();
                                    String jatuhTempo = modelUser.getMsgServer().getJatuhTempo();
                                    String mainGudang = modelUser.getMsgServer().getMainGudang() == null ? "" : modelUser.getMsgServer().getMainGudang();
                                    Log.e("", "id User: " + id);
                                    Log.e("", "nama User: " + name);
                                    Log.e("", "email User: " + email);
                                    Log.e("", "membership: " + membership);
                                    Log.e("", "identitasPelanggan: " + identitasPelanggan);
                                    Log.e("", "jatuhTempo: " + jatuhTempo);
                                    sessionManager.setLogin(true, id, identitasPelanggan, name, email, membership, jatuhTempo, mainGudang);
                                    sessionManager.setAccountUser(modelUser.getMsgServer().getName(), modelUser.getMsgServer().getMainEmail(), modelUser.getMsgServer().getMainAddress(), modelUser.getMsgServer().getMainPhone1());
                                    if (dataPengguna.getString("image_customer") != null) {
                                        sessionManager.setImage( Http.serverNotApi + "upload/customer-photo/" + dataPengguna.getString("image_customer"));
                                    }

                                }
                                getDataUser();
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

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(15000,
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

    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.NO_WRAP);
    }

    private void Snack(String string) {
        Snackbar snackbar = Snackbar.make(binding.mainLayout, string, Snackbar.LENGTH_LONG)
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

    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(EditAkun.this).create();
        alertDialog.setTitle(judul);
        alertDialog.setMessage(Pesan);
        alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        timer.cancel();
        timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog.dismiss();
                            }
                        });

                    }
                },
                DELAY
        );

    }
}