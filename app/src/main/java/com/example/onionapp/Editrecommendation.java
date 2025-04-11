package com.example.onionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Editrecommendation extends AppCompatActivity {

    Button updateLeafblight, updatePurpleblotch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editrecommendation);
        init();

        BottomNavigationView btnNav = (BottomNavigationView) findViewById(R.id.menubottom_navigator);
        btnNav.setSelectedItemId(R.id.edit);
        btnNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(Editrecommendation.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.edit:
                        return true;

                }
                return false;
            }
        });

        updatePurpleblotch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Editrecommendation.this, UpdateRecommendation.class);
                intent.putExtra("id","Purple Blotch");
                startActivity(intent);
            }
        });

        updateLeafblight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Editrecommendation.this, UpdateRecommendation.class);
                intent1.putExtra("id","Leaf Blight");
                startActivity(intent1);
            }
        });

    }
    private void init(){
        updateLeafblight = findViewById(R.id.leafblightbtnUpdate);
        updatePurpleblotch = findViewById(R.id.purpleblotchbtnUpdate);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Editrecommendation.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}