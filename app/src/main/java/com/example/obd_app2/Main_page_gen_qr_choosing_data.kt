package com.example.obd_app2

import Adapters.DataListDeleteAdapter
import Table_or_data_classes.Data_list_row
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.obd_app2.interfaces.ChooseTableOrDataToDelete
import com.example.obd_app2.interfaces.Main_to_secondary_frags
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.IOException
import java.lang.RuntimeException


class Main_page_gen_qr_choosing_data : Fragment(), ChooseTableOrDataToDelete{//мені було впадлу робити новий інтерфейс

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionsLauncher: ActivityResultLauncher<Array<String>>

    private var myInterface: Main_to_secondary_frags? = null
    private var columnData = arrayListOf<Data_list_row>()
    private var userId: Int? = null
    private var tableId: Int? = null
    private var selectedRowId: Int? = -1
    private var adapter: DataListDeleteAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_main_page_gen_qr_choosing_data, container, false)

        val data = arguments
        userId = data?.getInt("id")
        tableId = data?.getInt("tableId")

        myInterface = activity as Main_to_secondary_frags

        val dataList: ListView = v.findViewById(R.id.gen_qr_page_choosing_data_list)

        //Todo:: все так само як в Main_page_database_add_delete_data, тобто маєш достати рядки даних з таблиці з БД, яку користувач вибрав до цього, і вивести ці дані у таблицю
        //Тут я просто копіпастю, бо мені на той час було так впадлу все переписувати на щось інше
        //По факту можеш копіпастнути код з того файлу теж(у механізмі відображення я нічого не змінював)
        //ПОЧАТОК тудушки
        val columnCount: Int = 4
        val columnNames = arrayListOf<String>()

        //хардкод для тесту відображення у інтерфейсі (треба видалити)
        columnNames.add("1")
        columnNames.add("2")
        columnNames.add("3")
        columnNames.add("4")

        //cписок, що буде зберігати інформацію про структуру даних у таблиці
        val columnDataToDisplay = arrayListOf<Data_list_row>(Data_list_row(-1,columnCount, columnNames, true, false))

        val dataRowCount = 15
        for(i in 0..<dataRowCount step 1){
            columnData.add(Data_list_row(i+1, columnCount, arrayListOf("test_data1","test_data2","test_data3","test_data4"), false, false))
        }

        //КІНЕЦЬ тудушки

        columnDataToDisplay.addAll(columnData)

        adapter = DataListDeleteAdapter(v.context, columnDataToDisplay, this)
        dataList.adapter = adapter

        permissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            readPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
            writePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: writePermissionGranted
        }
        updateOrRequestPermissions(v.context)

        val genQRBtn = v.findViewById<Button>(R.id.main_gen_qr_page_choosing_data_gen_qr_button)
        genQRBtn.setOnClickListener {
            if (selectedRowId == -1){
                Toast.makeText(context, "Choose one data row to generate QR code", Toast.LENGTH_SHORT).show()
            }
            else{
                updateOrRequestPermissions(v.context)
                if(writePermissionGranted){
                    Log.d("myLog", "Permission granted")
                    genQr(v.context)
                }
                else{
                    Toast.makeText(v.context,"App needs access to storage to save QR code file", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val backBtn = v.findViewById<Button>(R.id.main_gen_qr_page_choosing_data_back_button)
        backBtn.setOnClickListener {
            myInterface!!.passDataToMainToReplaceFrags(Main_page_qr(), -1)
        }

        return v
    }


    override fun buttonChecked(index: Int) {
        //тут я реалізовую обмеження лише вибору у розмірі одного рядка
        for(i in 0..<index-1 step 1){
            columnData[i].isChecked = false
        }
        for(i in index..<columnData.size step 1){
            columnData[i].isChecked = false
        }
        columnData[index-1].isChecked = true
        selectedRowId = columnData[index-1].rowId
        Log.d("myLog","${selectedRowId}")
        //костиль, що перезавантажує список, щоб візуально показати, що можна вибрати лише один елемент
        adapter!!.notifyDataSetChanged()
    }

    override fun buttonUnchecked(index: Int) {
        columnData[index-1].isChecked = false
        selectedRowId = -1
        Log.d("myLog","${selectedRowId}")
    }

    private fun genQr(context: Context) {
        //Todo: зроби зміну типу string, що буде шифрувати посилання (userId, tableId, selectedRowId)
        Log.d("myLog", "gen QR")
        //коли інтегруєш бекенд, розкоментуй функції знизу
        //createQRCode(/*назва змінної, що зберігає зашифроване посилання*/, context)
        //Toast.makeText(v.context,"QR was successfully saved to gallery ", Toast.LENGTH_SHORT).show()
    }

    private fun createQRCode(str: String, context: Context){
        val multi = MultiFormatWriter()
        try{
            val bitMatrix = multi.encode(str, BarcodeFormat.QR_CODE, 300, 300)
            val bEnc = BarcodeEncoder()
            val bitmap = bEnc.createBitmap(bitMatrix)
            saveQRCode("test", bitmap, context)

        }catch (e: WriterException){
            throw RuntimeException(e)
        }
    }
    private fun saveQRCode(filename: String, bmp: Bitmap, context: Context): Boolean{
            val imageCollection = sdk29AndUp {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "$filename.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                put(MediaStore.Images.Media.WIDTH, bmp.width)
                put(MediaStore.Images.Media.HEIGHT, bmp.height)
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            return try {
                context.contentResolver.insert(imageCollection, contentValues)?.also { uri ->
                    context.contentResolver.openOutputStream(uri).use { outputStream ->
                        if(!outputStream?.let { bmp.compress(Bitmap.CompressFormat.JPEG, 95, it) }!!) {
                            throw IOException("Couldn't save bitmap")
                        }
                    }
                } ?: throw IOException("Couldn't create MediaStore entry")
                true
            } catch(e: IOException) {
                e.printStackTrace()
                false
            }
    }

    private fun updateOrRequestPermissions(context: Context) {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val hasWritePermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionsToRequest = mutableListOf<String>()
        if(!writePermissionGranted) {
            permissionsToRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(!readPermissionGranted) {
            permissionsToRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(permissionsToRequest.isNotEmpty()) {
            permissionsLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }
}