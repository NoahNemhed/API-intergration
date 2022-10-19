package com.example.uppgift3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class ShowWeather extends AppCompatActivity implements View.OnClickListener {

    private TextView city,temperature;
    private Button back,logout;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);

        mAuth = FirebaseAuth.getInstance();
        //Declaring variables.
        city = (TextView) findViewById(R.id.city);
        if (city == null) { Log.w("", "TextView is null"); }
        temperature = (TextView) findViewById(R.id.temperature);
        if (temperature == null) { Log.w("", "TextView is null"); }
        logout = (Button) findViewById(R.id.logout);
        if (logout == null) { Log.w("", "TextView is null"); }
        logout.setOnClickListener(this);

        back = (Button) findViewById(R.id.back);
        if (back == null) { Log.w("", "TextView is null"); }
        back.setOnClickListener(this);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String temperature1 = getIntent().getStringExtra("Temperature");
            double celsius = Math.round(Double.parseDouble(temperature1) - 273.15);
            String city1 = getIntent().getStringExtra("City");
            temperature.setText(celsius + " C");
            city.setText(city1);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.logout:
                logout();
                break;

            case R.id.back:
                back();
                break;

        }
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(ShowWeather.this, MainActivity.class));
    }

    private void back() {
        startActivity(new Intent(ShowWeather.this, WeatherActivity.class));
    }
}