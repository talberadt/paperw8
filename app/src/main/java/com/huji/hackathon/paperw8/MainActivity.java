package com.huji.hackathon.paperw8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    RecyclerView dataList;
    List<String> titles;
    List<Integer> images;
    Adapter adapter;
    Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = findViewById(R.id.dataList);
        titles = new ArrayList<>();
        images = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            this.titles.add("car");
            this.images.add(R.drawable.ic_baseline_drive_eta_24);
        }

        adapter = new Adapter(this, titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(adapter);

        addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(v -> {
            if (checkPrem()) {
//                String path = Environment.getExternalStorageDirectory().toString() + "/Documents/new";
//                File dir = new File(path);
//                dir.mkdir();
                Intent intent = new Intent(v.getContext(), folderCreationForm.class);
                v.getContext().startActivity(intent);



            }  else { requestPremission(); }

        });

        if (getIntent().getStringExtra("folderName") != null){

            titles.add(getIntent().getStringExtra("folderName"));
            images.add(R.drawable.ic_baseline_drive_eta_24);
            Log.e("aaaa", "bbbbbb");
            dataList.setLayoutManager(gridLayoutManager);
            dataList.setAdapter(adapter);


        }

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