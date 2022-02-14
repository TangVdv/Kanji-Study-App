package com.tangvdv.databaseconnection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnOpenKanji;
    Button btnOpenLibrary;
    Button btnOpenPackage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOpenKanji = (Button) findViewById(R.id.open_add_kanji);
        btnOpenLibrary = (Button) findViewById(R.id.open_library);
        btnOpenPackage = (Button) findViewById(R.id.open_package);

        openAddKanjiActivity();
        openLibrary();
        openPackage();
    }

    public void openAddKanjiActivity(){
        btnOpenKanji.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddKanji.class));
                    }
                }
        );
    }

    public void openLibrary(){
        btnOpenLibrary.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, KanjiLibrary.class));
                    }
                }
        );
    }

    public void openPackage(){
        btnOpenPackage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, PackageLibrary.class));
                    }
                }
        );
    }
}