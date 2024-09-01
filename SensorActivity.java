package com.example.womenapp;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

public class SensorActivity extends Activity implements SensorEventListener {

    private TextView xTextView, yTextView, zTextView;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private boolean isAccelerometerSensorAvailable, isFirstTime = true;
    private float lastX, lastY, lastZ;
    private float shakeThreshold = 5f;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        xTextView = findViewById(R.id.xTextView);
        yTextView = findViewById(R.id.yTextView);
        zTextView = findViewById(R.id.zTextView);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else {
            xTextView.setText("Accelerometer sensor is not available");
            isAccelerometerSensorAvailable = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccelerometerSensorAvailable) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelerometerSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float currentX = event.values[0];
            float currentY = event.values[1];
            float currentZ = event.values[2];

            xTextView.setText(String.format("%.2f m/s²", currentX));
            yTextView.setText(String.format("%.2f m/s²", currentY));
            zTextView.setText(String.format("%.2f m/s²", currentZ));

            if (!isFirstTime) {
                float xDifference = Math.abs(lastX - currentX);
                float yDifference = Math.abs(lastY - currentY);
                float zDifference = Math.abs(lastZ - currentZ);

                if ((xDifference > shakeThreshold && yDifference > shakeThreshold) ||
                        (xDifference > shakeThreshold && zDifference > shakeThreshold) ||
                        (yDifference > shakeThreshold && zDifference > shakeThreshold)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    } else {
                        vibrator.vibrate(500);
                    }
                }
            }

            lastX = currentX;
            lastY = currentY;
            lastZ = currentZ;
            isFirstTime = false;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }
}
