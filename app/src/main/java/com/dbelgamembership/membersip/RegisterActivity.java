package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dbelgamembership.membersip.Helper.Http;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText txt_namaPelanggan, txt_alamatPelanggan, txt_nomorHandphone, txt_tanggalLahir, txt_password, txt_passwordUlang, txt_emailPelanggan;
    Button btnRegister;
    LinearLayout layoutTanggalLahir, btnLogin;

    private ProgressDialog LoadingDialog;

    DatePickerDialog datePickerDialog;
    private String TAG = "";
    String tanggal = "";
    public String url = Http.server, jsonResult, type, user;

    ImageView backArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Date c = new Date();
        SimpleDateFormat af = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date

        tanggal = af.format(cal.getTime());
        LoadingDialog = new ProgressDialog(this);

        findID();

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txt_tanggalLahir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int inType = txt_tanggalLahir.getInputType(); // backup the input type
                txt_tanggalLahir.setInputType(InputType.TYPE_NULL); // disable soft input
                txt_tanggalLahir.onTouchEvent(motionEvent); // call native handler
                txt_tanggalLahir.setInputType(inType); // restore input type
                return true; // consume touch even
            }
        });

        layoutTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txt_tanggalLahir.setFocusable(false);
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                Date curDate = c.getTime();

                datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        String formattedMonth = "" + month;
                        String formattedDayOfMonth = "" + dayOfMonth;
                        if (month < 10) {
                            formattedMonth = "0" + month;
                        }
                        if (dayOfMonth < 10) {
                            formattedDayOfMonth = "0" + dayOfMonth;
                        }

                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, Integer.parseInt(formattedMonth) - 1);
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(formattedDayOfMonth));

                        Date tanggalBorn = c.getTime();
                        String tanggalX = af.format(tanggalBorn);

                        Log.e(TAG, "tanggal Sekarang : " + af.format(curDate));
                        Log.e(TAG, "tanggal Lahir : " + tanggalX);

                        if (tanggalBorn.after(curDate)) {
                            Toast.makeText(RegisterActivity.this, "Tanggal lahir tidak bisa lebih dari hari ini !", Toast.LENGTH_SHORT).show();
                        } else {
                            txt_tanggalLahir.setText(year + "-" + formattedMonth + "-" + formattedDayOfMonth);
                        }
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser() {
        String NamaPelanggan = txt_namaPelanggan.getText().toString();
        String NomorPelanggan = txt_nomorHandphone.getText().toString();
        String AlamatPelanggan = txt_alamatPelanggan.getText().toString();
        String EmailPelanggan = txt_emailPelanggan.getText().toString();
        String PasswordPelanggan = txt_password.getText().toString();
        String PasswordUlangPelanggan = txt_passwordUlang.getText().toString();
        String TanggalLahir = txt_tanggalLahir.getText().toString();

        if (TextUtils.isEmpty(NamaPelanggan)) {
            Toast.makeText(this, "Tolong isi nama anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(NomorPelanggan)) {
            Toast.makeText(this, "Tolong isi nomor telepon anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(AlamatPelanggan)) {
            Toast.makeText(this, "Tolong isi alamat anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordPelanggan)) {
            Toast.makeText(this, "Tolong isi password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(PasswordUlangPelanggan)) {
            Toast.makeText(this, "Tolong tulis kembali password anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(EmailPelanggan)) {
            Toast.makeText(this, "Tolong isi email anda . . . ", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(TanggalLahir)) {
            Toast.makeText(this, "Tolong tanggal lahir anda . . . ", Toast.LENGTH_SHORT).show();
        } else {
            if (!PasswordPelanggan.equals(PasswordUlangPelanggan)) {
                Toast.makeText(this, "Password tidak sama !", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(RegisterActivity.this, MembershipPilih.class);
                intent.putExtra("NAMA_MEMBER", NamaPelanggan);
                intent.putExtra("NOMOR_MEMBER", NomorPelanggan);
                intent.putExtra("EMAIL_MEMBER", EmailPelanggan);
                intent.putExtra("ALAMAT_MEMBER", AlamatPelanggan);
                intent.putExtra("TANGGAL_MEMBER", TanggalLahir);
                intent.putExtra("PASSWORD_MEMBER", PasswordPelanggan);
                startActivity(intent);
            }
        }

    }

    private void findID() {
        txt_namaPelanggan = findViewById(R.id.txt_namaPelanggan);
        txt_alamatPelanggan = findViewById(R.id.txt_alamatPelanggan);
        txt_nomorHandphone = findViewById(R.id.txt_nomorHandphonePelanggan);
        txt_tanggalLahir = findViewById(R.id.txt_tanggalLahir);
        txt_password = findViewById(R.id.txt_passwordPelanggan);
        txt_passwordUlang = findViewById(R.id.txt_passwordUlangPelanggan);
        btnRegister = findViewById(R.id.btnRegister);
        layoutTanggalLahir = findViewById(R.id.layoutTanggalLahir);
        btnLogin = findViewById(R.id.btnLogin);
        txt_emailPelanggan = findViewById(R.id.txt_emailPelanggan);
        backArrow = findViewById(R.id.backArrow);
    }


}