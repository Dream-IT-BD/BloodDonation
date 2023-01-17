package com.example.blooddonation.MainFragments.prevRequests.details_interested.userProfile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.databinding.FragmentProfilePublicViewBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FragmentProfilePublicView extends Fragment {

    private static final String TAG = "FragProfilePublicView";
    Context mContext;
    FragmentProfilePublicViewBinding binding;
    String id, userContactNumber, blood_request_id, user_id;
    String token;

    public FragmentProfilePublicView() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfilePublicViewBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");


        user_id = getArguments().getString("User_ID");
        //blood_request_id = getArguments().getString("blood_request_ID");
        blood_request_id = "1";
        Log.d(TAG, "FargmentProfilePublicView: @@@@@@@@@@@@@              User ID : " + id);


        getInterestedDonorData();
        //interestedDonorData();

        binding.btnContactNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + userContactNumber));
                startActivity(intent);
            }
        });

        binding.btnDonorManaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                donorManaged();

                // Post ID
                // User ID
            }
        });

        return binding.getRoot();
    }

//    private void interestedDonorData() {
//
//        RequestQueue queue = Volley.newRequestQueue(mContext);
//
//        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");
//
//        String url = "https://blood.dreamitdevlopment.com/public/api/user/view/"+id;
//        Log.d(TAG, "interestedDonorData: @@@@@@@@@@@@@@         ID on URL : " + id);
//
//        //String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/interested-donor/"+id;
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                try {
//
//                    JSONObject jsonObject = new JSONObject(response);
//
//                    binding.tvUserNameForPublicProfile.setText(jsonObject.getString("name"));
//
//                    binding.division.setText(jsonObject.getString("division"));
//                    binding.district.setText(jsonObject.getString("district"));
//                    binding.upazila.setText(jsonObject.getString("upazila"));
//
//                    blood_request_id = jsonObject.getString("blood_request_id");
//                    user_id = jsonObject.getString("user_id");
//                    String test = jsonObject.getString("number");
//
//                    userContactNumber = test;
//
//                    //Toast.makeText(mContext, "Interested : " + interestedDonorItems.toString(), Toast.LENGTH_SHORT).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@@@@     Volly Error : " + error);
//            }
//        });
//
//        queue.add(stringRequest);
//
//    }

    private void getInterestedDonorData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        //String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/user-view/"+id+"?token="+token;

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/interested-donor/" + blood_request_id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("interested_donor");

                    for (int i = 0; i < jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);

                        JSONObject object1 = object.getJSONObject("user");

                        String userID = object1.getString("id");

                        if (userID.equals(user_id)){
                            binding.tvUserNameForPublicProfile.setText(object1.getString("name"));

                            binding.division.setText(object1.getString("division"));
                            binding.tvDistrict.setText(object1.getString("district"));
                            binding.tvUpazila.setText(object1.getString("upazila"));

                            blood_request_id = object.getString("blood_request_id");
                            user_id = object.getString("user_id");
                            String test = object1.getString("number");

                            userContactNumber = test;
                        }

                    }

                    //Toast.makeText(mContext, "Interested : " + interestedDonorItems.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@@@@     Volly Error : " + error);
            }
        });

        queue.add(stringRequest);
    }



    private void donorManaged() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/managed-donor?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d(TAG, "onResponse: @@@@@@@@@@@      MarkAsManaged response : " + response);
                    JSONObject jsonObject = new JSONObject(response);

                    String status;

                    status = jsonObject.getString("status");

                    if (status.equals("success")){
                        Toast.makeText(mContext, "Donor Managed", Toast.LENGTH_SHORT).show();

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


                Log.d(TAG, "getParams: ..........................." + "Blood Request ID : " + blood_request_id +
                "User ID : " + user_id);

                Map<String, String> params = new HashMap<>();
                params.put("blood_request_id",blood_request_id);
                params.put("donor_id",user_id);
                return params;
            }
        };
        queue.add(stringRequest);

    }


}