package com.example.nfcparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText resName,resEmail,resPassword;
    private EditText resPhone,resNumberPlate,resCard;



    private Button btnRegister;
    private TextView textLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        resName = findViewById(R.id.register_name);
        resEmail = findViewById(R.id.register_email);
        resPassword = findViewById(R.id.register_password);
        resPhone = findViewById(R.id.register_number);
        resNumberPlate = findViewById(R.id.register_number_plate);
        resCard = findViewById(R.id.register_card);
        btnRegister  = findViewById(R.id.register);
        textLogin = findViewById(R.id.text_login);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void Register()
    {
        String name = resName.getText().toString().trim();
        String useremail = resEmail.getText().toString().trim();
        String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String pass = resPassword.getText().toString().trim();
        String phone = resPhone.getText().toString().trim();
        String numberPlate = resNumberPlate.getText().toString().trim();
        String card = resCard.getText().toString().trim();
        String cardPattern="[A-F0-9]+:[A-F0-9]+:[A-F0-9]+:[A-F0-9]";
        if(name.isEmpty())
        {
            resName.setError("Name can not be empty");
        }
        if(useremail.isEmpty())
        {
            resEmail.setError("Email can not be empty");

            if (!useremail.matches(emailPattern)){
                resEmail.setError("Invalid Email");
            }
        }

        if(pass.isEmpty())
        {
            resPassword.setError("Password can not be empty");
        }
        if(phone.isEmpty())
        {
            resPassword.setError("Password can not be empty");
        }
        if(numberPlate.isEmpty())
        {
            resPassword.setError("Password can not be empty");
        }
        if(card.isEmpty())
        {
            resPassword.setError("Password can not be empty");
            if (!card.matches(cardPattern)){
                resCard.setError("Invalid Card UID");
            }
        }
        else
        {
            //registration passing to Firebase
            mAuth.createUserWithEmailAndPassword(useremail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        User user = new User(name, useremail, phone, numberPlate, card, 0, "0", "-");

                        FirebaseDatabase.getInstance().getReference("cardUID")
                                .child(card)
                                        .setValue(user);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user);



                        mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {


                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "User registered successfully. Please verify your email address ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this, "Registration Failed"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}