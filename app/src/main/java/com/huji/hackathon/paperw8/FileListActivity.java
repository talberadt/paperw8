package com.huji.hackathon.paperw8;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListActivity extends AppCompatActivity {

    FloatingActionButton btn;
    RecyclerView dataList;
    List<String> titles = new ArrayList<>();
    List<Integer> images = new ArrayList<>();
    GridAdapter gridAdapter;
    Context context;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        RecyclerView recyclerView = findViewById(R.id.recycleView);
        TextView noFilesTextView = findViewById(R.id.noFilesTextView);
        btn = findViewById(R.id.fab);

        if (getIntent().getStringExtra("path") != null) {
            path = getIntent().getStringExtra("path");
        }


        btn.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.getMenu().add("SCAN FILE");
            popupMenu.getMenu().add("CHOOSE PHOTO FROM GALLERY");
            popupMenu.getMenu().add("FOLDER");

            popupMenu.setOnMenuItemClickListener(item -> {


                if (item.getTitle().equals("FOLDER")) {
                    Intent intent = new Intent(v.getContext(), folderCreationForm.class);
                    intent.putExtra("path", path);

                    v.getContext().startActivity(intent);
                }

                if (item.getTitle().equals("CHOOSE PHOTO FROM GALLERY")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivity(pickPhoto);
                    return true;
                }

                if (item.getTitle().equals("CHOOSE FILE FROM DEVICE")) {

                    return true;
                }

                if (item.getTitle().equals("SCAN FILE")) {
                    Intent i = new Intent(FileListActivity.this, ScannerAct.class);
                    startActivity(i);

                }
                return true;
            });

            popupMenu.show();
        });

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
