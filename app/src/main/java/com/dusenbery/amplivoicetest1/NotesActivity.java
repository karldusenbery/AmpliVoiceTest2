package com.dusenbery.amplivoicetest1;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    private FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        initializeUI();
    }

    private void initializeUI() {
        addBtn = (FloatingActionButton)findViewById(R.id.addFab);
    }

    // Executes this code when the plus sign floating action button at the bottom is clicked
    public void onAddFabClicked(View v)
    {
        Toast.makeText(this, "Clicked on Add button", Toast.LENGTH_SHORT).show();
    }

}
