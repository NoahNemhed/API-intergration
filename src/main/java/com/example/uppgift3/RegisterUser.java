package com.example.uppgift3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private EditText email,password;
    private Button submit;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        //Declaring variables.
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.register);
        submit.setOnClickListener(view ->
                createUser());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();






    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.register:
                createUser();
        }
    }


    public void createUser(){
        String mail = email.getText().toString().trim();
        String pword = password.getText().toString().trim();

        if(mail.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
            email.setError("Email is not valid.");
            email.requestFocus();
            return;
        } else if(pword.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }else{
            mAuth.createUserWithEmailAndPassword(mail,pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterUser.this, "User registed successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegisterUser.this, MainActivity.class));
                    }else{
                        Toast.makeText(RegisterUser.this, "Error", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }
}