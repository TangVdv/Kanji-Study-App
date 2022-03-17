package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class ReviewOption extends AppCompatActivity {
    Button btnStart;
    Spinner spinnerKanjiLimit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_option);

        spinnerKanjiLimit = findViewById(R.id.dropdownKanjiLimit);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.kanjiLimit, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerKanjiLimit.setAdapter(adapter);

        btnStart = (Button) findViewById(R.id.btnStart);


    }

    public void setKanjiLimit(){
        spinnerKanjiLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // TODO: get value from dropdown
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void startReview(){
        btnStart.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(ReviewOption.this, AddKanji.class));
                    }
                }
        );
    }
}