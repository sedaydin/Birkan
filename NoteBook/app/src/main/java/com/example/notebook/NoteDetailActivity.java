package com.example.notebook;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class NoteDetailActivity extends AppCompatActivity {
  Button btn;
  EditText title,note;

    private String parsednoteID;
    private FirebaseFirestore firebaseFireStore;
    private String noteID,demoID;
//    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        noteID = getIntent().getStringExtra("noteId");
        firebaseFireStore=FirebaseFirestore.getInstance();

        demoID = "ad834989-3759-4fad-9b9f-bd70f18a847d";

        btn=findViewById(R.id.save);
        title=findViewById(R.id.title);
        note=findViewById(R.id.note);
//        noteID=firebaseFireStore.collection("notebook-ba83e").document().getId();




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String notes=note.getText().toString();
                final String titlee=title.getText().toString();

                DocumentReference documentReference=firebaseFireStore.collection("Notes").document(String.valueOf(noteID));
                Map<String,Object> user=new HashMap<>();

                user.put("title", titlee);

                user.put("note",notes);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoteDetailActivity.this, "Note Kaydedildi.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NoteDetailActivity.this, "Hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(NoteDetailActivity.this, MainActivity.class));
            }

        });


//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(NoteDetailActivity.this, "Notunuz Ddeğiştirildi", Toast.LENGTH_SHORT).show();
//            }
//        });





    final DocumentReference documentReference=firebaseFireStore.collection("Notes").document(noteID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                title.setText(documentSnapshot.getString("title"));
                note.setText(documentSnapshot.getString("note"));
            }


    });
    }
}