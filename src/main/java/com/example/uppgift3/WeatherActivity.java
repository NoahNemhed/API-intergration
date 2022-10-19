package com.example.uppgift3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText city, country;
    private Button submit, logout;
    private FirebaseAuth mAuth;
    private String key = "01271c384ff1ca9d1b8017a4c323fada";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mAuth = FirebaseAuth.getInstance();
        //Declaring variables.
        city = (EditText) findViewById(R.id.city);
        if (city == null) { Log.w("", "TextView is null"); }
        country = (EditText) findViewById(R.id.country);
        if (country == null) { Log.w("", "TextView is null"); }
        submit = (Button) findViewById(R.id.submit);
        if (submit == null) { Log.w("", "TextView is null"); }
        submit.setOnClickListener(this);
        logout = (Button) findViewById(R.id.logout);
        if (logout == null) { Log.w("", "TextView is null"); }
        logout.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submit:
                search();
                break;

            case R.id.logout:
                logout();
                break;

        }
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(this, MainActivity.class));
    }


    public void search(){
        String city1 = city.getText().toString().trim();
        String country1 = country.getText().toString().trim();

        if (city1.isEmpty()) {
            city.setError("City is required");
            city.requestFocus();
            return;
        }else if(country1.isEmpty()){
            country.setError("City is required");
            country.requestFocus();
            return;
        }else{
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city1.toLowerCase() + "," + country1.toLowerCase() + "&appid=" + key;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject object = response.getJSONObject("main");
                        String temp = object.getString("temp");
                        Intent intent = new Intent(getBaseContext(), ShowWeather.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Temperature", temp);
                        intent.putExtra("City", city1);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, error -> {
                Toast.makeText(WeatherActivity.this, "Invalid City or Country code.", Toast.LENGTH_SHORT).show();

            });
            queue.add(request);




        }
    }
}