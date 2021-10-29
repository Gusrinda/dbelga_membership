package com.dbelgamembership.membersip.Screen.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.databinding.ActivityResetPasswordBinding;

public class ResetPassword extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sessionManager = new SessionManager(this);




    }
}