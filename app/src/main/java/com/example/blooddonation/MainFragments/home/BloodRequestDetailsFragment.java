package com.example.blooddonation.MainFragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.blooddonation.databinding.FragmentBloodRequestDetailsViewBinding;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BloodRequestDetailsFragment extends Fragment {

    private static final String TAG = "Blood Request";
    Context mContext;
    String id, logedin_user_ID, requestCreatorUserID;
    SharedPreferences sharedPreferences;
    String blood_need;
    int blood_managed = 0;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();

    // ViewBinding
    FragmentBloodRequestDetailsViewBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBloodRequestDetailsViewBinding.inflate(inflater,container,false);
        id = getArguments().getString("id");
        Log.d(TAG, "onCreate: fragment id pass ..."+id);

        sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        logedin_user_ID = sharedPreferences.getString("user_ID","");

        fetchRequestDetails();

        totalManagedDonorCounter();

        binding.donateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interestedToDonate();
            }
        });

        return binding.getRoot();
    }


    private void fetchRequestDetails() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/view/"+id+"?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d(TAG, "onResponse: "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    binding.patientName.setText(jsonObject.getString("patient_name"));
                    binding.hospitalName.setText(jsonObject.getString("hospital_name"));
                    binding.bloodGroup.setText(jsonObject.getString("blood_group"));
                    binding.gender.setText(jsonObject.getString("gender"));
                    binding.division.setText(jsonObject.getString("division"));
                    binding.tvDistrict.setText(jsonObject.getString("district"));
                    binding.tvUpazila.setText(jsonObject.getString("upazila"));

                    requestCreatorUserID = jsonObject.getString("user_id");
                    Log.d(TAG, "onResponse: @@@@@@@@@@@          Active User ID : " + logedin_user_ID);
                    Log.d(TAG, "onResponse: @@@@@@@@@           Request Creator ID : " + requestCreatorUserID);

                    if (logedin_user_ID.equals(requestCreatorUserID)){
                        binding.donateNow.setVisibility(View.GONE);
                        Log.d(TAG, "onResponse: @@@@@@@@@@@          Active User ID : " + logedin_user_ID);
                        Log.d(TAG, "onResponse: @@@@@@@@@@@          Request Creator ID : " + requestCreatorUserID);
                    }

                    blood_need = jsonObject.getString("blood_amount");
                    binding.tvBloodNeed.setText("রক্ত প্রয়োজনঃ " + blood_need + " ব্যাগ");

                    String dateFromAPI = jsonObject.getString("updated_at");

                    String date = dateFromAPI.substring(0,10);

                    binding.tvDate.setText(date);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        }) ;
        queue.add(stringRequest);

    }


    private void totalManagedDonorCounter() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/managed-donor/" + id;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@              Managed Response : " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("managed_donor");

                            Log.d(TAG, "onResponse: @@@@@@@@@@         Managed Data : " + jsonArray);

                            blood_managed = jsonArray.length();

                            binding.tvBloodManaged.setText("রক্ত পাওয়া গেছেঃ " + blood_managed + " ব্যাগ");

                            Log.d(TAG, "onResponse: @@@@@@@@@@@@@@               Managed Length : " + blood_managed);

                            bloodProgress();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@                  Error : " + error);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    private void bloodProgress() {
        binding.bloodProgress.getProgressDrawable().setColorFilter(
                Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

        //Toast.makeText(mContext, "Hello " + blood_managed, Toast.LENGTH_SHORT).show();

        binding.bloodProgress.setProgress(blood_managed);
//        binding.bloodProgress.setMax(Integer.parseInt(blood_need));
        binding.bloodProgress.setMax(5);
        binding.bloodProgress.setScaleY(3f);
    }

    private void interestedToDonate() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

        String url ="https://blood.dreamitdevlopment.com/public/api/interested-donor?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d(TAG, "onResponse: @@@@@@@@@@@      MarkAsManaged response : " + response);
                    JSONObject jsonObject = new JSONObject(response);

                    String status;

                    status = jsonObject.getString("status");

                    if (status.equals("success")){
                        Toast.makeText(mContext, "Status Changed", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext, "Something went wrong. Try Again Letter", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams(){


                Log.d(TAG, "getParams: ..........................." + id);

                Map<String, String> params = new HashMap<>();
                params.put("id",id);
                return params;
            }
        };
        queue.add(stringRequest);
    }


}