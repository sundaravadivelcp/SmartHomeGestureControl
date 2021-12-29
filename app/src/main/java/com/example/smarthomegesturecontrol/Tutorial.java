package com.example.smarthomegesturecontrol;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class Tutorial extends AppCompatActivity {

    private int[] option = {R.raw.h_0,R.raw.h_1,R.raw.h_2,R.raw.h_3,R.raw.h_4,R.raw.h_5,R.raw.h_6,R.raw.h_7,R.raw.h_8,R.raw.h_9,R.raw.h_increase_fan_speed,R.raw.h_decrease_fan_speed, R.raw.h_fan_on, R.raw.h_fan_off, R.raw.h_light_on, R.raw.h_light_off, R.raw.h_set_thermo};

    private VideoView videoView;

    private String rootPath = Environment.getExternalStorageDirectory().getPath();

    private Button practice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        Intent intent = getIntent();
        int i = intent.getIntExtra("selectedGesture", 0);
        videoView = (VideoView) findViewById(R.id.videoView);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        String uriPath = "android.resource://"+getPackageName()+"/"+option[i];
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);

        videoView.start();

        practice = (Button) findViewById(R.id.practice_button);
        practice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewSamplesIntent = new Intent(Tutorial.this, Practice.class);
                viewSamplesIntent.putExtra("selectedGesture", i);
                startActivity(viewSamplesIntent);
            }
        });
    }
}