package com.dbelgamembership.membersip.Screen.User

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.dbelgamembership.membersip.Screen.User.NewCameraGuideline.Constants.FILENAME_FORMAT
import com.dbelgamembership.membersip.Screen.User.NewCameraGuideline.Constants.REQUEST_CODE_CAMERA_PERMISSION
import com.dbelgamembership.membersip.Screen.User.NewCameraGuideline.Constants.TAG
import com.dbelgamembership.membersip.Screen.User.Verifikasi.CameraGuideline
import com.dbelgamembership.membersip.databinding.ActivityNewCameraGuidelineBinding
import com.inyongtisto.myhelper.extension.toGone
import com.inyongtisto.myhelper.extension.toVisible
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma: Double) -> Unit

class NewCameraGuideline : AppCompatActivity() {


    object Constants {
        const val REQUEST_CODE_CAMERA_PERMISSION = 0
        const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val TAG = "CameraXExample"
    }

    object CameraUtility {
        fun hasCameraPermissions(context: Context) =
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                EasyPermissions.hasPermissions(
                    context,
                    Manifest.permission.CAMERA
                )
            } else {
                EasyPermissions.hasPermissions(
                    context,
                    Manifest.permission.CAMERA
                )
            }
    }

    private lateinit var binding: ActivityNewCameraGuidelineBinding

    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var kodeKamera : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewCameraGuidelineBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestPermission()

        // Set up the listener for take photo button
        binding.cameraCaptureButton.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()

        kodeKamera = intent.getIntExtra("kode_guide", 1)

        setupViewGuide(kodeKamera)



    }

    private fun setupViewGuide(codeGuide: Int) {



        binding.apply {
            layoutGuideIdentitas.toGone()
            layoutGuideSelfie.toGone()
            layoutGuideSelfieIdentitas.toGone()

            if (codeGuide == 1) {
                layoutGuideIdentitas.toVisible()
                textPetunjuk.setText("Lakukan foto kartu identitas anda seperti petunjuk !")
            } else if (codeGuide == 2) {
                layoutGuideSelfie.toVisible()
                textPetunjuk.setText("Lakukan selfie wajah seperti petunjuk !")
            } else if (codeGuide == 3) {
                layoutGuideSelfieIdentitas.toVisible()
                textPetunjuk.setText("Lakukan selfie dengan kartu identitas dibawah wajah seperti petunjuk !")
            } else {
                textPetunjuk.setText("Ambil foto rumah anda")
            }
        }

    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            SimpleDateFormat(
                FILENAME_FORMAT, Locale.US
            ).format(System.currentTimeMillis()) + ".jpg"
        )

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)

                    val filepath = savedUri.path

                    val intent = Intent()

                    intent.putExtra("imageUri", savedUri.toString())
                    intent.putExtra(CameraGuideline.EXTRA_DATA, filepath)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        Log.d(TAG, "Average luminosity: $luma")
                    })
                }

            // Select back camera as a default
            var cameraSelector: CameraSelector? = null

            if (kodeKamera == 1 || kodeKamera == 4) {
                cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            } else {
                cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            }

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, "dbelga_membership").apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun requestPermission() {

        if (CameraUtility.hasCameraPermissions(this)) {
            startCamera()
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the camera permission to use this app",
                REQUEST_CODE_CAMERA_PERMISSION,
                Manifest.permission.CAMERA
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept the camera permission to use this app",
                REQUEST_CODE_CAMERA_PERMISSION,
                Manifest.permission.CAMERA

            )
        }

    }


    fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        startCamera()
    }

    fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }


}
