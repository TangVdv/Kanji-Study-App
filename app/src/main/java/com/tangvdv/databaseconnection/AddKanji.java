package com.tangvdv.databaseconnection;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddKanji extends AppCompatActivity {
    DatabaseHelper myDb;
    Button btnAddData;
    EditText editKanji, editTraduction;
    String[] data = new String[2];
    String[] column = { "name", "translation" };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kanji);

        editKanji = (EditText) findViewById(R.id.input_kanji);
        editTraduction = (EditText) findViewById(R.id.input_traduction);
        btnAddData = (Button) findViewById(R.id.button_add);

        AddData();
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        data[0] = editKanji.getText().toString();
                        data[1] = editTraduction.getText().toString();
                        myDb.insertData(column, data, "Kanji");
                        editKanji.getText().clear();
                        editTraduction.getText().clear();
                    }
                }
        );
    }
}