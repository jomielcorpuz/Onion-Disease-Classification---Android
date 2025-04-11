package com.example.onionapp;

import static android.Manifest.permission.CAMERA;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onionapp.ml.Onionspringmodel30epoch;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.xml.transform.Result;

public class Capture extends AppCompatActivity {

    //Miee


    //Miel---


    public static  int counter = 0;
    private static final int MY_CAMERA_REQUEST_CODE = 101;

    LinearLayout resultviewlayout;
    TextView imageName, Confidences, catgName;
    ImageView imageViewer;
    Bitmap storeImage, cachedImage;
    Toolbar backButton;
    ImageButton galleryBtn,cameraBtn;


    private String  currentPHOTOPATH;
    public  static String Image_Name;
    private  String imageFILENAME ="IMG_";

    int imageSize =224;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        initialize();
        BottomNavigationView classify = (BottomNavigationView) findViewById(R.id.bottom_navigator);
        classify.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:
                        Intent intent = new Intent(Capture.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.about:
                        Intent intent1 = new Intent(Capture.this, About.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Capture.this, ResultViewer.class);
                    startActivity(intent);
                    counter = 1;

                }else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }

            }
        });
        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Capture.this, ResultViewer.class);

                    startActivity(intent);
                    counter = 2;

                }else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);

                }

            }
        });

    }

    public void initialize(){
        cameraBtn = findViewById(R.id.camerabtn);
        galleryBtn = findViewById(R.id.gallerybtn);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Capture.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        try {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == 100) {
                    Intent intent = new Intent(Capture.this, ResultViewer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 100);
                    counter = 1;

                }else if (requestCode == 101) {

                    Intent intent = new Intent(Capture.this, ResultViewer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 101);
                    counter = 2;
                }
            }else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }



}