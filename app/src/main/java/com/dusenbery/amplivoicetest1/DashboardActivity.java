package com.dusenbery.amplivoicetest1;

import android.content.Intent;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dusenbery.amplivoicetest1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvFirstName;
    private TextView tvLastName;
    private LinearLayout llprofileTab;
    private LinearLayout llNotesTab;
    private FloatingActionButton micBtn;

    private FirebaseFirestore mFirestore;
    private FirebaseAuth mAuth;
    private static final String TAG = DashboardActivity.class.getName();

    String userID, email, createdDate, mFirstName, mLastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeUI();

        // Disables and removes the back button in the action bar at the top of the screen
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // Removes the app title in the action bar at the top of the screen
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Enable Firestore logging
        FirebaseFirestore.setLoggingEnabled(true);

        // Initialize Firestore
        initFirestore();

        // Get a reference to the users Firestore collection
        final CollectionReference users = mFirestore.collection("users");

        // Gets the current authenticated user from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Gets the current user's uid from the Firestore database and stores it as a String
        userID = user.getUid();

        //Gets the current user's email address from the Firestore database and stores it as a String
        email = user.getEmail();

        // Checking if we need to get the user firstName and lastName from persistant data if this the first time launching this activity after initial registration
        // or if we need to get the user firstName and lastName from the database
            // Gets user document from Firestore as reference
            DocumentReference userDocRef = mFirestore.collection("users").document(userID);

            userDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();

                        // if the user document for this uid exists, do this code
                        if (document.exists()) {

                            Log.d(TAG, "No such document");
                            Log.d("NAMES","Your names are known from the firestore database.");

                            // gets string values from the field's string values
                            mFirstName = (String) document.getString("firstName");
                            mLastName = (String) document.getString("lastName");

                            // sets the text on the TextViews from the database (slow)
                            tvFirstName = (TextView)findViewById(R.id.tvFirstName);
                            tvFirstName.setText(mFirstName);
                            tvLastName = (TextView)findViewById(R.id.tvLastName);
                            tvLastName.setText(mLastName);

                        }

                        // if the user document for this uid doesn't exist, do this code
                        else {
                            Log.d(TAG, "No such document");
                            Log.d("NAMES","Your names are maybe known from persistant data.");

                            //Using SharedPreferences to pull the user persistent data from a key-value pair
                            SharedPreferences myPreferences
                                    = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
                            String firstName = myPreferences.getString("FIRST_NAME", "unknown");
                            String lastName = myPreferences.getString("LAST_NAME", "unknown");

                            // Creates a new User object with name strings from persistant data
                            User mUser = new User(firstName, lastName);

                            // Sets user email field the current user object
                            mUser.setEmail(email);

                            //Gets the current time and sets that as a string that represents current time in ISO 8601 format
                            TimeZone tz = TimeZone.getTimeZone("UTC");
                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                            df.setTimeZone(tz);
                            createdDate = df.format(new Date());

                            // Sets user createdAtDate field the current user object
                            mUser.setCreationDate(createdDate);

                            // Adds a new document to the users collection with the created User mUser object data
                            users.document(userID).set(mUser);

                            // sets the text on the TextViews from persistant data (fast)
                            tvFirstName = (TextView)findViewById(R.id.tvFirstName);
                            tvFirstName.setText(firstName);
                            tvLastName = (TextView)findViewById(R.id.tvLastName);
                            tvLastName.setText(lastName);
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });


        // Executes this code when the Notes tab in the bottom bar is clicked
        llNotesTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });

        // Executes this code when the Profile tab in the bottom bar is clicked
        llprofileTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeUI() {
        //initializes the TextViews
        llNotesTab = (LinearLayout) findViewById(R.id.llNotesTab);
        llprofileTab = (LinearLayout) findViewById(R.id.llprofileTab);

        // initializes the buttons
        micBtn = (FloatingActionButton)findViewById(R.id.microphoneFab);
    }

    private void initFirestore() {
        // Access a Cloud Firestore instance from your Activity
        mFirestore = FirebaseFirestore.getInstance();
    }

    // Creates a menu at the top action bar at the top of the screen
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Creates a listener for the action bar at the top of the screen
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItem_logOut:
                logOut();
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Logs the user out and takes them back to the home screen
    private void logOut(){
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // Executes this code when the microphone floating action button at the bottom is clicked
    public void onFabClicked(View v)
    {
        Toast.makeText(this, "Clicked on Mic button", Toast.LENGTH_SHORT).show();
    }
}