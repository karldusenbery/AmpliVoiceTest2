package com.dusenbery.amplivoicetest1;

import android.content.Intent;
import android.os.Bundle;

import com.dusenbery.amplivoicetest1.model.Note;
import com.dusenbery.amplivoicetest1.utilities.NoteAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class NotesActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;
    private FloatingActionButton testNotesActivityBtn;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("notes");

    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        initializeUI();

        setUpRecyclerView();

        // Enables the back button in the action bar at the top of the screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Removes the app title in the action bar at the top of the screen
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Executes this code when the microphone icon floating action button at the bottom is clicked
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Let's add a note", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // {Test purposes only} Launches the NoteDetailActivity when the notes floating action button is clicked
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NotesActivity.this, NoteDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView() {
        Query query = notebookRef.orderBy("creationDate", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Note> options = new FirestoreRecyclerOptions.Builder<Note>()
                .setQuery(query, Note.class)
                .build();

        adapter = new NoteAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void initializeUI() {
        addBtn = (FloatingActionButton)findViewById(R.id.addFab);
    }

    // Creates a listener for the action bar at the top of the screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Enables the action bar at the top of the screen
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
