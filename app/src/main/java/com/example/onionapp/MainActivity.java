package com.example.onionapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //updateRecommendation();

        initializeRecommendation();

        BottomNavigationView classify = (BottomNavigationView) findViewById(R.id.bottom_navigator);
        ImageButton button1=(ImageButton) findViewById(R.id.purpleblotchbtn);
        ImageButton button2=(ImageButton) findViewById(R.id.leafblightbtn);
        // ImageButton button3=(ImageButton) findViewById(R.id.capturebtn);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent int1 = new Intent(MainActivity.this, Learnpurpleblotch.class);
                startActivity(int1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent int2 = new Intent(MainActivity.this, Learnleafblight.class);
                startActivity(int2);
            }
        });

        /*button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent int3 = new Intent(MainActivity.this, Capture.class);
                startActivity(int3);
            }
        });*/
        classify.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.classify:
                        Intent intent = new Intent(MainActivity.this, Capture.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.history:
                        Intent intent2 = new Intent(MainActivity.this,History.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.about:
                        Intent intent1 = new Intent(MainActivity.this, About.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.edit:
                        Intent intent3 = new Intent(MainActivity.this, Editrecommendation.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });



    }


    private void updateRecommendation() {
        // Needed a method or condition for updating recommendations after opening the app so that it wont update everytime when opening or launching the app
        DataManager dataManager = new DataManager(this);

        String Leafblight_recommDefault = getResources().getString(R.string.leaf_blight);
        String Leafblight_chemDefault = getResources().getString(R.string.leafblight_chem_control);
        String Leafblight_tipsDefault = getResources().getString(R.string.leafblight_chem_control);
        String Leafblight_refDefault = getResources().getString(R.string.leafblight_chem_control);
        String PurpleB_recommDefault = getResources().getString(R.string.purple_blotch);
        String PurpleB_chemDefault = getResources().getString(R.string.purpleblotch_chem_control);
        String PurpleB_tipsDefault = getResources().getString(R.string.purpleblotch_chem_control);
        String PurpleB_refDefault = getResources().getString(R.string.purpleblotch_chem_control);


        try {
            // Open the database for writing
            dataManager.open();

            // Retrieve the "purpleblotchRecommendation" record
            Cursor cursor = dataManager.getAllRecords();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                    int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                    int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);

                    if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0){
                        int recId = cursor.getInt(ID);
                        String name = cursor.getString(nameIndex);
                        String value = cursor.getString(valueIndex);
                        String chemicalRecommendation = cursor.getString(chemValueIndex);

                        // Check if the columns are present in the cursor
                        if ("purpleblotchRecommendation".equals(name)) {
                            if (value.equals(PurpleB_recommDefault) && chemicalRecommendation.equals(PurpleB_chemDefault)) {
                                // Update the "purpleblotchRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, PurpleB_recommDefault, PurpleB_chemDefault,PurpleB_tipsDefault,PurpleB_refDefault);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "purpleblotchRecommendation updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating purpleblotchRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            else {

                            }
                        }
                        if ("blightRecommendation".equals(name)) {
                            if (value.equals(Leafblight_recommDefault) && chemicalRecommendation.equals(Leafblight_chemDefault)) {
                                // Update the "purpleblotchRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, Leafblight_recommDefault, Leafblight_chemDefault,Leafblight_tipsDefault,Leafblight_refDefault);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "purpleblotchRecommendation updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating purpleblotchRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
                            }
                            else {
                            }
                        }
                    }
                } while (cursor.moveToNext());

                // Close the cursor
                cursor.close();
            }


        } finally {
            // Close the database
            dataManager.close();
        }
    }

    private void initializeRecommendation(){
        DataManager dataManager = new DataManager(this);

        String Leafblight_recommDefault = getResources().getString(R.string.leaf_blight);
        String Leafblight_chemDefault = getResources().getString(R.string.leafblight_chem_control);
        String Leafblight_tipsDefault = getResources().getString(R.string.leafblight_tips);
        String Leafblight_refDefault = getResources().getString(R.string.leafblight_ref);
        String PurpleB_recommDefault = getResources().getString(R.string.purple_blotch);
        String PurpleB_chemDefault = getResources().getString(R.string.purpleblotch_chem_control);
        String PurpleB_tipsDefault = getResources().getString(R.string.purpleblotch_tips);
        String PurpleB_refDefault = getResources().getString(R.string.purpleblotch_ref);


        try {
            // Open the database for reading
            dataManager.open();

            // Check if "blightRecommendation" already exists in the database
            if (!dataManager.isRecordExist("blightRecommendation")) {
                // If it doesn't exist, insert the record
                long blightRecId = dataManager.insertRecord("blightRecommendation",Leafblight_recommDefault ,Leafblight_chemDefault,Leafblight_tipsDefault,Leafblight_refDefault);

                if (blightRecId != -1) {
                    // Record inserted successfully
                    // Toast.makeText(this, "Blight Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    // Toast.makeText(this, "Error inserting Blight Recommendation", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The record already exists
                //Toast.makeText(this, "Blight Recommendation already exists", Toast.LENGTH_SHORT).show();
            }

            // Check if "purpleblotchRecommendation" already exists in the database
            if (!dataManager.isRecordExist("purpleblotchRecommendation")) {
                // If it doesn't exist, insert the record

                long purpleblotchRecId = dataManager.insertRecord("purpleblotchRecommendation",PurpleB_recommDefault ,PurpleB_chemDefault,PurpleB_tipsDefault,PurpleB_refDefault);

                if (purpleblotchRecId != -1) {
                    // Record inserted successfully
                    //Toast.makeText(this, "Purple Blotch Recommendation inserted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    // There was an error inserting the record
                    //Toast.makeText(this, "Error inserting Purple Blotch Recommendation", Toast.LENGTH_SHORT).show();
                }
            } else {
                // The record already exists
                //Toast.makeText(this, "Purple Blotch Recommendation already exists", Toast.LENGTH_SHORT).show();

            }
        } finally {
            // Close the database
            dataManager.close();
        }
    }
}