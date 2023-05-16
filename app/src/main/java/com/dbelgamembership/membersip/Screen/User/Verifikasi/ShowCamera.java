package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceHolder holder;

    public ShowCamera(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Camera.Parameters parameters = camera.getParameters();

        //Change orientation optic camera
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
            parameters.set("orientation", "potrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        } else {
            parameters.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        }

        camera.setParameters(parameters);
        try {
            SurfaceTexture st = new SurfaceTexture(MODE_PRIVATE);
            camera.setPreviewTexture(st);

            camera.setPreviewDisplay(holder);
            camera.getParameters().getSupportedPreviewSizes();
            camera.startPreview();
            Log.d("TAG", "Camera preview started.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        camera.stopPreview();
//        camera.release();

    }
}
