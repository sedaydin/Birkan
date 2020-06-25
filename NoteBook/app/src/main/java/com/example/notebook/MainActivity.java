package com.example.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
  Button btn, delete;
    FirebaseAuth firebaseAuth;

    private Query query;
    private RecyclerView List;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    private CollectionReference NoteBook=db.collection("Notes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List = (RecyclerView) findViewById(R.id.recycler_view);
        List.setLayoutManager(new LinearLayoutManager(this));

        btn=findViewById(R.id.button);
        delete=findViewById(R.id.delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,NoteActivity.class));
            }
        });






    }



    @Override
    protected void onStart() {

        super.onStart();
        com.google.firebase.firestore.Query query=NoteBook.orderBy("note", com.google.firebase.firestore.Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ContactDb> options=
                new FirestoreRecyclerOptions.Builder<ContactDb>()
                        .setQuery(query, ContactDb.class).build();

        FirestoreRecyclerAdapter<ContactDb, MainActivity.FindHolder> adapter=
                new FirestoreRecyclerAdapter<ContactDb, MainActivity.FindHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull MainActivity.FindHolder holder, final int position, @NonNull ContactDb model) {
                        holder.title.setText(model.getTitle());
                        holder.note.setText(model.getNote());



                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {


                                Intent i = new Intent(MainActivity.this, NoteDetailActivity.class);

                                startActivity(i);
                            }
                        });


                    }


                    public MainActivity.FindHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_show_notes, viewGroup,false);
                        MainActivity.FindHolder viewHolder= new MainActivity.FindHolder(view);
                        return viewHolder;
                    }
                };


       List.setAdapter(adapter);
        adapter.startListening();

    }









    public static class FindHolder extends RecyclerView.ViewHolder {
        TextView title,note;



        public FindHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            note=itemView.findViewById(R.id.note);

        }
    }
}
