package com.example.obd_app2

import android.content.Context
import android.content.Intent
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_main_page_scan, container, false)
        val cameraButton: Button = v.findViewById(R.id.main_scan_page_camera_button)
        val data = arguments
        val userId = data?.getInt("id")
        cameraButton.setOnClickListener{
            val intent = Intent(activity, Scan_page::class.java).apply {
                putExtra("id", userId)
            }
            startActivity(intent)
        }
        return v
    }

}