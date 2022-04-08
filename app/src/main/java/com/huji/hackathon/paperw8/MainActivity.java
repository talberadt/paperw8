package com.huji.hackathon.paperw8;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    Button addBtn;

    private Button captureB;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        captureB = findViewById(R.id.imgButton);
        captureB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ScannerAct.class);
                startActivity(i);
            }
        });

        dataList = findViewById(R.id.dataList);
        titles = new ArrayList<>();
        images = new ArrayList<>();
//        File f = new File("drawable/image.jpg");
//        Bitmap bitmap;
//        try {
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(f));
//        } catch (IOException e) {
//            return;
//        }
        // Convert bitmap to base64 encoded string
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        String base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP);
//         FirebaseFunctions mFunctions;
//        mFunctions = FirebaseFunctions.getInstance();


        for(int i = 0; i < 5; i++) {
            this.titles.add("car");
            this.images.add(R.drawable.ic_baseline_drive_eta_24);
        }

        adapter = new Adapter(this, titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

//        addBtn = findViewById(R.id.addButton);
//        addBtn.setOnClickListener(v -> {
//            if (checkPrem()) {
//                titles.add("new");
//                images.add(R.drawable.ic_baseline_drive_eta_24);
//                dataList.setLayoutManager(gridLayoutManager);
//                dataList.setAdapter(adapter);
//                String path = Environment.getExternalStorageDirectory().toString() + "/Documents/new";
//                new File(path).mkdir();
//            }  else { requestPremission(); }
//        });


    }

    private boolean checkPrem() {
        int res = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (res == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    private void requestPremission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "GIVE STORAGE PERMISSION MF!", Toast.LENGTH_SHORT).show();

        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);       //
    }

}