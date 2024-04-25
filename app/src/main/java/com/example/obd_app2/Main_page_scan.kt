package com.example.obd_app2

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class Main_page_scan : Fragment() {
    private val v: View? = null
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
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                setResult(result.contents)
            }
        }
    }

    private fun setResult(str: String) {
    //Todo: добавити функцію дешифрування + функцію переходу на фрагмент, де виводяться дані
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
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

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_page_scan, container, false)
        val cameraButton: Button = v.findViewById(R.id.main_scan_page_camera_button)
        cameraButton.setOnClickListener{
            checkPermissionCamera(v.context)
        }
        return v
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