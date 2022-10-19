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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email, password;
    private Button login, register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();
        //Declaring variables.
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, WeatherActivity.class));
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.register:
                Intent intent = new Intent(this, RegisterUser.class);
                startActivity(intent);
                break;

            case R.id.login:
                login();
                break;

        }
    }

    public void login() {
        String mail = email.getText().toString().trim();
        String pword = password.getText().toString().trim();

        if (mail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            email.setError("Email is not valid.");
            email.requestFocus();
            return;
        } else if (pword.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(mail, pword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Signed in.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, WeatherActivity.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to login.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }


}