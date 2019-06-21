package com.dusenbery.amplivoicetest1;

import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.dusenbery.amplivoicetest1.model.User;
import com.dusenbery.amplivoicetest1.utilities.ConvertEpoch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvFirstName;
    private TextView tvLastName;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private static final String TAG = DashboardActivity.class.getName();

    String userID, email, createdDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        //Using SharedPreferences to pull the user persistent data from a key-value pair
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
        String firstName = myPreferences.getString("FIRST_NAME", "unknown");
        String lastName = myPreferences.getString("LAST_NAME", "unknown");

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore
        initFirestore();

        // Get a reference to the users Firestore collection
        CollectionReference users = mFirestore.collection("users");

        // Gets the current authenticated user from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Gets the current user's uid from the Firestore database and stores it as a String
        userID = user.getUid();

        //Gets the current user's email address from the Firestore database and stores it as a String
        email = user.getEmail();

        // Gets the current user's created date from the Firestore database and stores it as a String
        //createdDate = Long.toString(user.getMetadata().getCreationTimestamp());

        ConvertEpoch convert = new ConvertEpoch();
        createdDate = convert.epochToIso8601(user.getMetadata().getCreationTimestamp());

        // Creates a new User object
        User mUser = new User(firstName, lastName);

        // Sets user email field the current user object
        mUser.setEmail(email);

        // Sets user createdAtDate field the current user object
        mUser.setCreationDate(createdDate);

        // Add a new document to the users collection with the created User object
        users.document(userID).set(mUser);


        //TODO: Get first and last name fields from Firestore
        String yourFirstname;
        String yourLastName;

        DocumentReference docRef = mFirestore.collection("users").document(userID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        tvFirstName = (TextView)findViewById(R.id.tvFirstName);
        tvFirstName.setText(firstName);
        tvLastName = (TextView)findViewById(R.id.tvLastName);
        tvLastName.setText(lastName);

    }

    private void initFirestore() {
        // Access a Cloud Firestore instance from your Activity
        mFirestore = FirebaseFirestore.getInstance();
    }
}