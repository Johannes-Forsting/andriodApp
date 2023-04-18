package com.example.withdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.withdatabase.adapter.MyAdapter;

import java.util.HashMap;
import java.util.List;

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




}