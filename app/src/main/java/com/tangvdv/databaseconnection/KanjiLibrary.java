package com.tangvdv.databaseconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.GridLayout;
import android.widget.TextView;

public class KanjiLibrary extends AppCompatActivity {
    TextView textView;
    KanjiShow kanjiShow;
    DatabaseHelper myDb;
    Button myButton;
    String[] queryArray = {"Kanji", "Traduction"};
    boolean[] selectedQuery;
    Integer queryId = 0;
    Integer textSize;
    Cursor resultQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);

        kanjiShow = new KanjiShow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_library);

        textView = (TextView) findViewById(R.id.textView);

        selectedQuery = new boolean[queryArray.length];

        //update textview when clicked
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(KanjiLibrary.this);

                builder.setTitle("Select what to show");

                builder.setCancelable(false);

                builder.setMultiChoiceItems(queryArray, selectedQuery, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean checkedBox) {
                        if (checkedBox) {
                            queryId = i;
                            textView.setText(queryArray[queryId]);
                            dialogInterface.dismiss();
                            viewAll();

                        }
                        for (int j = 0; j < selectedQuery.length; j++) {
                            if(j != queryId){
                                selectedQuery[j] = false;
                            }
                        }

                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();


            }
        });

        resultQuery = myDb.getAllData("Kanji");
        if(resultQuery.getCount() == 0) {
            showMessage("Error","Nothing found in the database");
            finish();
            return;
        }
    }

    public void viewAll() {
        if (queryId == 0) textSize = 60;
        else textSize = 20;
        GridLayout gridView = (GridLayout) findViewById(R.id.buttonGrid);
        gridView.removeAllViews();

        while(resultQuery.moveToNext()){
            myButton = new Button(this);
            myButton.setText(resultQuery.getString(queryId+1));
            myButton.setId(Integer.parseInt(resultQuery.getString(0)));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(KanjiLibrary.this, KanjiShow.class);
                    intent.putExtra("id",v.getId());
                    startActivity(intent);
                }
            });
            myButton.setWidth(300);
            myButton.setHeight(300);
            myButton.setTextSize(textSize);
            gridView.addView(myButton);
        }
        resultQuery.moveToPosition(-1);
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}