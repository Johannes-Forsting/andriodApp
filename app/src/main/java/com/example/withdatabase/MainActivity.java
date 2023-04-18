package com.example.withdatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private FirebaseService firebaseService;
    private ArrayAdapter myAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.myList);
        myAdapter = new ArrayAdapter(this, R.layout.myrow, R.id.rowTextView);
        firebaseService = new FirebaseService(myAdapter);
        listView.setAdapter(myAdapter);
        myAdapter.add(firebaseService.notes);
        firebaseService.startListener();
    }

    private static final int PICK_IMAGE_REQUEST = 1;

    private void chooseImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            // Generate an image name, e.g., using a timestamp
            String imageName = "IMG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()) + ".jpg";

            // Call the uploadImage function
            FirebaseService firebaseService = new FirebaseService(myAdapter);
            firebaseService.uploadImage(imageName, imageUri);
        }
    }








}