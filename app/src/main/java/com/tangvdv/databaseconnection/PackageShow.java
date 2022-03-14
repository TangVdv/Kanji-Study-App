package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PackageShow extends AppCompatActivity {
    DatabaseHelper myDb;
    Integer selectedKanji;
    TextView Title;
    Cursor result;
    Integer idPackage;
    Integer[] kanjiListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_show);
        myDb = new DatabaseHelper(this);

        Intent intent = getIntent();
        idPackage = intent.getIntExtra("id", -1);
        if (idPackage < 0) return;
        result = myDb.getSpecificPackage(idPackage);
        result.moveToFirst();
        Title = (TextView) findViewById(R.id.packageName);
        Title.setText(result.getString(0));


        kanjiList();

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedKanji != null)
                    addKanjiToList();
            }
        });
    }

    public void addKanjiToList(){
        result = myDb.getSpecificData(selectedKanji+1,"Kanji");
        result.moveToFirst();
        if (result.getCount()>0) {
            String[] column = {"Package_id", "Kanji_id"};
            String[] value = {String.valueOf(idPackage), result.getString(0)};
            myDb.insertData(column, value, "Package_has_Kanji");
            kanjiList();
        }
    }

    public void setListAdapter(){
        Spinner dropdown = findViewById(R.id.spinner1);

        result = myDb.getAllData("Kanji");
        String[] items = new String[result.getCount()];
        result.moveToPosition(-1);
        int i = 0;
        while(result.moveToNext()) {
            items[i] = result.getString(1);
            ++i;
        }

        List<String> list = new ArrayList();
        for(String s : items) {
            if(s != null && s.length() > 0) {
                list.add(s);
            }
        }

        items = list.toArray(new String[list.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //selectedKanji = adapterView.getItemAtPosition(i).toString();
                selectedKanji = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void kanjiList(){
        LinearLayout layoutKanjiList = findViewById(R.id.kanjiList);
        layoutKanjiList.removeAllViews();

        result = myDb.getKanjiFromPackage(idPackage);
        kanjiListId = new Integer[result.getCount()];
        int i = 0;
        while(result.moveToNext()) {
            LinearLayout container = new LinearLayout(this);
            container.setId(result.getInt(0));
            container.setOrientation(LinearLayout.HORIZONTAL);

            TextView kanjiText = new TextView(this);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            kanjiText.setLayoutParams(textParams);
            kanjiText.setText(result.getString(1));
            kanjiText.setId(result.getInt(0));
            kanjiText.setTextSize(30);
            kanjiListId[i] = Integer.valueOf(result.getString(0));
            ++i;

            Button deleteButton = new Button(this);
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 0f);
            deleteButton.setLayoutParams(buttonParams);
            deleteButton.setText("Delete");
            deleteButton.setTextSize(15);
            deleteButton.setId(result.getInt(0));
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout btnLayout = (LinearLayout) findViewById(v.getId());
                    myDb.deleteKanjiFromPackage(idPackage, v.getId());
                    layoutKanjiList.removeView(btnLayout);
                }
            });

            container.addView(kanjiText);
            container.addView(deleteButton);
            layoutKanjiList.addView(container);
        }

        setListAdapter();
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}