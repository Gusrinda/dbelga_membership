package com.dbelgamembership.membersip.Screen.User.Verifikasi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.dbelgamembership.membersip.Helper.API.APIClient;
import com.dbelgamembership.membersip.Helper.API.APIInterface;
import com.dbelgamembership.membersip.Helper.Http;
import com.dbelgamembership.membersip.Helper.SessionManager;
import com.dbelgamembership.membersip.R;
import com.dbelgamembership.membersip.Screen.MainActivity;
import com.dbelgamembership.membersip.databinding.ActivityMembershipFotoBinding;
import com.developer.kalert.KAlertDialog;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MembershipFoto extends AppCompatActivity {

    private static final String TAG = "MEMBERSHIP FOTO";
    private ActivityMembershipFotoBinding binding;
    private Bitmap bitmap;
    private SessionManager sessionManager;

    private static boolean selfie = false;
    private ImagePopup imagePopup;
    private ProgressDialog progressDialog;

    private Uri uriFotoIdentitas, uriFotoWajah, uriFotoSelfie;

    String fotoIdentitas = "";
    String fotoWajah = "";
    String fotoSelfie = "";

    @Override
    public void onBackPressed() {
        new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                .setTitleText("Keluar")
                .setContentText("Keluar dari halaman ini akan menyebabkan semua proses pendaftaran member debet hilang. Anda yakin ?")
                .setConfirmText("Ya")
                .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .setCancelText("Tidak")
                .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMembershipFotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(this);

        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_black_24);
//        verifBinding.toolbar.setNavigationIcon
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Keluar")
                        .setContentText("Keluar dari halaman ini akan menyebabkan semua proses pendaftaran member debet hilang. Anda yakin ?")
                        .setConfirmText("Ya")
                        .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                        .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finish();
                            }
                        })
                        .setCancelText("Tidak")
                        .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        binding.btnUploadFotoIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                selfie = false;
                intent.putExtra("kode_guide", 1);
                startActivityForResult(intent, 101);
//                startActivity(intent);
            }
        });

        binding.btnUploadFotoWajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                selfie = true;
                intent.putExtra("kode_guide", 2);
//                startActivity(intent);
                startActivityForResult(intent, 102);

            }
        });

        binding.btnUploadFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MembershipFoto.this, CameraGuideline.class);
                selfie = true;

                intent.putExtra("kode_guide", 3);
