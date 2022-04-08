package com.huji.hackathon.paperw8;

import static android.Manifest.permission.CAMERA;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScannerAct extends AppCompatActivity {

    private ImageView captureIV;
    private TextView resultTV;
    private Button detectBtn;
    private Bitmap imageBitmap;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        captureIV = findViewById(R.id.idIVCapture);
        resultTV = findViewById(R.id.idTVDetect);
        detectBtn = findViewById(R.id.idBtnDetect);
        if(checkPrem()){
            captureImage();
        } else {
            checkPermmitions();
        }
        detectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectText();
            }
        });

//        snapBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

    }
    private boolean checkPrem(){
        int camerPer = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return camerPer == PackageManager.PERMISSION_GRANTED;
    }

    private void checkPermmitions(){
        int PERMMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, PERMMISSION_CODE);
    }

    private void captureImage(){
        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePic.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePic, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 ){
            boolean cameraPer = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if(cameraPer){
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                captureImage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            captureIV.setImageBitmap(imageBitmap);

        }
    }



    private void detectText(){
        InputImage img = InputImage.fromBitmap(imageBitmap, 0);
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        Task<Text> res = recognizer.process(img).addOnSuccessListener(new OnSuccessListener<Text>() {
            @Override
            public void onSuccess(Text text) {
                StringBuilder res = new StringBuilder();
                ArrayList<String> array = new ArrayList<>();
                for(Text.TextBlock block: text.getTextBlocks()){
                    String blockText = block.getText();
                    StringBuilder s = new StringBuilder();
                    int counter = 0;
                    if(!blockText.isEmpty())
                    {
                        for(char c: blockText.toCharArray()){
                            if (Character.isDigit(c)){
                                s.append(c);
                            }
                            if (c == '/'){
                                s.append(c);
                                counter++;
                            }
                        }
                        if(counter == 2){
                            array.add(s.toString());
                        }
                    }
                    resultTV.setText(array.get(0));

//                    Point[] blockCornerPoint = block.getCornerPoints();
//                    Rect blockFrame = block.getBoundingBox();
//                    for(Text.Line line: block.getLines()){
//                        String lineText = line.getText();
//                        Point[] lineCornerPoint = line.getCornerPoints();
//                        Rect lineRect = line.getBoundingBox();
//                        for(Text.Element element: line.getElements()){
//                            String elementText = element.getText();
//                            res.append(elementText);
//
//                        }
//
//                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ScannerAct.this,"Fail to etect text from image" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}