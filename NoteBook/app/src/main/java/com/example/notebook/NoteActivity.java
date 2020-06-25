package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NoteActivity extends AppCompatActivity {
   Button save;
    EditText title,note;
    FirebaseFirestore firestore;
    UUID uuid=UUID.randomUUID();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        save=findViewById(R.id.save);
        title=findViewById(R.id.title);
        note=findViewById(R.id.note);
        firestore=FirebaseFirestore.getInstance();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String notes=note.getText().toString();
                final String titlee=title.getText().toString();

                DocumentReference documentReference=firestore.collection("Notes").document(String.valueOf(uuid));
                Map<String,Object> user=new HashMap<>();

                user.put("title", titlee);

                user.put("note",notes);

                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(NoteActivity.this, "Note Kaydedildi.", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(NoteActivity.this, "Hata oluştu", Toast.LENGTH_SHORT).show();
                    }
                });
                startActivity(new Intent(NoteActivity.this, MainActivity.class));
            }

        });
    }
}
