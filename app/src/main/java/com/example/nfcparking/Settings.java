package com.example.nfcparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Settings extends AppCompatActivity {

    private Button logout,edit;
    TextView tvWlcEmail,tvName,tvEmail, tvPhone, tvNumberPlate, tvCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        logout = findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Settings.this, LoginActivity.class));
                finish();
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Homepage selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.NFC:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), UserProfile.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.settings:
                        return true;
                }
                return false;
            }
        });

//        edit = findViewById(R.id.edit_profile_btn);
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                UserProfile userProfile = new UserProfile();
//
//                // Create a new Intent to start the EditProfileActivity
//                Intent intent = new Intent(Settings.this, EditProfile.class);
//
//                // Pass data from UserProfileActivity to EditProfileActivity using Intent extras
//                intent.putExtra("name", userProfile.getName());
//                intent.putExtra("email", userProfile.getEmail());
//                intent.putExtra("phone", userProfile.getPhone());
//                intent.putExtra("numberPlate", userProfile.getNumberPlate());
//                intent.putExtra("card", userProfile.getCard());
//
//                // Start the EditProfileActivity
//                startActivity(intent);
//            }
//        });
    }


}