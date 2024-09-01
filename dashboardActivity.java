// src/main/java/com/example/myapp/DashboardActivity.java
package com.example.womenapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class dashboardActivity extends AppCompatActivity {

    private ImageView sosImageView;
    private ImageView sensorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        sosImageView = findViewById(R.id.im2);
        sensorImageView = findViewById(R.id.im3);


        sosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle SOS button click
                handleSosClick();
            }
        });

        sensorImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Sensor button click
                handleSensorClick();
            }
        });
    }

    private void handleSosClick() {
        // Example: Show a toast message or start another activity
        Toast.makeText(this, "SOS button clicked", Toast.LENGTH_SHORT).show();

         Intent intent = new Intent(dashboardActivity.this, SosActivity.class);
         startActivity(intent);
    }

    private void handleSensorClick() {
        // Example: Show a toast message or start another activity
        Toast.makeText(this, "Sensor button clicked", Toast.LENGTH_SHORT).show();
        // You can start another activity here if needed
         Intent intent = new Intent(dashboardActivity.this, SensorActivity.class);
        startActivity(intent);
    }
}
