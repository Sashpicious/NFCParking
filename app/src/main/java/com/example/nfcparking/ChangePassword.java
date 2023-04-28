package com.example.nfcparking;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePassword extends AppCompatActivity {

    ProgressDialog progressDialog;
    @NonNull
    ChangePassword binding;
    FirebaseAuth mAuth;
    EditText resetEmail;

    Button btnResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        resetEmail=findViewById(R.id.forgotEmail);
        btnResetPass=findViewById(R.id.resetPass);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        btnResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail()){
                    return;
                }
                String email = resetEmail.getText().toString();
                progressDialog.setTitle("Sending Email");
                progressDialog.show();

                mAuth.sendPasswordResetEmail(email)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                progressDialog.cancel();
                                Toast.makeText(ChangePassword.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.cancel();
                                Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }


        });
        
    }

    private Boolean validateEmail() {
        String val = resetEmail.getText().toString();
        if(val.isEmpty()){
            resetEmail.setError("Field cannot be empty!");
            return false;
        }
        else{
            resetEmail.setError(null);
            return true;
        }
    }
}