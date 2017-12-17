package com.qrcodevorking

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_read_qr.*
import kotlinx.android.synthetic.main.activity_show_result.*

/**
 * Created by Oleg on 17.12.2017.
 */
class ReadQR:AppCompatActivity() {
    private val TAG = ReadQR::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_qr)
        start_read_qr_btn.setOnClickListener {
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setCameraId(0)
            integrator.setPrompt("Scanning")
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(false)
            integrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if(result != null){
            if(result.contents == null){
               Log.d(TAG,"Content null")
                Toast.makeText(this,"Content null",Toast.LENGTH_LONG).show()
                //text_result.setText("Content null. Error!!!")
            } else{
                Log.d(TAG,result.contents)
                Toast.makeText(this,result.contents,Toast.LENGTH_LONG).show()
                //text_result.setText(result.contents)
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}