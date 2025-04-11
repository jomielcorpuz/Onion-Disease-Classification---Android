package com.example.onionapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import com.example.onionapp.ml.Onionspringmodel30epoch;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultViewer extends AppCompatActivity {

    private static final String TAG = "ResultViewer";
    LinearLayout detailslayout;
    FloatingActionsMenu FloatngMenu;
    FloatingActionButton Camera,Gallery,Home;
    ImageView imageViewer;
    TextView imageName,percentageresult,recomendationresult,chemcontrolreslt,tips,refTxt, dateTakes, time;


    public static  int counter = 0;
    public  static String Image_Name;
    private  String imageFILENAME ="IMG_";
    private String  currentPHOTOPATH;
    public static  String resultdef = "";
    int imageSize =224;
    Bitmap storeImage, cachedImage;
    DBHelper dbHelper;

    Bitmap imageToStore;
    String timeStamp, image_name, Image_path, date_Taken, ID;

    String Date_taken, DiseaseName,ImagePath,Image_ID;
    DataManager dataManager = new DataManager(this);

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommendation_layout);

        initialize();
        try {

            if (Capture.counter == 1) {
                accessCamera();
            }  else if(Capture.counter == 2){
                accessGallery();
            }
            else if (RecyclerViewAdapter.ItemCounter == 1) {

                String DateTaken = "";
                Bitmap Image = null;
                Bundle bundle = getIntent().getExtras();
                if (bundle != null){
                    Image_ID = bundle.getString("ID");
                    DiseaseName = bundle.getString("Disease_Name");
                    Image = bundle.getParcelable("Image");
                    ImagePath = bundle.getString("Image_Path");
                    DateTaken = bundle.getString("Date");
                    String dateComb = DateTaken.toString();
                    String[] dateTimeComponents = dateComb.split("");
                    String date = dateTimeComponents[0];
                    String timevalue = dateTimeComponents[1];

                    if (DiseaseName.equals("Undefined Images") | DiseaseName.equals("Healthy")){
                        detailslayout.setVisibility(View.GONE);
                        percentageresult.setVisibility(View.GONE);
                        dateTakes.setText(date);
                        //time.setText(timevalue);
                        dateTakes.setVisibility(View.VISIBLE);
                        //time.setVisibility(View.VISIBLE);
                        percentageresult.setVisibility(View.GONE);

                    }else if (DiseaseName.equals("Leaf blight")){
                        detailslayout.setVisibility(View.VISIBLE);
                        percentageresult.setVisibility(View.GONE);
                        try {
                            // Open the database for reading
                            dataManager.open();

                            // Retrieve the "blightRecommendation" record
                            Cursor cursor = dataManager.getAllRecords();
                            if (cursor != null && cursor.moveToFirst()) {
                                do {
                                    int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                                    int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                                    int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);
                                    int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                                    int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                                    // Check if the columns are present in the cursor
                                    if (nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0) {
                                        String name = cursor.getString(nameIndex);
                                        String leafBlightRecommendation = cursor.getString(valueIndex);
                                        String leafblightchemRecommendation = cursor.getString(chemValueIndex);
                                        String carefultips = cursor.getString(tipsValueIndex);
                                        String references = cursor.getString(refValueIndex);



                                        if ("blightRecommendation".equals(name)) {

                                            // Found the "blightRecommendation" record
                                            // Toast.makeText(this, "Blight Recommendation: " + value, Toast.LENGTH_SHORT).show();
                                            recomendationresult.setText(leafBlightRecommendation);
                                            dateTakes.setText(date);
                                           // time.setText(timevalue);
                                            dateTakes.setVisibility(View.VISIBLE);
                                            //time.setVisibility(View.VISIBLE);
                                            chemcontrolreslt.setText(leafblightchemRecommendation);
                                            tips.setText(carefultips);
                                            refTxt.setText(references);
                                            break;
                                        }
                                    }
                                } while (cursor.moveToNext());

                                // Close the cursor
                                cursor.close();
                            }
                        }
                        finally {
                            // Close the database
                            dataManager.close();
                        }
                    } else if (DiseaseName.equals("Purple Blotch")) {
                        detailslayout.setVisibility(View.VISIBLE);
                        percentageresult.setVisibility(View.GONE);
                        try {
                            // Open the database for reading
                            dataManager.open();

                            // Retrieve the "blightRecommendation" record
                            Cursor cursor = dataManager.getAllRecords();
                            if (cursor != null && cursor.moveToFirst()) {
                                do {
                                    int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                                    int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                                    int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);
                                    int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                                    int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                                    // Check if the columns are present in the cursor
                                    if (nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0) {
                                        String name = cursor.getString(nameIndex);
                                        String purpleBLotchRecommendation = cursor.getString(valueIndex);
                                        String purpleBlotchChemControl = cursor.getString(chemValueIndex);
                                        String carefultips = cursor.getString(tipsValueIndex);
                                        String references = cursor.getString(refValueIndex);


                                        if ("purpleblotchRecommendation".equals(name)) {

                                            // Found the "blightRecommendation" record
                                            // Toast.makeText(this, "Blight Recommendation: " + value, Toast.LENGTH_SHORT).show();
                                            recomendationresult.setText(purpleBLotchRecommendation);
                                            dateTakes.setText(date);
                                            //time.setText(timevalue);
                                            dateTakes.setVisibility(View.VISIBLE);
                                            //time.setVisibility(View.VISIBLE);
                                            chemcontrolreslt.setText(purpleBlotchChemControl);
                                            tips.setText(carefultips);
                                            refTxt.setText(references);
                                            break;
                                        }
                                    }
                                } while (cursor.moveToNext());

                                // Close the cursor
                                cursor.close();
                            }
                        }
                        finally {
                            // Close the database
                            dataManager.close();
                        }
                    }
                    imageName.setText(DiseaseName);
                    imageViewer.setImageBitmap(Image);
                    dateTakes.setText(DateTaken);
                }else {
                    Log.d(TAG,"Unable to load data");

                }
            }
            else {
                Toast.makeText(this,"Error", Toast.LENGTH_SHORT).show();
            }


            Camera.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        accessCamera();
                        FloatngMenu.collapse();

                    }else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
                    }
                }
            });

            Gallery.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        accessGallery();
                        FloatngMenu.collapse();

                    }else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
                    }
                }
            });

            Home.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResultViewer.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void initialize() {
        imageViewer = findViewById(R.id.imageView);
        imageName = findViewById(R.id.diseaseName);
        percentageresult = findViewById(R.id.percentage);
        recomendationresult = findViewById(R.id.recommendationText);
        chemcontrolreslt = findViewById(R.id.chemrecommtxtvw);
        detailslayout = findViewById(R.id.detailslayout);
        Camera = findViewById(R.id.Camera);
        Gallery = findViewById(R.id.Gallery);
        Home = findViewById(R.id.Home);
        FloatngMenu = findViewById(R.id.FloatngActionMenu);
        dbHelper = new DBHelper(this);
        dateTakes = findViewById(R.id.date);
        time = findViewById(R.id.time);
        tips = findViewById(R.id.tipstxtvw);
        refTxt = findViewById(R.id.reftxtvw);

    }

    private void accessGallery() {
        try{
            Capture.counter = 2;
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(intent, 101);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void accessCamera() {

        try {
            Capture.counter = 1;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                        File imageFILE = File.createTempFile(imageFILENAME, ".jpg", storageDirectory);
                        currentPHOTOPATH = imageFILE.getAbsolutePath();

                        Uri imageURI = FileProvider.getUriForFile(ResultViewer.this,
                                "com.example.onionapp.fileprovider", imageFILE);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                        startActivityForResult(intent, 100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
    }


    //CHECK PERMISSION
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (requestCode == 100 && Capture.counter == 1) {
                    Intent intent = new Intent(ResultViewer.this, ResultViewer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 100);
                    Capture.counter = 1;

                }else if (requestCode == 101 && Capture.counter == 2) {

                    Intent intent = new Intent(ResultViewer.this, ResultViewer.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivityForResult(intent, 101);
                    Capture.counter = 2;
                }
            }else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //CLASSIFY FUNCTION
    @SuppressLint("DefaultLocale")
    public void classifyImage(Bitmap image) {
        try {
            Onionspringmodel30epoch model = Onionspringmodel30epoch.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            int pixel = 0;
            for (int i = 0; i < imageSize; ++i) {
                for (int j = 0; j < imageSize; ++j) {
                    final int val = intValues[pixel++];


                    int IMAGE_MEAN = 0;
                    float IMAGE_STD = 255.0f;
                    byteBuffer.putFloat((((val >> 16) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                    byteBuffer.putFloat((((val >> 8) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);
                    byteBuffer.putFloat((((val) & 0xFF)-IMAGE_MEAN)/IMAGE_STD);

//                    byteBuffer.putFloat(((val >> 16) & 0xFF ) * (1.f/255));
//                    byteBuffer.putFloat(((val >> 8) & 0xFF ) * (1.f/255));
//                    byteBuffer.putFloat((val & 0xFF ) * (1.f/255));

                }
            }
            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Onionspringmodel30epoch .Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; ++i) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] nameClass = {"Healthy", "Leaf blight", "Purple Blotch", "Undefined Images"};



            //IDENTIFIY THE RESULT FROM THE MODEL TO THE (nameClass)
            imageName.setText(nameClass[maxPos]);
            //SAVE/SET THE RESULT
            Image_Name = imageName.getText().toString();

            String CONFIDENCE_LEVEL = String.format("%.2f", maxConfidence * 100);
            percentageresult.setText(CONFIDENCE_LEVEL+"%");

            //CLASSIFIY THE RESULT AND SET THE RECOMMENDATION LAYOUT
            try {
                if (Image_Name.equals("Leaf blight")) {
                    detailslayout.setVisibility(View.VISIBLE);
//                    recomendationresult.setText(R.string.leaf_blight);
//                    chemcontrolreslt.setText(R.string.leafblight_chem_control);
                    try {
                        // Open the database for reading
                        dataManager.open();

                        // Retrieve the "blightRecommendation" record
                        Cursor cursor = dataManager.getAllRecords();
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                                int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                                int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);
                                int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                                int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                                // Check if the columns are present in the cursor
                                if (nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0) {
                                    String name = cursor.getString(nameIndex);
                                    String leafBlightRecommendation = cursor.getString(valueIndex);
                                    String leafblightchemRecommendation = cursor.getString(chemValueIndex);
                                    String carefultips = cursor.getString(tipsValueIndex);
                                    String references = cursor.getString(refValueIndex);


                                    if ("blightRecommendation".equals(name)) {

                                        percentageresult.setVisibility(View.VISIBLE);
                                        // Found the "blightRecommendation" record
                                        // Toast.makeText(this, "Blight Recommendation: " + value, Toast.LENGTH_SHORT).show();
                                        recomendationresult.setText(leafBlightRecommendation);
                                        chemcontrolreslt.setText(leafblightchemRecommendation);
                                        tips.setText(carefultips);
                                        refTxt.setText(references);
                                        Capture.counter = 0;
                                        break;
                                    }
                                }
                            } while (cursor.moveToNext());

                            // Close the cursor
                            cursor.close();
                        }
                    }
                    finally {
                        // Close the database
                        dataManager.close();
                    }
                }
                else if(Image_Name.equals("Purple Blotch")){

                    detailslayout.setVisibility(View.VISIBLE);
//                    recomendationresult.setText(R.string.purple_blotch);
//                    chemcontrolreslt.setText(R.string.purpleblotch_chem_control);

                    try {
                        // Open the database for reading
                        dataManager.open();

                        // Retrieve the "blightRecommendation" record
                        Cursor cursor = dataManager.getAllRecords();
                        if (cursor != null && cursor.moveToFirst()) {
                            do {
                                int nameIndex = cursor.getColumnIndex(DBHelper.COLUMN_NAME);
                                int valueIndex = cursor.getColumnIndex(DBHelper.COLUMN_VALUE);
                                int chemValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CHEMICAL_RECOMMENDATION);
                                int tipsValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_CAREFULTIPS);
                                int refValueIndex = cursor.getColumnIndex(DBHelper.COLUMN_REFERENCES);

                                // Check if the columns are present in the cursor
                                if (nameIndex >= 0 && valueIndex >= 0 && chemValueIndex >= 0) {
                                    String name = cursor.getString(nameIndex);
                                    String purpleBLotchRecommendation = cursor.getString(valueIndex);
                                    String purpleBlotchChemControl = cursor.getString(chemValueIndex);
                                    String carefultips = cursor.getString(tipsValueIndex);
                                    String references = cursor.getString(refValueIndex);


                                    if ("purpleblotchRecommendation".equals(name)) {

                                        percentageresult.setVisibility(View.VISIBLE);
                                        // Found the "blightRecommendation" record
                                        // Toast.makeText(this, "Blight Recommendation: " + value, Toast.LENGTH_SHORT).show();
                                        recomendationresult.setText(purpleBLotchRecommendation);
                                        chemcontrolreslt.setText(purpleBlotchChemControl);
                                        tips.setText(carefultips);
                                        refTxt.setText(references);
                                        Capture.counter = 0;
                                        break;
                                    }
                                }
                            } while (cursor.moveToNext());

                            // Close the cursor
                            cursor.close();
                        }
                    }
                    finally {
                        // Close the database
                        dataManager.close();
                    }
                }
                else if(Image_Name.equals("Healthy")){
                    percentageresult.setVisibility(View.GONE);
                    detailslayout.setVisibility(View.GONE);
                    Capture.counter = 0;
                }  else if(Image_Name.equals("Undefined Images")){
                    percentageresult.setVisibility(View.GONE);
                    detailslayout.setVisibility(View.GONE);
                    Capture.counter = 0;
                }
                else {
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 100 && Capture.counter == 1) {
                    try {

                        onImageResult(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    if (requestCode == 101 && Capture.counter == 2) {
                        Uri dat = data.getData();
                        try {

                            storeImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                            //int rotate = getCameraPhotoOrientation(imageViewer, currentPHOTOPATH);
                            Matrix matrix = new Matrix();
                            matrix.postRotate(270);
                            cachedImage = Bitmap.createBitmap(storeImage, 0, 0, storeImage.getWidth(), storeImage.getHeight(), matrix, true);
                            imageViewer.setImageBitmap(cachedImage);
                            storeImage = Bitmap.createScaledBitmap(storeImage, imageSize, imageSize, false);

                            //---------------------------Image Classifier CNN ---------------------------
                            classifyImage(storeImage);
                            saveImage(storeImage);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }else{
                Intent intent = new Intent(ResultViewer.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP |
                        Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }


    public static int getCameraPhotoOrientation(ImageView context, String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(
                    imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    private void onImageResult(Intent data){

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = 2;
        Bitmap cachedImage = BitmapFactory.decodeFile(currentPHOTOPATH, o2);
        int rotate = getCameraPhotoOrientation(imageViewer, currentPHOTOPATH);
        Matrix matrix = new Matrix();
        matrix.postRotate(rotate);
        cachedImage = Bitmap.createBitmap(cachedImage , 0, 0, cachedImage.getWidth(), cachedImage.getHeight(), matrix, true);
        imageViewer.setImageBitmap(cachedImage);

        //---------------------------Image Classifier CNN ---------------------------
        storeImage = Bitmap.createScaledBitmap(cachedImage, imageSize, imageSize, false);
        classifyImage(storeImage);
        saveImage(storeImage);
    }
    public void saveImage (Bitmap bitmap){

        Log.d(TAG, "saveImage: Run saveImage");
        try {
            //------------------------ FOR Image PAth ------------------------
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
            String dateCapture = dateFormat1.format(new Date());
            String imageFileName = "IMG_" + dateCapture + ".jpg";
            String filename = String.format(imageFileName, dateCapture);

            Log.d(TAG, "saveImage: Run ImagePath");

            //------------------------ FOR Date Taken ------------------------
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeStamp = dateFormat.format(new Date());

            image_name = imageName.getText().toString();
            Image_path = filename;
            date_Taken =  timeStamp;
            ID = null;

            Log.d(TAG, "saveImage: Run Date Taken");
            if (!(imageName.getText().toString().equals("No Data")) && imageViewer.getDrawable()!=null && storeImage != null)
            {
                dbHelper.StoreImage(new ModelClass(ID, image_name, bitmap, Image_path,date_Taken));
                Toast.makeText(ResultViewer.this, "Image Saved", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "saveImage: Image Saved to Database");

            }else
            {
                Toast.makeText(ResultViewer.this, "Please capture Image or Select from Gallery ", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void leafblightValue() {




    }
}

