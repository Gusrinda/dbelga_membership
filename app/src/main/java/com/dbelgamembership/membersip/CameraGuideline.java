package com.dbelgamembership.membersip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.dbelgamembership.membersip.databinding.ActivityCameraGuidelineBinding;

public class CameraGuideline extends AppCompatActivity {

    private static final String TAG = "CameraGuidelines";
    private ActivityCameraGuidelineBinding binding;

    Camera camera;
    ShowCamera showCamera;
    int kode_Camera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCameraGuidelineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setContentView(view);

        camera = Camera.open();

        showCamera = new ShowCamera(this, camera);

        binding.frameCamera.addView(showCamera);


        kode_Camera = getIntent().getIntExtra("kode_guide", 0);

        Log.e(TAG, "KODE KAMERA: " + kode_Camera );

        if (kode_Camera == 1) {
            binding.incLayoutFotoIdentitas.getRoot().setVisibility(View.VISIBLE);
        } else if (kode_Camera == 2) {
            binding.incLayoutFotoWajah.getRoot().setVisibility(View.VISIBLE);
        } else if (kode_Camera == 3) {
            binding.incLayoutFotoSelfie.getRoot().setVisibility(View.VISIBLE);
        }

        binding.btnAmbilFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (camera != null) {
                    camera.takePicture(null, null, mPictureCallback);
                }
            }
        });

    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {

        }
    };
}