package com.example.nfcparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private String userID;



    private Button btnLogout;


    private TextView tvName,tvEmail, tvPhone, tvNumberPlate, tvCard;


    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_profile);
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Homepage selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

        tvName = findViewById(R.id.text_name);
        tvEmail = findViewById(R.id.text_email);
        tvPhone = findViewById(R.id.text_phone_number);
        tvNumberPlate = findViewById(R.id.text_number_plate);
        tvCard = findViewById(R.id.text_card_number);




        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.NFC:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),Settings.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

//reading from firebase
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mUser = mAuth.getCurrentUser();


        if (mUser != null) {

            userID = mUser.getUid();


            DatabaseReference ref = mDatabase.child(userID);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    if (name != null && !name.isEmpty()) {
                        tvName.setText(name);
                    }
                    String useremail = dataSnapshot.child("useremail").getValue(String.class);
                    if (useremail != null && !useremail.isEmpty()) {
                        tvEmail.setText(useremail);
                    }
                    String phone = dataSnapshot.child("phone").getValue(String.class);
                    if (phone != null && !phone.isEmpty()) {
                        tvPhone.setText(phone);
                    }
                    String numberPlate = dataSnapshot.child("numberPlate").getValue(String.class);
                    if (numberPlate != null && !numberPlate.isEmpty()) {
                        tvNumberPlate.setText(numberPlate);
                    }
                    String card = dataSnapshot.child("card").getValue(String.class);
                    if (card != null && !card.isEmpty()) {
                        tvCard.setText(card);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }

    }

//    public String getName() {
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//        mUser = mAuth.getCurrentUser();
//
//
//        if (mUser != null) {
//
//            userID = mUser.getUid();
//
//
//            DatabaseReference ref = mDatabase.child(userID);
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String name = dataSnapshot.child("name").getValue(String.class);
//                    if (name != null && !name.isEmpty()) {
//                        tvName.setText(name);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        return tvName.getText().toString();
//    }
//
//        public String getEmail() {
//            mAuth = FirebaseAuth.getInstance();
//            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//            mUser = mAuth.getCurrentUser();
//
//
//            if (mUser != null) {
//
//                userID = mUser.getUid();
//
//
//                DatabaseReference ref = mDatabase.child(userID);
//                ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        String name = dataSnapshot.child("useremail").getValue(String.class);
//                        if (name != null && !name.isEmpty()) {
//                            tvEmail.setText(name);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        return tvEmail.getText().toString();
//    }
//
//    public String getPhone() {
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//        mUser = mAuth.getCurrentUser();
//
//
//        if (mUser != null) {
//
//            userID = mUser.getUid();
//
//
//            DatabaseReference ref = mDatabase.child(userID);
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String name = dataSnapshot.child("phone").getValue(String.class);
//                    if (name != null && !name.isEmpty()) {
//                        tvPhone.setText(name);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        return tvPhone.getText().toString();
//    }
//
//    public String getNumberPlate() {
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//        mUser = mAuth.getCurrentUser();
//
//
//        if (mUser != null) {
//
//            userID = mUser.getUid();
//
//
//            DatabaseReference ref = mDatabase.child(userID);
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String name = dataSnapshot.child("numberPlate").getValue(String.class);
//                    if (name != null && !name.isEmpty()) {
//                        tvNumberPlate.setText(name);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        return tvNumberPlate.getText().toString();
//    }
//
//    public String getCard() {
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
//        mUser = mAuth.getCurrentUser();
//
//
//        if (mUser != null) {
//
//            userID = mUser.getUid();
//
//
//            DatabaseReference ref = mDatabase.child(userID);
//            ref.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    String name = dataSnapshot.child("card").getValue(String.class);
//                    if (name != null && !name.isEmpty()) {
//                        tvCard.setText(name);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//        return tvCard.getText().toString();
    }


//    public void startEditProfile() {
//        // Create an Intent to start the EditActivity
//        Intent i = new Intent(UserProfile.this, EditProfile.class);
//
//        // Get the data you want to pass to the EditProfile
//        String userName = tvName.getText().toString();
//        String userEmail = tvEmail.getText().toString();
//        String userPhone = tvPhone.getText().toString();
//        String userNumberPlate = tvNumberPlate.getText().toString();
//        String userCard = tvCard.getText().toString();
//
//        // Put the data in the Intent as extras
//        i.putExtra("name", userName);
//        i.putExtra("email", userEmail);
//        i.putExtra("number", userPhone);
//        i.putExtra("numberplate", userNumberPlate);
//        i.putExtra("card", userCard);
//
//        // Start the EditProfile
//        startActivity(i);
//    }
