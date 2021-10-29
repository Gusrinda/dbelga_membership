package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.dbelgamembership.membersip.databinding.ActivityCameraGuidelineBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.dm7.barcodescanner.core.CameraPreview;

public class CameraGuideline extends AppCompatActivity {

    private static final String TAG = "CameraGuidelines";
    private ActivityCameraGuidelineBinding binding;

    public static final int MEDIA_TYPE_IMAGE = 1;

    ShowCamera showCamera;
    int kode_Camera;


    // Constant used to identify data sent between Activities.
    public static final String EXTRA_DATA = "";

    private Camera camera;
    private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCameraGuidelineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        kode_Camera = getIntent().getIntExtra("kode_guide", 0);

        try {
            if (kode_Camera == 1) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            }
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }

        showCamera = new ShowCamera(this, camera);

        binding.frameCamera.addView(showCamera);

        Log.e(TAG, "KODE KAMERA: " + kode_Camera);


        if (kode_Camera == 1) {
            binding.incLayoutFotoIdentitas.getRoot().setVisibility(View.VISIBLE);
            binding.textPetunjuk.setText("Lakukan foto kartu identitas anda seperti petunjuk !");
        } else if (kode_Camera == 2) {
            binding.incLayoutFotoWajah.getRoot().setVisibility(View.VISIBLE);
            binding.textPetunjuk.setText("Lakukan selfie wajah seperti petunjuk !");
        } else if (kode_Camera == 3) {
            binding.incLayoutFotoSelfie.getRoot().setVisibility(View.VISIBLE);
            binding.textPetunjuk.setText("Lakukan selfie dengan kartu identitas dibawah wajah seperti petunjuk !");
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
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                Log.e(TAG, "Error creating media file, check storage permissions");
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(bytes);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.e(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "Error accessing file: " + e.getMessage());
            }


            Uri fileFoto = getOutputMediaFileUri(pictureFile);

            String filepath = fileFoto.getPath();

            Intent intent = new Intent();

            intent.putExtra("imageUri", fileFoto.toString());
            intent.putExtra(EXTRA_DATA, filepath);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(File file) {
        return Uri.fromFile(file);
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();              // release the camera immediately on pause event
    }

    private void releaseCamera() {
        if (camera != null) {
            camera.release();        // release the camera for other applications
            camera = null;
        }
    }

}