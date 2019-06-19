package com.dusenbery.amplivoicetest1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dusenbery.amplivoicetest1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegistrationActivity extends AppCompatActivity {

    private EditText et_FirstName, et_LastName, et_Email, et_Password;
    private Button regBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Enables the back button in the action bar at the top of the screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Removes the app title in the action bar at the top of the screen
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth = FirebaseAuth.getInstance();

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        String firstName, lastName, email, password;
        firstName = et_FirstName.getText().toString();
        lastName= et_LastName.getText().toString();
        email = et_Email.getText().toString();
        password = et_Password.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Please enter a first name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "Please enter a last name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

        /**
         *
         * Using SharedPreferences to store the user persistent data as a key-value pair
         */
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putString("FIRST_NAME", firstName);
        myEditor.putString("LAST_NAME", lastName);
        myEditor.apply();



    }

    private void initializeUI() {
        et_FirstName = findViewById(R.id.etFirstName);
        et_LastName = findViewById(R.id.etLastName);
        et_Email = findViewById(R.id.etEmail);
        et_Password = findViewById(R.id.etPassword);
        regBtn = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
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