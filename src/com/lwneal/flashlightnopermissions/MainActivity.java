package com.lwneal.flashlightnopermissions;

import java.io.IOException;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

  boolean isOn;
  Camera cam;
  SurfaceTexture samsungCompatibilityHack = new SurfaceTexture(0);
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }
  
  private void turnFlashlightOn() throws IOException {
    Log.d("FlashlightNoPermissions", "Turning Flashlight On");
    cam = Camera.open();
    cam.setPreviewTexture(samsungCompatibilityHack);
    Parameters p = cam.getParameters();
    p.setFlashMode(Parameters.FLASH_MODE_TORCH);
    cam.setParameters(p);
    cam.startPreview();
    isOn = true;
  }
  
  private void turnFlashlightOff() {
    Log.d("FlashlightNoPermissions", "Turning Flashlight Off");
    cam.stopPreview();
    cam.release();
    isOn = false;
  }
  
  public void toggleFlashlight(View v) {
    try {
      if (!isOn)
        turnFlashlightOn();
      else
        turnFlashlightOff();
    } catch (Exception e) {
      e.printStackTrace();
      Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT);
    }
  }
}
