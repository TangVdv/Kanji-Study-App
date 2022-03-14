package com.tangvdv.databaseconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.util.Arrays;

public class PackageLibrary extends AppCompatActivity {
    DatabaseHelper myDb;
    Cursor resultQuery;
    Button myButton;
    Button createButton;
    EditText inputDialog;
    String[] column = {"name", "numberKanji"};;
    String[] data = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDb = new DatabaseHelper(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_library);

        inputDialog = new EditText(this);
        createButton = (Button) findViewById(R.id.createPackage);

        resultQuery = myDb.getAllData("Package");

        createButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PackageLibrary.this);

                        builder.setTitle("Write a name");

                        builder.setView(inputDialog);

                        builder.setPositiveButton("Create", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                data[0] = inputDialog.getText().toString();
                                data[1] = null;
                                myDb.insertData(column, data, "Package");
                                resultQuery = myDb.getSpecificDataByName(data[0], "Package");
                                Intent intent = new Intent(PackageLibrary.this, PackageShow.class);
                                intent.putExtra("id", resultQuery.getInt(0));
                                startActivity(intent);
                                finish();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                ((ViewGroup) inputDialog.getParent()).removeView(inputDialog);
                                inputDialog.getText().clear();

                            }
                        });

                        builder.show();
                }
        });

        viewAll();
    }

    public void viewAll() {
        LinearLayout layoutPackageList = (LinearLayout) findViewById(R.id.layoutPackage);
        layoutPackageList.removeAllViews();

        while(resultQuery.moveToNext()){
            LinearLayout container = new LinearLayout(this);
            container.setId(resultQuery.getInt(0));
            container.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams containerParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150, 1f);
            container.setLayoutParams(containerParams);

            myButton = new Button(this);
            LinearLayout.LayoutParams myButtonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 1f);
            myButton.setLayoutParams(myButtonParams);
            myButton.setText(resultQuery.getString(1));
            myButton.setId(Integer.parseInt(resultQuery.getString(0)));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PackageLibrary.this, PackageShow.class);
                    intent.putExtra("id", v.getId() );
                    startActivity(intent);
                }
            });

            ImageButton deleteButton = new ImageButton(this);
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 0f);
            deleteButton.setLayoutParams(deleteButtonParams);
            deleteButton.setImageResource(R.drawable.trashcan);
            deleteButton.setAdjustViewBounds(true);


            deleteButton.setId(resultQuery.getInt(0));
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout btnLayout = (LinearLayout) findViewById(v.getId());
                    myDb.deleteSpecificPackage(v.getId());
                    layoutPackageList.removeView(btnLayout);
                }
            });

            container.addView(myButton);
            container.addView(deleteButton);
            layoutPackageList.addView(container);
        }
        resultQuery.moveToPosition(-1);
    }

}