package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Review extends AppCompatActivity {
    String kanjiLimit;
    Integer options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        kanjiLimit = intent.getStringExtra("limit");
        options = intent.getIntExtra("option", 0);
    }
}