package com.example.smarthomegesturecontrol;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;

public class Practice extends AppCompatActivity {

    private Button upload_button;

    private Button record_button;

    private String rootPath = Environment.getExternalStorageDirectory().getPath();

    private String[] options = {"Gesture_0","Gesture_1","Gesture_2","Gesture_3","Gesture_4","Gesture_5","Gesture_6","Gesture_7","Gesture_8","Gesture_9","Increase Fan Speed","Decrease Fan Speed", "Turn On Fan","Turn Off Fan", "Turn On Lights", "Turn Off Lights","Set Thermostat to Specified Temperature"};

    private File finalFile;

    private int selectedGesture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = getIntent();
        selectedGesture = intent.getIntExtra("selectedGesture", 0);

        record_button = findViewById(R.id.record_button);
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "Record button is clicked", Toast.LENGTH_LONG).show();
                captureVideoFromCamera();
            }
        });

        upload_button = findViewById(R.id.upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(), "Upload button is clicked", Toast.LENGTH_LONG).show();

                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file",finalFile.getName(),RequestBody.create(finalFile,MediaType.parse("video/*"))).build();
                Request request = new Request.Builder().url("http://192.168.0.99:5000/").post(requestBody).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onResponse(@NotNull okhttp3.Call call, @NotNull Response response) throws IOException {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(Practice.this,response.body().string(),Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Practice.this,"Network error",Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }
                        });
                    }
                });

                Intent viewSamplesIntent = new Intent(Practice.this, MainActivity.class);
                startActivity(viewSamplesIntent);
            }
        });

    }

    private void captureVideoFromCamera(){
        int c = 0;
        File file = new File(rootPath + "/"+ options[selectedGesture]+"_PRACTICE_"+c+"_Chandrasekaran.mp4");
        while(file.exists()){
            file = new File(rootPath + "/"+ options[selectedGesture]+"_PRACTICE_"+(++c)+"_Chandrasekaran.mp4");
        }
        finalFile = file;
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING",1);
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 5);
        intent.putExtra("EXTRA_VIDEO_QUALITY", 0);
        Uri fileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        setMicMuted(true);
        startActivityForResult(intent, 1);
    }

    private void setMicMuted(Boolean state){
        AudioManager audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        int audioMode = audioManager.getMode();
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        if (audioManager.isMicrophoneMute() != state) {
            audioManager.setMicrophoneMute(state);
        }
        audioManager.setMode(audioMode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            VideoView videoView = new VideoView(this);
            videoView.setVideoURI(data.getData());
            videoView.start();
            builder.setView(videoView).show();
        }
    }
}