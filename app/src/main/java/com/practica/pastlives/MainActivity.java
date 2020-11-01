package com.practica.pastlives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button bStart;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bStart = findViewById(R.id.bStart);
        bStart.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, InfoMenuAct.class);
        startActivity(i);
    }
}