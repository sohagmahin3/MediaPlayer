package com.example.mediaplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener , MediaPlayer.OnCompletionListener {

    //UI components
    private VideoView videoDisplayView;
    private Button btnVideoView;
    private Button btnPlayMusic, btnPauseMusic;
    private SeekBar seekBarVolume;
    private SeekBar seekBarmoveForward;

    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private AudioManager audioManager;

    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoDisplayView = findViewById(R.id.videoView);
        btnVideoView = findViewById(R.id.btnVideo);
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPauseMusic = findViewById(R.id.btnPauseMusic);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        seekBarmoveForward = findViewById(R.id.mfSeekBar);


        mediaController = new MediaController(MainActivity.this);
        audioManager =(AudioManager) getSystemService(AUDIO_SERVICE);

        btnVideoView.setOnClickListener(this);
        btnPlayMusic.setOnClickListener(this);
        btnPauseMusic.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.freemusic);

        int maxVolumeOfUserService = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        int currentVolumeOfUserDevice = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        seekBarVolume.setMax(maxVolumeOfUserService);
        seekBarVolume.setProgress(currentVolumeOfUserDevice);

        seekBarmoveForward.setOnSeekBarChangeListener(this);
        seekBarmoveForward.setMax(mediaPlayer.getDuration());
        mediaPlayer.setOnCompletionListener(this);


        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Toast.makeText(MainActivity.this,Integer.toString(progress),Toast.LENGTH_SHORT).show();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
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
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBarmoveForward.setProgress(mediaPlayer.getCurrentPosition());
                    }
                },0,1000);
                break;

            case R.id.btnPauseMusic:
                mediaPlayer.pause();
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser) {
            //Toast.makeText(this, Integer.toString(progress), Toast.LENGTH_SHORT).show();
            mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mediaPlayer.pause();


    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        timer.cancel();
        Toast.makeText(this,"Music is finished !",Toast.LENGTH_SHORT).show();
    }
}
