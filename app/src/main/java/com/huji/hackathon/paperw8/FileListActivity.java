package com.huji.hackathon.paperw8;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class FileListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TextView noFilesTextView = findViewById(R.id.noFilesTextView);

        String path = getIntent().getStringExtra("path");
        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        if (filesAndFolders == null || filesAndFolders.length == 0) { // no files
            noFilesTextView.setVisibility(View.VISIBLE);
            return;
        }

        noFilesTextView.setVisibility(View.INVISIBLE);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new ListFilesAdapter(getApplicationContext(), filesAndFolders));
    }
}