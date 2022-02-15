package com.tangvdv.databaseconnection;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;

public class PackageLibrary extends AppCompatActivity {
    DatabaseHelper myDb;
    LinearLayout layoutPackage;
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
                                ((ViewGroup) inputDialog.getParent()).removeView(inputDialog);
                                inputDialog.getText().clear();
                                dialogInterface.dismiss();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                ((ViewGroup) inputDialog.getParent()).removeView(inputDialog);
                                inputDialog.getText().clear();
                                viewAll();

                            }
                        });

                        builder.show();
                }
        });

        viewAll();
    }


    public void viewAll() {
        layoutPackage = (LinearLayout) findViewById(R.id.layoutPackage);
        layoutPackage.removeAllViews();
        showMessage("PLZZZZZZZZ", String.valueOf(layoutPackage.getChildCount()));

        while(resultQuery.moveToNext()){
            myButton = new Button(this);
            myButton.setText(resultQuery.getString(1));
            myButton.setId(Integer.parseInt(resultQuery.getString(0)));
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PackageLibrary.this, null);
                    intent.putExtra("id",v.getId());
                    startActivity(intent);
                }
            });
            layoutPackage.addView(myButton);
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