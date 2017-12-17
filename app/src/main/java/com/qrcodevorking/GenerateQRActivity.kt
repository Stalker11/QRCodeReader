package com.qrcodevorking

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_show_code.*

/**
 * Created by Oleg on 17.12.2017.
 */
class GenerateQRActivity : AppCompatActivity() {
    private val TAG = GenerateQRActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_code)

        generate_new_qr.setOnClickListener {
            val writer = MultiFormatWriter()
            try {
                val encoder = writer.encode(input_qr_info.text.toString(), BarcodeFormat.QR_CODE, 400, 400)
                val barCodeReader = BarcodeEncoder()
                val bitmap = barCodeReader.createBitmap(encoder)
                code_picture.setImageBitmap(bitmap)
            } catch (e: WriterException) {

            } catch (e: IllegalArgumentException){

            }
        }
    }

}