package com.qrcodevorking;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Oleg on 14.10.2017.
 */

public class ShowResult extends AppCompatActivity {
  private TextView mText;
  private Barcode mBarcode;
  public static final String TAG = ShowResult.class.getSimpleName();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_show_result);
    mText = (TextView) findViewById(R.id.text_result);
    mBarcode = getIntent().getParcelableExtra("12");

    mText.setText(mBarcode.displayValue);
    Log.d(TAG, "onCreate: " + mBarcode);
    super.onCreate(savedInstanceState);
  }
}
