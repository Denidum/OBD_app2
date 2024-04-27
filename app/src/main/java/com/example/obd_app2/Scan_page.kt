package com.example.obd_app2

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class Scan_page : AppCompatActivity() {
    private var userId : Int? = null
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
        if(isGranted){
            showCamera()
        }
        else{

        }
    }

    private val scanLauncher = registerForActivityResult(ScanContract()){
            result: ScanIntentResult ->
        run {
            if (result.contents == null) {
                passResult("0")
            } else {
                passResult(result.contents)
            }
        }
    }

    private fun passResult(str: String) {
        val intentVal = Intent(this, Main_page::class.java).apply(){
            putExtra("dataScan", str)
            putExtra("id", userId)
        }
        startActivity(intentVal)
        finish()
    }

    private fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("test prompt")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(true)

        scanLauncher.launch(options)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scan_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        userId = intent.getIntExtra("id", 0)
        checkPermissionCamera(this)
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        }
        else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context, "CAMERA permission required", Toast.LENGTH_SHORT).show()
        }
        else{
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
}