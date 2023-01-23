package com.example.blooddonation.MainFragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.DashboardElementContainerActivity;
import com.example.blooddonation.MainFragments.prevRequests.fragmentRequests;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentFindBloodDonorBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentFindBloodDonor extends Fragment {

    private static final String TAG = "fragFindBloodDonor";
    FragmentFindBloodDonorBinding binding;
    Context mContext;
    private ArrayList<String> divisions ,districts ,upazilas;
    String token;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

//    Don't delete ! This is for empty field validator
    String requestingBloodGroup;
    String patientName, hospitalName, hospitalLocation;
    Editable contactNumber;
    int numberValidator;


    public FragmentFindBloodDonor() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    DashboardElementContainerActivity activity = new DashboardElementContainerActivity();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFindBloodDonorBinding.inflate(inflater, container, false);
        //View view = inflater.inflate(R.layout.fragment_find_blood_donor, container, false);

        divisions = new ArrayList<String>();
        districts = new ArrayList<String>();
        upazilas = new ArrayList<String>();

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        getDivisionData();

        spinnerConfig();

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

        binding.btnFindBloodDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@@                         Submit Button Clicked");

                //emptyValidation();
                bloodRequest();

            }
        });

        return binding.getRoot();
    }

    private void bloodRequest() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/create?token=" + token;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@" +response);

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    Log.d(TAG, "onResponse:    Status           :     " + status);

                    if (status.equals("success")){
                        //Toast.makeText(mContext, "Request Added", Toast.LENGTH_SHORT).show();
//                        popupWindow();

                        // Not Tested yet because domain is expired :(
                        Snackbar bar = Snackbar.make(getView().findViewById(R.id.findBloodDonorXML), " ", Snackbar.LENGTH_LONG);
                        bar.setText("Request Added");
                        bar.show();

//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.dashboard_container, new fragmentRequests())
//                                .commit();
                    }else {
                        Toast.makeText(mContext, "Try Again Letter", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    @@@@@@@@@@@@@      Volly Error : " +error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){

                String patient_name, patient_diagnosis, blood_group, blood_amount, gender, hospital_name, hospital_address, division, district, upazila, note;

                patient_name = binding.etPatientName.getText().toString().trim();
                patient_diagnosis = binding.etPatientDiagnosis.getText().toString().trim();
                blood_group = binding.spinnerBloodGroup.getText().toString();
                blood_amount = binding.etBloodAmount.getText().toString().trim();
                gender = binding.spinnerGender.getText().toString();
                hospital_name = binding.etHospitalName.getText().toString().trim();
                hospital_address = binding.etHospitalAddress.getText().toString().trim();
                division = binding.spinnerDivision.getText().toString();
                district = binding.spinnerDistrict.getText().toString();
                upazila = binding.spinnerUpazila.getText().toString();
                note = binding.etAdditionalNote.getText().toString();


                Log.d(TAG, "getParams: ............." + patient_name + "......" + patient_diagnosis +  "......" + blood_group + "....." + blood_amount + "........." + gender + "......" +
                        hospital_name + "....." + hospital_address + "......." +  division + "....." + district + "....." + upazila + "......." + note);

                Map<String, String> params = new HashMap<>();
                params.put("patient_name",patient_name);
                params.put("patient_diagnosis",patient_diagnosis);
                params.put("blood_group",blood_group);
                params.put("blood_amount", blood_amount);
                params.put("gender",gender);
                params.put("hospital_name",hospital_name);
                params.put("hospital_address", hospital_address);
                params.put("division",division);
                params.put("district",district);
                params.put("upazila",upazila);
                params.put("note", note);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getDivisionData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
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
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, divisions);
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

        RequestQueue queue = Volley.newRequestQueue(mContext);
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
                            ArrayAdapter<String> divisionAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, districts);
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

        RequestQueue queue = Volley.newRequestQueue(mContext);
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
                            ArrayAdapter<String> upazilaAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, upazilas);
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
        // Blood Group Spinner
        ArrayAdapter<CharSequence> bloodGroupAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.blood_group,
                android.R.layout.simple_spinner_item);
        bloodGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerBloodGroup.setAdapter(bloodGroupAdapter);

        // Gender Selector Spinner
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender,
                android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerGender.setAdapter(genderAdapter);

    }

    private void popupWindow(){

        Button home;

        dialogBuilder = new AlertDialog.Builder(mContext);
        final View windowView = getLayoutInflater().inflate(R.layout.request_added_popup, null);

        home = windowView.findViewById(R.id.goToHome);

        dialogBuilder.setView(windowView);
        dialog = dialogBuilder.create();
        dialog.show();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.hide();

                Fragment fragment = new FragmentDashboard();

                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                fragmentTransaction.addToBackStack(null);



            }
        });
    }

// Empty Validator
//    private void emptyValidation() {
//
//        Log.d(TAG, "emptyValidation: @@@@@@@@@@@@@@@@@                   Empty Validating Starting here  : ");
//
//        requestingBloodGroup = spinnerBloodGroup.getText().toString();
//
//        patientName = etPatientFullName.getText().toString();
//        hospitalName = etHospitalName.getText().toString();
//        hospitalLocation = etHospitalLocation.getText().toString();
//        contactNumber = etContactNumber.getText();
//
//        numberValidator = etContactNumber.getText().length();
//
//        if (patientName.isEmpty()){
//            etPatientFullName.requestFocus();
//            etPatientFullName.setError("Please Enter Patient name");
//        }else if (hospitalName.isEmpty()){
//            etHospitalName.requestFocus();
//            etHospitalName.setError("Enter Hospital Name");
//        }else if (hospitalLocation.isEmpty()){
//            etHospitalLocation.requestFocus();
//            etHospitalLocation.setError("Enter Hospital Location");
//        }else if (contactNumber == null){
//            etContactNumber.requestFocus();
//            etContactNumber.setError("Enter Contact Number");
//        }else if (numberValidator != 11){
//            etContactNumber.requestFocus();
//            etContactNumber.setError("Enter a valid number");
//        }else if (requestingBloodGroup.isEmpty()){
//            Toast.makeText(mContext, "Please Spacify the blood group.", Toast.LENGTH_SHORT).show();
//        }else{
////            Toast.makeText(mContext, "Request Successful", Toast.LENGTH_SHORT).show();
//
//            Snackbar bar = Snackbar.make(getView().findViewById(R.id.findBloodDonorXML), " ", Snackbar.LENGTH_LONG);
//            bar.setText("Request Successful");
//            bar.show();
//        }
//    }



    // Submit Button Config

// onClick Hint Changer
    /*
    //        etContactNumber.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                //         android:hint="Contact Number"
//
//                if (view.hasFocus()){
//                    etContactNumber.setHint("Without +88");
//                }else if (!view.hasFocus()){
//                    etContactNumber.setHint("");
//                }
//            }
//        });
     */
}