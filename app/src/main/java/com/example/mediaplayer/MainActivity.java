package com.example.mediaplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView videoDisplayView;
    private Button buttonView;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoDisplayView = findViewById(R.id.videoView);
        buttonView = findViewById(R.id.button);
        buttonView.setOnClickListener(this);

        mediaController = new MediaController(MainActivity.this);
    }

    @Override
    public void onClick(View v) {
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
        videoDisplayView.setVideoURI(uri);
        videoDisplayView.setMediaController(mediaController);
        mediaController.setAnchorView(videoDisplayView);
        videoDisplayView.start();

    }

}
