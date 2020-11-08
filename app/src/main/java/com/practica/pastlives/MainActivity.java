package com.practica.pastlives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Serializable {
    private int counter;
    private int length;
    private boolean sound;
    private Button bStart;
    private Button bSound;
    private TextView tvTitle;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTitle = findViewById(R.id.tvTitle);
        bStart = findViewById(R.id.bStart);
        bStart.setOnClickListener(this);
        bSound = findViewById(R.id.bSound);
        bSound.setOnClickListener(this);
        setStart();

        mediaPlayer = MediaPlayer.create(this, R.raw.toocrazy);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        sound = true;

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSound:
                if (sound) {
                    sound = false;
                    bSound.setBackgroundResource(R.drawable.nosound);
                    mediaPlayer.pause();
                } else {
                    sound = true;
                    bSound.setBackgroundResource(R.drawable.sound);
                    mediaPlayer.start();
                }
                break;
            case R.id.bStart:
                switch (counter) {
                    case 0:
                        tvTitle.setVisibility(View.VISIBLE);
                        tvTitle.setText(R.string.title);
                        counter++;
                        break;
                    case 1:
                        tvTitle.setText(R.string.title2);
                        counter++;
                        break;
                    case 2:
                        length = mediaPlayer.getCurrentPosition();
                        mediaPlayer.pause();
                        Intent i = new Intent(this, InfoMenuAct.class);
                        i.putExtra("length", length);
                        i.putExtra("sound", sound);
                        startActivity(i);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
            }

    }

    private void setStart() {
        tvTitle.setVisibility(View.GONE);
        counter = 0;
    }

    @Override
    protected void onPause() {
        mediaPlayer.pause();
        length = mediaPlayer.getCurrentPosition();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mediaPlayer.seekTo(length);
        mediaPlayer.start();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }


}