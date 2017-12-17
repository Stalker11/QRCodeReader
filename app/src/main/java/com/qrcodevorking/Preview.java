package com.qrcodevorking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by Oleg on 14.10.2017.
 */

public class Preview extends SurfaceView implements SurfaceHolder.Callback {
  public static final String TAG = Preview.class.getSimpleName();
  private BarcodeDetector mBarcodeDetector;
  private MainActivity activity;
  private CameraSource mCameraSource;
  private SurfaceHolder mHolder;
  private TextView text;
  private Camera mCamera;

  public Preview(Context context, final MainActivity activity, final TextView textview) {
    super(context);
    this.activity = activity;
    this.text = textview;
    mHolder = getHolder();
    mHolder.addCallback(this);
    mBarcodeDetector = new BarcodeDetector.Builder(activity)
            .setBarcodeFormats(Barcode.ALL_FORMATS)
            .build();
    mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {

      @Override
      public void release() {
        Log.d(TAG, "release: ");
      }

      @Override
      public void receiveDetections(Detector.Detections<Barcode> detections) {
        SparseArray<Barcode> codes = detections.getDetectedItems();
        Log.d(TAG, "receiveDetections: "+codes.size()+".");
        activity.showToast(String.valueOf(codes.size()));
        if (codes.size() > 0) {
          Intent intent = new Intent(activity,ShowResult.class);
          intent.putExtra("12",codes.valueAt(0));
          activity.startActivity(intent);
          activity.finish();
         // activity.showToast(codes.valueAt(0).displayValue);
         /* activity.startActivity(intent);
          activity.finish();*/
         // textview.setText(codes.valueAt(0).displayValue);
          for (int a = 0; a < codes.size(); a++) {
//            String xa = codes.valueAt(a).calendarEvent.description;
            Barcode code = codes.get(a);
            //textview.setText(codes.valueAt(0).displayValue);
            Log.d(TAG, "receiveDetections: " + codes.valueAt(0).displayValue);
          }
        }
      }
    });

    mCameraSource = new CameraSource.Builder(activity, mBarcodeDetector)
            .setAutoFocusEnabled(true)
            .setRequestedPreviewSize(640, 840)
            .build();

  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Log.d(TAG, "surfaceCreated:1 ");
    try {
      if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, 1000);
      }
      mCameraSource.start(mHolder);
       mCamera = getCamera(mCameraSource);
      if(mCamera != null){
       /* Camera.Parameters param = camera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(param);*/
      }
      Log.d(TAG, "surfaceCreated:2 "+getCamera(mCameraSource));

    } catch (IOException e) {
      e.printStackTrace();
      Log.d(TAG, "surfaceCreated:3 " + e.getMessage());
    }
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    mCameraSource.stop();
  }

  public void scan() {
    if(mCamera != null){
      activity.showToast("Flash on");
        Camera.Parameters param = mCamera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(param);
    }
  }
public void stopScan(){
  if(mCamera != null){
    Camera.Parameters param = mCamera.getParameters();
    param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
    mCamera.setParameters(param);
  }
}
  private Camera getCamera(@NonNull CameraSource cameraSource) {
    Field[] declaredFields = CameraSource.class.getDeclaredFields();

    for (Field field : declaredFields) {
      Log.d(TAG, "getCamera: "+field.getType()+" "+Camera.class);
      if (field.getType() == Camera.class) {
        field.setAccessible(true);
        Log.d(TAG, "getCamera:1 "+field.getType());
        try {
          Camera camera = (Camera) field.get(cameraSource);
          if (camera != null) {
            return camera;
          }
          return null;
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
        break;
      }
    }
    return null;
  }
}


