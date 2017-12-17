package com.qrcodevorking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.BarcodeDetector;

public class MainActivity extends AppCompatActivity {
  private FrameLayout mCameraPreview;
  private BarcodeDetector mBarcodeDetector;
  private Button mScan;
  private Button mStopScan;
  private TextView mSearchInfo;
  private Preview mPreview;
  private Button mGenerator;
  private Button mStarter;
  private static final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mGenerator = (Button)findViewById(R.id.generate_qr);
    mStarter = (Button)findViewById(R.id.start_new_reader);
    mStarter.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,ReadQR.class));
      }
    });
    mGenerator.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(MainActivity.this,GenerateQRActivity.class));
      }
    });
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
      if (ContextCompat.checkSelfPermission(MainActivity.this,
              Manifest.permission.CAMERA)
              != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(new String[]{Manifest.permission.CAMERA}
                , 1000);
      }
      if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1000);
      } else {
        createPreviw();
      }

    }
    else {
      createPreviw();
    }
  }
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
    @NonNull int[] grantResults){
      if (requestCode == 1000 && grantResults[0] == 0) {
        createPreviw();
      }

      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

  private void createPreviw() {
    mCameraPreview = (FrameLayout) findViewById(R.id.surface_preview);
    mScan = (Button) findViewById(R.id.btn_scan);
    mSearchInfo = (TextView) findViewById(R.id.scan_info);
    mStopScan = (Button) findViewById(R.id.btn_stop_scan);
    mPreview = new Preview(this, this, mSearchInfo);
    mCameraPreview.addView(mPreview);
    mScan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPreview.scan();
      }
    });
    mStopScan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPreview.stopScan();
      }
    });
  }
  public void showToast(String toast){
    Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
  }
}

