package com.practica.pastlives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

public class ResultAct extends AppCompatActivity  implements Serializable {
    private TextView tvResult;
    private ImageView ivResult;
    private MediaPlayer mediaPlayer;
    private int length;
    private boolean sound;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvResult = findViewById(R.id.tvResult);
        ivResult = findViewById(R.id.ivResult);

        Intent i = getIntent();
        PastLife pl = (PastLife) i.getSerializableExtra("result");
        sound = (boolean) i.getSerializableExtra("sound");
        if (sound) {
            length = (int) i.getSerializableExtra("length");
        }
        System.out.println(pl);
        ResultCalculator rc = new ResultCalculator(pl, this);

        ivResult.setBackground(rc.getRaceImage());
        tvResult.setText(rc.generateText());
        if (sound) {
            mediaPlayer = MediaPlayer.create(this, R.raw.toocrazy);
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    protected void onPause() {
        if (sound) {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (sound) {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
        }
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        if (sound) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}