//                startActivity(intent);
                startActivityForResult(intent, 103);
            }
        });

        imagePopup = new ImagePopup(this);
        imagePopup.setWindowHeight(1200); // Optional
        imagePopup.setWindowWidth(800); // Optional
        imagePopup.setBackgroundColor(Color.TRANSPARENT);  // Optional
        imagePopup.setHideCloseIcon(false);  // Optional
        imagePopup.setImageOnClickClose(true);  // Optional

        binding.imgFotoIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoIdentitas.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.imgFotoWajah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoWajah.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.imgFotoSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.initiatePopup(binding.imgFotoSelfie.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup();
            }
        });

        binding.btnKirimIdentitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uriFotoIdentitas == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto identitas terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else if (uriFotoWajah == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto wajah terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else if (uriFotoSelfie == null) {
                    Toast.makeText(MembershipFoto.this, "Ambil foto selfie dengan identitas terlebih dahulu !", Toast.LENGTH_SHORT).show();
                } else {
                    new KAlertDialog(MembershipFoto.this, KAlertDialog.WARNING_TYPE)
                            .setTitleText("Verifikasi")
                            .setContentText("Anda akan mengirim foto anda untuk proses verifikasi member ?")
                            .setConfirmText("Ya")
                            .confirmButtonColor(R.color.biruBelga, MembershipFoto.this)
                            .cancelButtonColor(R.color.grey_font, MembershipFoto.this)
                            .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    sendDataVerifikasi(fotoIdentitas, fotoWajah, fotoSelfie);
                                }
                            })
                            .setCancelText("Tidak")
                            .setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                                @Override
                                public void onClick(KAlertDialog kAlertDialog) {
                                    kAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            }
        });
    }

    public void save(String fileText, String fileName) {
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(fileName + ".txt", MODE_PRIVATE);
            fos.write(fileText.getBytes());

//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + fileName + ".txt", Toast.LENGTH_LONG).show();
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

    private void sendDataVerifikasi(String fotoIdentitas, String fotoWajah, String fotoSelfie) {
        progressDialog = ProgressDialog.show(MembershipFoto.this, "Loading", "Please Wait...");

        APIInterface apiInterface = APIClient.getClient(Http.server).create(APIInterface.class);
        Call<String> call = apiInterface.doSendVerification(sessionManager.getPID(), fotoIdentitas, fotoWajah, fotoSelfie);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body());
                    JsonObject root = new JsonParser().parse(String.valueOf(response.body())).getAsJsonObject();
                    boolean check = root.get("success").getAsBoolean();
                    if (!check) {
                        PeringatanDialog("Error", jsonObject.getString("msgServer"));
                    } else {
                        Toast.makeText(MembershipFoto.this, "Tunggu verifikasi admin dalam 1x24 jam !", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(MembershipFoto.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        byte[] imgBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.NO_WRAP);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == 101) {
                //CAMERA FOTO IDENTITAS
                final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                Log.e(TAG, "onActivityResult: FOTO IDENTITAS  -> " + result);
                Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);
                uriFotoIdentitas = myUri;
                try {
                    fotoIdentitas = imageToString(MediaStore.Images.Media.getBitmap(getContentResolver(), uriFotoIdentitas));
                    save(fotoIdentitas, "fotoIdentitas");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                setView(101, myUri);
            } else if (requestCode == 102) {
                //CAMERA SELFIE
                final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                Log.e(TAG, "onActivityResult: FOTO SELFIE  -> " + result);
                Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);
                uriFotoWajah = myUri;
                try {
                    fotoWajah = imageToString(MediaStore.Images.Media.getBitmap(getContentResolver(), uriFotoWajah));

                    save(fotoWajah, "fotoWajah");


                } catch (IOException e) {
                    e.printStackTrace();
                }
                setView(102, myUri);
            } else if (requestCode == 103) {
                //CAMERA SELFIE WITH ID
                final String result = data.getStringExtra(String.valueOf(CameraGuideline.EXTRA_DATA));
                Log.e(TAG, "onActivityResult: FOTO SELFIE ID  -> " + result);
                Uri myUri = Uri.parse(data.getStringExtra("imageUri"));
                Log.e(TAG, "onActivityResult: URI FOTO  -> " + myUri);
                uriFotoSelfie = myUri;
                try {
                    fotoSelfie = imageToString(MediaStore.Images.Media.getBitmap(getContentResolver(), uriFotoSelfie));
                    save(fotoSelfie, "fotoSelfie");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setView(103, myUri);
            }

        }

    }

    private void setView(int i, Uri myUri) {
        try {
            bitmap = handleSamplingAndRotationBitmap(this, myUri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (i == 101) {
            binding.imgFotoIdentitas.setImageBitmap(bitmap);
            binding.imgFotoIdentitas.setVisibility(View.VISIBLE);
            binding.imgCheckFotoIdentitas.setVisibility(View.VISIBLE);
        } else if (i == 102) {
            binding.imgFotoWajah.setImageBitmap(bitmap);
            binding.imgFotoWajah.setVisibility(View.VISIBLE);
            binding.imgCheckFotoWajah.setVisibility(View.VISIBLE);
        } else if (i == 103) {
            binding.imgFotoSelfie.setImageBitmap(bitmap);
            binding.imgFotoSelfie.setVisibility(View.VISIBLE);
            binding.imgCheckFotoSelfie.setVisibility(View.VISIBLE);
        }

        selfie = false;

    }


    /**
     * This method is responsible for solving the rotation issue if exist. Also scale the images to
     * 1024x1024 resolution
     *
     * @param context       The current context
     * @param selectedImage The Image URI
     * @return Bitmap image results
     * @throws IOException
     */
    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(img, selectedImage);
        return img;
    }

    /**
     * Calculate an inSampleSize for use in a {@link BitmapFactory.Options} object when decoding
     * bitmaps using the decode* methods from {@link BitmapFactory}. This implementation calculates
     * the closest inSampleSize that will result in the final decoded bitmap having a width and
     * height equal to or larger than the requested width and height. This implementation does not
     * ensure a power of 2 is returned for inSampleSize which can be faster when decoding but
     * results in a larger bitmap which isn't as useful for caching purposes.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     *                  method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    /**
     * Rotate an image if required.
     *
     * @param img           The image bitmap
     * @param selectedImage Image URI
     * @return The resulted Bitmap after manipulation
     */
    private static Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {

        ExifInterface ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

//        if (selfie) {
//            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_FLIP_HORIZONTAL);
//        } else {
//            orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
//        }

        Log.e(TAG, "rotateImageIfRequired: " + orientation);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        if (selfie) {
            matrix.postRotate(degree + 180);
            matrix.postScale(-1, 1);
        } else {
            matrix.postRotate(degree);
        }
//        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    private void PeringatanDialog(String judul, String Pesan) {
        Timer timer = new Timer();
        final long DELAY = 2000; // milliseconds
        AlertDialog alertDialog = new AlertDialog.Builder(MembershipFoto.this).create();
        alertDialog.setTitle(judul);
        alertDialog.setMessage(Pesan);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
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