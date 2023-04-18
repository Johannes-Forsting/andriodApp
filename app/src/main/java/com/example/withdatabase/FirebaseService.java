package com.example.withdatabase;

import android.net.Uri;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.withdatabase.model.Note;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirebaseService {
    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();
    private StorageReference imageReference = storageReference.child("images");
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter adapter;
    public FirebaseService(ArrayAdapter adapter){
        this.adapter = adapter;
    }

    public void uploadImage(String imageName, Uri imageUri) {
        StorageReference imageRef = storageReference.child("images/" + imageName);

        // Upload the image using the Uri
        UploadTask uploadTask = imageRef.putFile(imageUri);

        // Register success and failure listeners
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Get a URL to the uploaded content
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        System.out.println("Image uploaded successfully: " + uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                System.out.println("Image upload failed: " + exception.getMessage());
            }
        });
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
