package com.example.onionapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private static final String TAG = "History" ;
    TextView info;
    BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private DBHelper dbHelper;
    ArrayList<ModelClass> modelClassArrayList;
    RecyclerViewAdapter objectRVAdapter;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
        ReadDataforView();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(History.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.classify:
                        Intent intent1 = new Intent(History.this, Capture.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.history:
                        return true;

                    case R.id.about:
                        Intent intent2 = new Intent(History.this, About.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });

    }
    public void init() {
        info = findViewById(R.id.Info);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        recyclerView = findViewById(R.id.Recycler_Viewer);
        dbHelper = new DBHelper(this);
        modelClassArrayList = new ArrayList<>();
        objectRVAdapter = new RecyclerViewAdapter(History.this, modelClassArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(objectRVAdapter);
    }

    public void getData() {
        try {
            modelClassArrayList.clear(); // Clear the list before adding new data
            modelClassArrayList.addAll(dbHelper.getAllImageData());
            Log.e(TAG, "Get Data: getAllImageData");
            objectRVAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ReadDataforView() {
        try {
            Cursor cursor = dbHelper.ReadData();
            if (cursor.moveToFirst()) {
                info.setVisibility(View.INVISIBLE);
                getData();
            } else {
                info.setVisibility(View.VISIBLE);
                // Handle the case when there is no data
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else {
            backToast = Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}