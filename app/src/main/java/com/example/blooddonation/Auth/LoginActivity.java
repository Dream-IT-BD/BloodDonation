package com.example.blooddonation.Auth;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.blooddonation.HomeActivity;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.FragmentDashboard;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.LoginActivityBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    String myMainAuthToken;
    public SharedPreferences sharedPreferences;
    LoginActivityBinding binding;
    TextInputEditText etPhone, etPassword;
    TextView tvRegisterNow;
    Button btnLogin;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        sharedPreferences = getSharedPreferences("authToken", Context.MODE_PRIVATE);

        loadingDialog = new LoadingDialog(LoginActivity.this);

        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        tvRegisterNow = findViewById(R.id.tvRegisterNow);
        btnLogin = findViewById(R.id.btnLogin);

        tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadingDialog.show();
//                checkInternet();

                loginRequest();
//                nextActivityValidator();

//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
//                    etPhone.setError("Enter a valid Email");
//                    return;
//                }
            }
        });
    }

//    private void checkInternet() {
//
//        if (CheckNetwork.isInternetAvailable(LoginActivity.this)){
//            Toast.makeText(this, "Internet Connection successful", Toast.LENGTH_SHORT).show();
//        }else{
//            getSupportFragmentManager().beginTransaction().add(R.id.loginActivity, new fragNoInternet()).commit();
//        }
//    }

    private void nextActivityValidator() {

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
        myMainAuthToken = sharedPreferences.getString("token","");

//        if (myMainAuthToken.isEmpty()){
//            Toast.makeText(MainActivity.this, "Login to access", Toast.LENGTH_SHORT).show();
//        }else{
//            startActivity(new Intent(MainActivity.this, SecondMainActivity.class));
//            finish();
//        }
    }

    private void loginRequest() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://blood.dreamitdevlopment.com/public/api/login";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@" +response);
                try {

                    JSONObject jsonObject = new JSONObject(response);

                    String token = jsonObject.getString("access_token");
                    String name = jsonObject.getString("name");
                    String number = jsonObject.getString("number");

                    Log.d(TAG, "onResponse:    Token  : " + token);
                    Log.d(TAG, "onResponse:    Name   :   " + name);
                    Log.d(TAG, "onResponse:    Number  :  " + number);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.putString("name", name);
                    editor.putString("number", number);
                    editor.apply();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
                    token = sharedPreferences.getString("token","");

                    Toast.makeText(LoginActivity.this, "Login Done", Toast.LENGTH_SHORT).show();

                    loadingDialog.hide();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadingDialog.hide();
                Log.d(TAG, "onErrorResponse:    " +error);
                Toast.makeText(LoginActivity.this, "Login Field", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                String phone = etPhone.getText().toString().trim();
                String pass = etPassword.getText().toString();

                Log.d(TAG, "getParams: ..........................." + phone + "   ....  "  + pass);

                Map<String, String> params = new HashMap<>();
                params.put("number",phone);
                params.put("password",pass);
                return params;
            }
        };
        queue.add(stringRequest);

    }


    @Override
    protected void onStart() {
        super.onStart();

        String token;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        if (!token.isEmpty()){
            Toast.makeText(LoginActivity.this, token, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }
    }
}