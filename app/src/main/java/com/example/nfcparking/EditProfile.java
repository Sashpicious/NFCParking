package com.example.nfcparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";

    EditText edName, edEmail, edNumber, edNumberPlate, edCard;

    Button btnSave, btnCancel;

    BottomNavigationView bottomNavigationView;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private FirebaseDatabase mFirebaseDatabase;


    private String userID;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Homepage selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.NFC:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        Intent data = getIntent();
//        String name = data.getStringExtra("name");
        String email = data.getStringExtra("email");
        String number = data.getStringExtra("number");
        String numberplate = data.getStringExtra("numberplate");
        String card = data.getStringExtra("card");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        edName = findViewById(R.id.text_name);
        edEmail = findViewById(R.id.text_email);
        edNumber = findViewById(R.id.text_phone_number);
        edNumberPlate = findViewById(R.id.text_number_plate);
        edCard = findViewById(R.id.text_card_number);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating User Info");
        progressDialog.setMessage("Please Wait...");

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edName.getText().toString().isEmpty() || edEmail.getText().toString().isEmpty() || edNumber.getText().toString().isEmpty() ||
                        edNumberPlate.getText().toString().isEmpty()  || edCard.getText().toString().isEmpty()) {
                    Toast.makeText(EditProfile.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = edName.getText().toString();
                progressDialog.show();
                mUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DatabaseReference ref = mDatabase.child(userID);
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email", email);
//                        edited.put("name", edName.getText().toString());
                        edited.put("phone", edNumber.getText().toString());
                        edited.put("numberPlate", edNumberPlate.getText().toString());
                        edited.put("card", edCard.getText().toString());
                        ref.setValue(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog.cancel();
                                Toast.makeText(EditProfile.this, "Profile has been updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                                finish();
                            }
                        });
//                        Toast.makeText(EditProfileActivity.this, "Email changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

//        edName.setText(name);
        edEmail.setText(email);
        edNumber.setText(number);
        edNumberPlate.setText(numberplate);
        edCard.setText(card);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));
                finish();
            }
        });




    }
}