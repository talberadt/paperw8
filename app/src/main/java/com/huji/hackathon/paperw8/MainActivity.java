package com.huji.hackathon.paperw8;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
//import com.google.firebase.ml.vision.common.FirebaseVisionImage;
//import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;

import java.io.ByteArrayOutputStream;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn;
    RecyclerView dataList;
    List<String> titles = new ArrayList<>();
    List<Integer> images = new ArrayList<>();
    GridAdapter gridAdapter;
    Context context;



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
        btn = findViewById(R.id.fab);
        titles = new ArrayList<>();
        images = new ArrayList<>();

        setBasicFolders();

        gridAdapter = new GridAdapter(this, titles, images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(gridAdapter);

        btn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenu().add("FILE");
            popupMenu.getMenu().add("SCAN FILE");
            popupMenu.getMenu().add("FOLDER");

            popupMenu.setOnMenuItemClickListener(item -> {

                if (checkPrem()) {
                    if (item.getTitle().equals("FOLDER")) {
                            Intent intent = new Intent(v.getContext(), folderCreationForm.class);
                            v.getContext().startActivity(intent);
                        }

                    if (item.getTitle().equals("FILE")) {
//                        Intent intent = new Intent(v.getContext(), folderCreationForm.class);
//                        v.getContext().startActivity(intent);
                        return true;
                    }

                    if (item.getTitle().equals("SCAN FILE")){
                        return true;

                    }
                    return true;
            }
                else { requestPremission(); };
            popupMenu.show();
            return true;
        });
            popupMenu.show();
        });

        if (getIntent().getStringExtra("folderName") != null) {
            titles.add(getIntent().getStringExtra("folderName"));
            images.add(R.drawable.ic_baseline_blackfolder);
            dataList.setLayoutManager(gridLayoutManager);
            dataList.setAdapter(gridAdapter);

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

    private void setBasicFolders() {
        String[] basicTitles = new String[] {"Car", "Quick Access", "Bills", "University"};
        Integer[] basicImages = new Integer[] {R.drawable.ic_baseline_drive_eta_24,
                R.drawable.ic_baseline_quickaccess, R.drawable.ic_baseline_attach_money_24,
                R.drawable.ic_baseline_menu_book_24};
        String documentsPath = Environment.getExternalStorageDirectory().toString() + "/Documents/";
        for (int i = 0; i < basicTitles.length; i++) {
            this.titles.add(basicTitles[i]);
            this.images.add(basicImages[i]);
            String path = documentsPath.concat(basicTitles[i]);
            new File(path).mkdir();
        }

        File a = new File(documentsPath + "/Car/license.pdf");
        File b = new File(documentsPath + "/Car/insurance.pdf");
        try {
            a.createNewFile();
            b.createNewFile();
        } catch (IOException ignored) {}
    }

}