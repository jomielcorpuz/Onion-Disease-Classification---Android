package com.example.onionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateRecommendation extends AppCompatActivity {

    Button updateBtn, cancelBtn;
    EditText recommedtxt,chemedtxt,tipstxt,refTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recommendation);

        init();
        Bundle extras = getIntent().getExtras();
        String Name = extras.getString("id");


        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Name.equals("Leaf Blight")){
                    updateLeafBLight();
                    Toast.makeText(UpdateRecommendation.this,"Succesfully Updated Recommendations for Leaf Blight", Toast.LENGTH_SHORT).show();
                } else if(Name.equals("Purple Blotch")){
                    updatePurpleBlotch();
                    Toast.makeText(UpdateRecommendation.this,"Succesfully Updated Recommendations for Purple Blotch", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateRecommendation.this,"Error Occured", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(UpdateRecommendation.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateRecommendation.this, Editrecommendation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });




    }
    private void init(){
        updateBtn = findViewById(R.id.saveBtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        recommedtxt = findViewById(R.id.recommEditxt);
        chemedtxt = findViewById(R.id.chemEditxt);
        tipstxt = findViewById(R.id.tipsEdit);
        refTxt = findViewById(R.id.refsEdit);
    }


    private void updateLeafBLight(){

        String updateRecommendations = recommedtxt.getText().toString();
        String updateChem = chemedtxt.getText().toString();
        String updateTips = tipstxt.getText().toString();
        String updateReference = refTxt.getText().toString();

        if (!updateRecommendations.isEmpty() && !updateChem.isEmpty() ){
            DataManager dataManager = new DataManager(this);
            try {
                // Open the database for writing
                dataManager.open();

                // Retrieve the "blightRecommendation" record
                Cursor cursor = dataManager.getAllRecords();
                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        int ID = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                        int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                        int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                        int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);
                        int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                        int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                        if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0){
                            int recId = cursor.getInt(ID);
                            String name = cursor.getString(nameIndex);
                            String value = cursor.getString(valueIndex);
                            String chemicalRecommendation = cursor.getString(chemValueIndex);
                            String carefultips = cursor.getString(tipsValueIndex);
                            String references = cursor.getString(refValueIndex);

                            // Check if the columns are present in the cursor
                            if ("blightRecommendation".equals(name)) {
                                // Update the "blightRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, updateRecommendations, updateChem,updateTips,updateReference);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
                                    Toast.makeText(this, "blightRecommendation updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
                                    Toast.makeText(this, "Error updating blightRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
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
        }else {
            chemedtxt.setError("Please Input Text");
        }


    }

    private void updatePurpleBlotch(){

        String updateRecommendations = recommedtxt.getText().toString();
        String updateChem = chemedtxt.getText().toString();
        String updateTips = tipstxt.getText().toString();
        String updateReference = refTxt.getText().toString();

        if (!updateRecommendations.isEmpty() && !updateChem.isEmpty() ){
            DataManager dataManager = new DataManager(this);
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
                        int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                        int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                        if (ID >= 0 && nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0){
                            int recId = cursor.getInt(ID);
                            String name = cursor.getString(nameIndex);
                            String value = cursor.getString(valueIndex);
                            String chemicalRecommendation = cursor.getString(chemValueIndex);

                            // Check if the columns are present in the cursor
                            if ("purpleblotchRecommendation".equals(name)) {
                                // Update the "purpleblotchRecommendation" record
                                int rowsAffected = dataManager.updateRecord(recId, name, updateRecommendations, updateChem,updateTips,updateReference);

                                if (rowsAffected > 0) {
                                    // Record updated successfully
//                            Toast.makeText(this, "purpleblotchRecommendation updated successfully", Toast.LENGTH_SHORT).show();
                                } else {
                                    // There was an error updating the record
//                            Toast.makeText(this, "Error updating purpleblotchRecommendation", Toast.LENGTH_SHORT).show();
                                }
                                break;
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
        }else {
            chemedtxt.setError("Please Input Text");
        }


    }
}