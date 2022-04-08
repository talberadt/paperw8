package com.huji.hackathon.paperw8;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.huji.hackathon.paperw8.databinding.ActivityMain2Binding;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList = findViewById(R.id.dataList);
        btn = findViewById(R.id.fab);

        setBasicFolders();

        gridAdapter = new GridAdapter(this, this.titles, this.images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        dataList.setLayoutManager(gridLayoutManager);
        dataList.setAdapter(gridAdapter);

        btn.setOnClickListener(v -> {
            if (checkPrem()) {
                Intent intent = new Intent(v.getContext(), folderCreationForm.class);
                v.getContext().startActivity(intent);
            }  else { requestPremission(); }

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