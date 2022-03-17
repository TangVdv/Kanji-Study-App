package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

public class ReviewOption extends AppCompatActivity {
    Button btnStart;
    Spinner spinnerKanjiLimit;
    String kanjiLimit;
    CheckBox option1, option2, option3, option4;
    Integer options = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_option);

        spinnerKanjiLimit = findViewById(R.id.dropdownKanjiLimit);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.kanjiLimit, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerKanjiLimit.setAdapter(adapter);

        btnStart = (Button) findViewById(R.id.btnStart);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);


        setKanjiLimit();
        startReview();


    }

    public void setKanjiLimit(){
        spinnerKanjiLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kanjiLimit = adapterView.getItemAtPosition(i).toString();
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

                        if(option1.isChecked())
                            options++; // you can save this as checked somewhere
                        if(option2.isChecked())
                            options++; // you can save this as checked somewhere
                        if(option3.isChecked())
                            options++; // you can save this as checked somewhere
                        if(option4.isChecked())
                            options++; // you can save this as checked somewhere

                        Intent intent = new Intent(new Intent(ReviewOption.this, Review.class));
                        intent.putExtra("limit", kanjiLimit);
                        intent.putExtra("option", options);
                        startActivity(intent);
                    }
                }
        );
    }
}