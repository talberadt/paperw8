package com.huji.hackathon.paperw8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fileReg extends AppCompatActivity {

    EditText fileName;
    EditText tags;
    Button addButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_reg);

        fileName = findViewById(R.id.folderNameText);
        tags = findViewById(R.id.folderTagsText);
        addButton = findViewById(R.id.SubmitButton);
        progressBar = findViewById(R.id.progressBar2);

        addButton.setOnClickListener(v -> {
            String folderNameString = fileName.getText().toString().trim();
            List<String> folderTagsString = Arrays.asList(tags.getText().toString().split(","));
            progressBar.setVisibility(View.VISIBLE);

            String path = Environment.getExternalStorageDirectory().toString() + "/Documents/" + folderNameString;
            File dir = new File(path);
            dir.mkdir();

            Intent intent = new Intent(v.getContext(), MainActivity.class);
            intent.putExtra("folderName", folderNameString);
            intent.putStringArrayListExtra("folderTags", new ArrayList<>(folderTagsString));
            v.getContext().startActivity(intent);
        });
    }
}