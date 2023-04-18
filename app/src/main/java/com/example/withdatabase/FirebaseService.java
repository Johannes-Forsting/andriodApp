package com.example.withdatabase;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.withdatabase.model.Note;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FirebaseService {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter adapter;
    public FirebaseService(ArrayAdapter adapter){
        this.adapter = adapter;
    }


    public void addNote(String txt){
        DocumentReference ref = db.collection("notes").document();
        Map<String, String> map = new HashMap<>();
        map.put("text", txt);
        ref.set(map);

    }

    public void addNoteComplete(String text){
        DocumentReference ref = db.collection("notes").document();
        Map<String, String> map = new HashMap<>();
        map.put("text", text);
        ref.set(map).addOnSuccessListener(unused -> System.out.println("Document saved, " + text
        )).addOnFailureListener(e -> System.out.println("Document NOT saved, " + text));

    }

    public List<Note> notes = new ArrayList<>();
    public void startListener(){

        db.collection("notes").addSnapshotListener((snap, error)->{
            if(error == null){
                notes.clear();
                for(DocumentSnapshot s : snap.getDocuments()){
                    String text = s.getData().get("text").toString();
                    Note note = new Note(s.getId(), text);
                    notes.add(note);
                    System.out.println("Text added to list: " + text);
                }
                adapter.clear();
                adapter.addAll(notes);
                adapter.notifyDataSetChanged();
            }
        });
    }




}
