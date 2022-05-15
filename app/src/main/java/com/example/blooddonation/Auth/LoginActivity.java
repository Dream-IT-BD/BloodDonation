package com.example.blooddonation.Auth;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.blooddonation.HomeActivity;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.FragmentDashboard;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.LoginActivityBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
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
    String token, userSelectedDateOfLastDonation;

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

                    token = jsonObject.getString("access_token");

                    Log.d(TAG, "onResponse:    Token  : " + token);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
                    token = sharedPreferences.getString("token","");

                    Toast.makeText(LoginActivity.this, "Login Done", Toast.LENGTH_SHORT).show();

                    getUserPreviousBloodDonationData();
                    loadingDialog.hide();


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

    private void getUserPreviousBloodDonationData() {
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/personal-profile-view?token=" + token;
        Log.d(TAG, "getUserPreviousBloodDonationData: @@@@@@@@@@@@@@@@             token : " + token);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {

                    for (int i = 0; i< response.length(); i++){

                        JSONObject profileObject = response.getJSONObject(i);

                        String blood = profileObject.getString("blood_donation");
                        //Log.d(TAG, "onResponse: @@@@@@@@@@           Blood : " + blood);

                        if (blood.equals("null")){
                            // I'll show the datePickerPopup
                            datePickerPopup();
                        }else {
                            // I'll navigate to the next activity
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();

                            //Log.d(TAG, "onResponse: @@@@@@@@@@@           Record : " + blood);
                        }


                        Log.d(VolleyLog.TAG, "onResponse: @@@@@@@@@@           My Object : " + profileObject);
                        //Log.d(TAG, "onResponse: @@@@@@@@@@           Blood Donation : " + donationDataObject);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }

    private void datePickerPopup() {

        Calendar calendar = Calendar.getInstance();

        final int yearFrom = calendar.get(Calendar.YEAR);
        final int monthFrom = calendar.get(Calendar.MONTH);
        final int dayFrom = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LoginActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                //String dateForDateFrom = dayOfMonth + "/" + month + "/" + year;
                userSelectedDateOfLastDonation = year + "-" + month + "-" + dayOfMonth;

                sendLastDonationDataWithDate();

                Log.d(TAG, "onDateSet: @@@@@@@@@          Your Selected Date is : " + userSelectedDateOfLastDonation);
            }
        }, yearFrom,monthFrom,dayFrom);

        datePickerDialog.setTitle("আপনার স্ররবশেষ রক্ত দানের তারিখ । \n");
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, "SKIP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(LoginActivity.this, "Skip", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onCancel: @@@@@@@@@@@@@@                You Pressed Skip");
            }
        });
        datePickerDialog.setCanceledOnTouchOutside(false);
        datePickerDialog.show();
    }

    private void sendLastDonationDataWithDate() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://blood.dreamitdevlopment.com/public/api/donation/check?token=" + token;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String requestStatus = jsonObject.getString("status");

                    if (requestStatus.equals("success")){
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                String date = userSelectedDateOfLastDonation;
                Log.d(TAG, "getParams: ..........................." + date);
                Map<String, String> params = new HashMap<>();
                params.put("donation_date",date);
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