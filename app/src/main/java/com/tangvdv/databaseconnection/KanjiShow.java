package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class KanjiShow extends AppCompatActivity {
    DatabaseHelper myDb;
    TextView Kanji, Traduction;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_show);

        Kanji = (TextView) findViewById(R.id.kanji);
        Traduction = (TextView) findViewById(R.id.traduction);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);

        if (id >= 0) {
            Cursor resData = myDb.getSpecificData(id, "Kanji");

            if (resData == null || resData.getCount() == 0) {
                showMessage("Error", "Nothing found in the database");
                return;
            }
            resData.moveToFirst();
            Kanji.setText(resData.getString(0));
            Traduction.setText(resData.getString(1));
        }
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}