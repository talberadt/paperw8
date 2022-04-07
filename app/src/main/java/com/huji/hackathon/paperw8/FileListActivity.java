package com.huji.hackathon.paperw8;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class FileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TextView noFilesTextView = findViewById(R.id.noFilesTextView);

        String path = getIntent().getStringExtra("path");
//        File root = new File(path)
    }
}