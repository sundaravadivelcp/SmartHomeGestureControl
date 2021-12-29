package com.example.smarthomegesturecontrol;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private Button viewSamples;
    int selectedGesture = 0;
    private String[] options = {"Gesture_0","Gesture_1","Gesture_2","Gesture_3","Gesture_4","Gesture_5","Gesture_6","Gesture_7","Gesture_8","Gesture_9","Increase Fan Speed","Decrease Fan Speed", "Turn On Fan","Turn Off Fan", "Turn On Lights", "Turn Off Lights","Set Thermostat to Specified Temperature"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlePermissions(MainActivity.this);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        viewSamples = (Button) findViewById(R.id.view_sample_button);
        viewSamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewSamplesIntent = new Intent(MainActivity.this, Tutorial.class);

                viewSamplesIntent.putExtra("selectedGesture", selectedGesture);
                startActivity(viewSamplesIntent);
            }
        });
    }

    public static void handlePermissions(Activity activity) {

        int storagePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int REQUEST_EXTERNAL_STORAGE = 1;

        String[] PERMISSIONS = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };

        if (storagePermission != PackageManager.PERMISSION_GRANTED) {
            Log.i("log", "Read/Write Permissions needed!");
        }

        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS,
                REQUEST_EXTERNAL_STORAGE
        );

        Log.i("log", "Permissions Granted!");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner){
            selectedGesture = i;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}