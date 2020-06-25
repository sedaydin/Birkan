package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    Button btn, delete;
    FirebaseAuth firebaseAuth;

    private Query query;
    private RecyclerView List;
    private String noteID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference NoteBook = db.collection("Notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List = (RecyclerView) findViewById(R.id.recycler_view);
        List.setLayoutManager(new LinearLayoutManager(this));

        btn = findViewById(R.id.button);
        delete = findViewById(R.id.delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NoteActivity.class));
            }
        });


    }



    @Override
    protected void onStart() {

        super.onStart();
        com.google.firebase.firestore.Query query = NoteBook.orderBy("note", com.google.firebase.firestore.Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ContactDb> options =
                new FirestoreRecyclerOptions.Builder<ContactDb>()
                        .setQuery(query, ContactDb.class).build();

         FirestoreRecyclerAdapter<ContactDb, MainActivity.FindHolder> adapter =
                new FirestoreRecyclerAdapter<ContactDb, MainActivity.FindHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final MainActivity.FindHolder holder, final int position, @NonNull final ContactDb model) {
                        holder.title.setText(model.getTitle());
                        holder.note.setText(model.getNote());



                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("LongLogTag")
                            @Override
                            public void onClick(View view) {
                                String noteId = getSnapshots().getSnapshot(position).getId();

                                Intent i = new Intent(MainActivity.this, NoteDetailActivity.class);
                                i.putExtra("noteId", noteId);
                                startActivity(i);

                            }
                        });
                        holder.del.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String noteId = getSnapshots().getSnapshot(position).getId();
                                db.collection("Notes").document(noteId).delete();

                        }
                    });


                }


        public MainActivity.FindHolder onCreateViewHolder (@NonNull ViewGroup viewGroup,int viewType)
        {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_notes, viewGroup, false);
            MainActivity.FindHolder viewHolder = new MainActivity.FindHolder(view);
            return viewHolder;
        }
    }

    ;


       List.setAdapter(adapter);
        adapter.startListening();

}


public static class FindHolder extends RecyclerView.ViewHolder {
    TextView title, note;
    Button del;


    public FindHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        note = itemView.findViewById(R.id.note);
        del = itemView.findViewById(R.id.delete);
    }
}
}
