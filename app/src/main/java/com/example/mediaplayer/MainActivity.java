package com.example.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView videoDisplayView;
    private Button btnVideoView;
    private MediaController mediaController;
    private MediaPlayer mediaPlayer;

    private Button btnPlayMusic, btnPauseMusic;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoDisplayView = findViewById(R.id.videoView);
        btnVideoView = findViewById(R.id.btnVideo);
        btnVideoView.setOnClickListener(this);

        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);

        mediaController = new MediaController(MainActivity.this);

        btnPlayMusic.setOnClickListener(this);
        btnPauseMusic.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.freemusic);

    }


    @Override
    public void onClick(View ButtonView) {

        switch(ButtonView.getId()){
            case R.id.btnVideo:
                Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.video);
                videoDisplayView.setVideoURI(uri);
                videoDisplayView.setMediaController(mediaController);
                mediaController.setAnchorView(videoDisplayView);
                videoDisplayView.start();
                break;

            case R.id.btnPlayMusic:
                mediaPlayer.start();
                break;

            case R.id.btnPauseMusic:
                mediaPlayer.pause();
                break;
        }
    }

}
