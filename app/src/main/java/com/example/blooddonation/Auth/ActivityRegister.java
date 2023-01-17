package com.example.blooddonation.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.example.blooddonation.databinding.ActivityRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActivityRegister extends AppCompatActivity {

    private ArrayList<String> divisions ,districts ,upazilas;
    ActivityRegisterBinding binding;
    public SharedPreferences sharedPreferences;


    private static final String TAG = "RegisterActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        sharedPreferences = getSharedPreferences("authToken", Context.MODE_PRIVATE);

        getDivisionData();

        spinnerConfig();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                emptyError();

                registerRequest();

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Name : " + binding.etUserName.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Phone : " + binding.etPhoneNumber.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Address : " + binding.etAddress.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Age : " + binding.etAgeForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Weight : " + binding.etWeightForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Password : " + binding.etPasswordOneForRegister.getText());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Password : " + binding.etPasswordTwoForRegister.getText());

                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Division : " + binding.spinnerDivision.getText().toString());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    District : " + binding.spinnerDistrict.getText().toString());
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@                    Upazila : " + binding.spinnerUpazila.getText().toString());

            }
        });

        divisions = new ArrayList<String>();
        districts = new ArrayList<String>();
        upazilas = new ArrayList<String>();

        binding.spinnerDivision.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDistrictData();
            }
        });

        binding.spinnerDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                name = binding.etUserName.getText().toString();
                number = binding.etPhoneNumber.getText().toString();
                blood_group = binding.spinnerBloodGroup.getText().toString();
                gender = binding.spinnerGender.getText().toString();
                age = binding.etAgeForRegister.getText().toString();
                weight = binding.etWeightForRegister.getText().toString();
                division = binding.spinnerDivision.getText().toString();
                district = binding.spinnerDistrict.getText().toString();
                upazila = binding.spinnerUpazila.getText().toString();
                password = binding.etPasswordOneForRegister.getText().toString();
                password_confirmation = binding.etPasswordTwoForRegister.getText().toString();

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
        RequestQueue queue = Volley.newRequestQueue(ActivityRegister.this);
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

                    Intent intent = new Intent(ActivityRegister.this, HomeActivity.class);
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
                String phone = binding.etPhoneNumber.getText().toString();
                String pass = binding.etPasswordTwoForRegister.getText().toString();

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
        name = binding.etUserName.getText().toString();
        number = binding.etPhoneNumber.getText().toString();
        blood_group = binding.spinnerBloodGroup.getText().toString();
        gender = binding.spinnerGender.getText().toString();
        age = binding.etAgeForRegister.getText().toString();
        weight = binding.etWeightForRegister.getText().toString();
        division = binding.spinnerDivision.getText().toString();
        district = binding.spinnerDistrict.getText().toString();
        upazila = binding.spinnerUpazila.getText().toString();
        password = binding.etPasswordOneForRegister.getText().toString();
        password_confirmation = binding.etPasswordTwoForRegister.getText().toString();


        /*

         */

        if (name.isEmpty()){
            binding.etUserName.setError("Type your name");
            binding.etUserName.requestFocus();
        }else if (blood_group.isEmpty()){
            binding.spinnerBloodGroup.setError("Select your blood group");
            binding.spinnerBloodGroup.requestFocus();
        }else if (number.isEmpty()){
            binding.etPhoneNumber.setError("Type Phone Number");
            binding.etPhoneNumber.requestFocus();
        }else if (age.isEmpty()){
            binding.etAgeForRegister.setError("Your age ?");
            binding.etAgeForRegister.requestFocus();
        }else if (weight.isEmpty()){
            binding.etWeightForRegister.setError("Your weight ?");
            binding.etWeightForRegister.requestFocus();
        }else if (division.isEmpty()){
            binding.spinnerDivision.setError("This can't be empty");
            binding.spinnerDivision.requestFocus();
        }else if (district.isEmpty()){
            binding.spinnerDistrict.setError("This can't be empty");
            binding.spinnerDistrict.requestFocus();
        }else if (upazila.isEmpty()){
            binding.spinnerUpazila.setError("This can't be empty");
            binding.spinnerUpazila.requestFocus();
        }else if (password.isEmpty()){
            binding.etPasswordOneForRegister.setError("Please type a password");
            binding.etPasswordOneForRegister.requestFocus();
        }else if (password_confirmation.isEmpty()){
            binding.etPasswordTwoForRegister.setError("Please type previous password");
            binding.etPasswordTwoForRegister.requestFocus();
        }else if (!password.equals(password_confirmation)){
            binding.etPasswordTwoForRegister.setError("Don't match");
            binding.etPasswordTwoForRegister.requestFocus();
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
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(ActivityRegister.this, android.R.layout.simple_spinner_dropdown_item, divisions);
                            binding.spinnerDivision.setAdapter(divisionAdapter);

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
        binding.spinnerDistrict.setText(null);

        RequestQueue queue = Volley.newRequestQueue(ActivityRegister.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/district?district_id=" + binding.spinnerDivision.getText().toString();

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
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(ActivityRegister.this, android.R.layout.simple_spinner_dropdown_item, districts);
                            binding.spinnerDistrict.setAdapter(divisionAdapter);

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
        binding.spinnerUpazila.setText(null);

        RequestQueue queue = Volley.newRequestQueue(ActivityRegister.this);
        String url = "https://blood.dreamitdevlopment.com/public/api/upazila?district_id=" + binding.spinnerDistrict.getText().toString();

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
                            ArrayAdapter<String> upazilaAdapter = new ArrayAdapter<>(ActivityRegister.this, android.R.layout.simple_spinner_dropdown_item, upazilas);
                            binding.spinnerUpazila.setAdapter(upazilaAdapter);

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
        binding.spinnerGender.setAdapter(genderAdapter);

        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(this,
                R.array.blood_group,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        // Division Selection Spinner
//        ArrayAdapter<CharSequence> divisionAdapter = ArrayAdapter.createFromResource(this,
//                R.array.division,
//                android.R.layout.simple_spinner_item);
//        divisionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerDivision.setAdapter(divisionAdapter);
    }

}