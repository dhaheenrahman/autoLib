package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.google.android.material.textfield.TextInputEditText
import com.google.zxing.Result
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import java.text.SimpleDateFormat
import java.util.*

import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

class QrScanner : AppCompatActivity() {

    private lateinit var barcodeView: CompoundBarcodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.qrscanner)
        barcodeView = findViewById(R.id.barcodeView)
        barcodeView.decodeSingle(callback)
        barcodeView.setStatusText("Place the QR pasted on the book in the rectangle")

    }


    override fun onResume() {
        super.onResume()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            barcodeView.resume()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onPause() {
        super.onPause()
        barcodeView.pause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                barcodeView.resume()
            } else {
                // Permission denied
                Log.e(TAG, "Camera permission denied")
            }
        }
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            // QR code scan result
            val barcode: Result? = result.result
            if (barcode != null) {
                Log.d(TAG, "QR code scanned: " + barcode.text)
                barcodeView.pause()

                val intent = Intent(this@QrScanner ,BookIssue::class.java)
                val str = barcode.text
                intent.putExtra("barcodevalue", str)
                startActivity(intent)
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
}


