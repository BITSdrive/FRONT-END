package com.example.myapplication3;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;



public class MainActivity extends AppCompatActivity {

    ProgressBar circularProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        circularProgressBar = findViewById(R.id.circularProgressBar);

        int progress = 75;
        circularProgressBar.setProgress(progress);
    }

}