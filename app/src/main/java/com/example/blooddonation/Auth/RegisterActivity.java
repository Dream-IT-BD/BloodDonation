package com.example.blooddonation.Auth;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.HomeActivity;
import com.example.blooddonation.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText etFullName, etPhoneNumber, etAddress, etAgeForRegister, etWeightForRegister, etPasswordOneForRegister, etPasswordTwoForRegister;
    AutoCompleteTextView spinnerGender, spinnerBloodGroup, spinnerDivision, spinnerDistrict, spinnerUpazila;
    Button btnRegister;
    String gender, bloodGroup, division;
    private ArrayList<String> divisions ,districts ,upazilas;
    String myMainAuthToken;
    public SharedPreferences sharedPreferences;
    TextInputLayout customerSpinnerLayout;

    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferences = getSharedPreferences("authToken", Context.MODE_PRIVATE);

        getDivisionData();

        // Spinner
        spinnerGender = findViewById(R.id.spinnerGender);
        spinnerBloodGroup = findViewById(R.id.spinnerBloodGroup);

        spinnerDivision = findViewById(R.id.spinnerDivision);
        spinnerDistrict = findViewById(R.id.spinnerDistrict);
        spinnerUpazila = findViewById(R.id.spinnerUpazila);
        spinnerConfig();

        // EditText
        etFullName = findViewById(R.id.etHospitalLocation);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAddress = findViewById(R.id.etAddress);
        etAgeForRegister = findViewById(R.id.etAgeForRegister);
        etWeightForRegister = findViewById(R.id.etWeightForRegister);
        etPasswordOneForRegister = findViewById(R.id.etPasswordOneForRegister);
        etPasswordTwoForRegister = findViewById(R.id.etPasswordTwoForRegister);

        customerSpinnerLayout = findViewById(R.id.customerSpinnerLayout);

        // Button
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                emptyError();

                registerRequest();

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Name : " + etFullName.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Phone : " + etPhoneNumber.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Address : " + etAddress.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Age : " + etAgeForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Weight : " + etWeightForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Password : " + etPasswordOneForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Password : " + etPasswordTwoForRegister.getText());

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Division : " + spinnerDivision.getText().toString());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    District : " + spinnerDistrict.getText().toString());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Upazila : " + spinnerUpazila.getText().toString());

            }
        });

        divisions = new ArrayList<String>();
        districts = new ArrayList<String>();
        upazilas = new ArrayList<String>();

        spinnerDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDistrictData();
            }
        });

        spinnerDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getUpazilaData();
            }
        });

    }


    private void registerRequest(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://blood.dreamitdevlopment.com/public/api/register";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@" +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    Log.d(TAG, "onResponse:    Status           :     " + status);
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();

                    if (status.equals("success")){
                        loginRequest();
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " +error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){

                String name, number, blood_group, gender, age, weight, division, district, upazila, password, password_confirmation;
                name = etFullName.getText().toString();
                number = etPhoneNumber.getText().toString();
                blood_group = spinnerBloodGroup.getText().toString();
                gender = spinnerGender.getText().toString();
                age = etAgeForRegister.getText().toString();
                weight = etWeightForRegister.getText().toString();
                division = spinnerDivision.getText().toString();
                district = spinnerDistrict.getText().toString();
                upazila = spinnerUpazila.getText().toString();
                password = etPasswordOneForRegister.getText().toString();
                password_confirmation = etPasswordTwoForRegister.getText().toString();

                Log.d(TAG, "getParams: ..........................." + name + "......" + number +  "......" + blood_group + "....." + gender + "......" + age + "....." +
                        weight + "....." + division + "....." + district + "....." + upazila + "......" + password + "......" + password_confirmation);

                Map<String, String> params = new HashMap<>();
                params.put("name",name);
                params.put("number",number);
                params.put("blood_group",blood_group);
                params.put("gender",gender);
                params.put("age",age);
                params.put("weight",weight);
                params.put("division",division);
                params.put("district",district);
                params.put("upazila",upazila);
                params.put("password",password);
                params.put("password_confirmation",password_confirmation);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void loginRequest() {
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/login";


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@" +response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String token = jsonObject.getString("access_token");

                    Log.d(TAG, "onResponse:    token           :     " + token);


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("authToken", Context.MODE_PRIVATE);
                    token = sharedPreferences.getString("token","");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " +error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                String phone = etPhoneNumber.getText().toString();
                String pass = etPasswordTwoForRegister.getText().toString();

                Log.d(TAG, "getParams: ..........................." + phone + "   ....  "  + pass);

                Map<String, String> params = new HashMap<>();
                params.put("number",phone);
                params.put("password",pass);
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void emptyError() {

        String name, number, blood_group, gender, age, weight, division, district, upazila, password, password_confirmation;
        name = etFullName.getText().toString();
        number = etPhoneNumber.getText().toString();
        blood_group = spinnerBloodGroup.getText().toString();
        gender = spinnerGender.getText().toString();
        age = etAgeForRegister.getText().toString();
        weight = etWeightForRegister.getText().toString();
        division = spinnerDivision.getText().toString();
        district = spinnerDistrict.getText().toString();
        upazila = spinnerUpazila.getText().toString();
        password = etPasswordOneForRegister.getText().toString();
        password_confirmation = etPasswordTwoForRegister.getText().toString();


        /*

         */

        if (name.isEmpty()){
            etFullName.setError("Type your name");
            etFullName.requestFocus();
        }else if (gender.isEmpty()){
            customerSpinnerLayout.setError("Select your gender");
//            customerSpinnerLayout.requestFocus();
        }else if (blood_group.isEmpty()){
            spinnerBloodGroup.setError("Select your blood group");
            spinnerBloodGroup.requestFocus();
        }else if (number.isEmpty()){
            etPhoneNumber.setError("Type Phone Number");
            etPhoneNumber.requestFocus();
        }else if (age.isEmpty()){
            etAgeForRegister.setError("Your age ?");
            etAgeForRegister.requestFocus();
        }else if (weight.isEmpty()){
            etWeightForRegister.setError("Your weight ?");
            etWeightForRegister.requestFocus();
        }else if (division.isEmpty()){
            spinnerDivision.setError("This can't be empty");
            spinnerDivision.requestFocus();

        }else if (district.isEmpty()){
            spinnerDistrict.setError("This can't be empty");
            spinnerDistrict.requestFocus();
        }else if (upazila.isEmpty()){
            spinnerUpazila.setError("This can't be empty");
            spinnerUpazila.requestFocus();
        }else if (password.isEmpty()){
            etPasswordOneForRegister.setError("Please type a password");
            etPasswordOneForRegister.requestFocus();
        }else if (password_confirmation.isEmpty()){
            etPasswordTwoForRegister.setError("Please type previous password");
            etPasswordTwoForRegister.requestFocus();
        }else if (!password.equals(password_confirmation)){
            etPasswordTwoForRegister.setError("Don't match");
            etPasswordTwoForRegister.requestFocus();
        }else {
            Toast.makeText(getApplicationContext(), "Hold Tight", Toast.LENGTH_SHORT).show();
        }


    }

    private void setSpinnerError(Spinner spinner, String error){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error"); // any name of the error will do
            selectedTextView.setTextColor(Color.RED); //text color in which you want your error message to be displayed
            selectedTextView.setText(error); // actual error message
            spinner.performClick(); // to open the spinner list if error is found.

        }
    }

    private void getDivisionData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://blood.dreamitdevlopment.com/public/api/division";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("division");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject division = jsonArray.getJSONObject(i);

                                divisions.add(division.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, divisions);
                            spinnerDivision.setAdapter(divisionAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

    private void getDistrictData() {
        districts.clear();
        spinnerDistrict.setText(null);

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/district?district_id=" + spinnerDivision.getText().toString();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("district");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject division = jsonArray.getJSONObject(i);

                                districts.add(division.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, districts);
                            spinnerDistrict.setAdapter(divisionAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

    private void getUpazilaData(){

        upazilas.clear();
        spinnerUpazila.setText(null);

        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/upazila?district_id=" + spinnerDistrict.getText().toString();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("upazila");

                            Log.d(TAG, "onResponse: @@@@@@@" + jsonArray);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject upazila = jsonArray.getJSONObject(i);

                                upazilas.add(upazila.getString("bn_name"));
                            }

                            /*for address select*/
                            ArrayAdapter<String> upazilaAdapter = new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_spinner_dropdown_item, upazilas);
                            spinnerUpazila.setAdapter(upazilaAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        });
        queue.add(stringRequest);
    }

    private void spinnerConfig() {
        // Gender Selector Spinner
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this,
                R.array.gender,
                android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(genderAdapter);

        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        // Division Selection Spinner
//        ArrayAdapter<CharSequence> divisionAdapter = ArrayAdapter.createFromResource(this,
//                R.array.division,
//                android.R.layout.simple_spinner_item);
//        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDivision.setAdapter(divisionAdapter);
    }

}