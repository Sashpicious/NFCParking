package com.example.nfcparking;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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
                    String card = dataSnapshot.child("card").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();

                    TextView tvWelcomeNFC = findViewById(R.id.text_welcome_nfc);

                    tvWelcomeNFC.setText("Welcome to NFC Parking, " + name + "!\nPlease select your card.");

                    ImageButton Card1 = findViewById(R.id.Card1);
                    Card1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.setComponent(new ComponentName("com.yuanwofei.cardemulator.pro","com.yuanwofei.cardemulator.ShortcutHandlerActivity"));
                            intent.putExtra("card_id", card);
                            intent.putExtra("card_name","card");
                            startActivity(intent);}
                    });
                    TextView entryCard = findViewById(R.id.entry_status);
                    TextView accessTime = findViewById(R.id.entry_time);
                    TextView userType = findViewById(R.id.user_type);
                    DatabaseReference cardsRef = FirebaseDatabase.getInstance().getReference("cardUID").child(card);
                    cardsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot == null) {
                                return;
                            }
                            // Get the card status from the dataSnapshot
                            int entryStatus = dataSnapshot.child("status").getValue(int.class);
                            String time = dataSnapshot.child("time").getValue(String.class);
                            String usertype = dataSnapshot.child("userType").getValue(String.class);
                            // Do something with the card status, like displaying it in a TextView

                            if(entryStatus == 1) {

                                entryCard.setText("Entered");
                            }
                            else {

                                entryCard.setText("Exited");
                            }

                            if (usertype.equals("1")) {
                                userType.setText("PREMIUM");
                            }
                            else {
                                userType.setText("STANDARD");
                            }

                            accessTime.setText(time);

                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Handle any errors
                            Log.e(TAG, "Failed to read value.", error.toException());
                        }
                    });




                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }




//call string from firebase and put it in intent.putExtra

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Homepage selected
        bottomNavigationView.setSelectedItemId(R.id.NFC);

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


    }


    @Override

    //keeps user logged in
    public void onStart(){

        super.onStart();

        if(mUser ==null)

        { startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        }

    }

//    public void logout() {
//
//        FirebaseAuth.getInstance().signOut();
//
//        startActivity(new Intent(MainActivity.this, LoginActivity.class));
//
//
//
//    }





