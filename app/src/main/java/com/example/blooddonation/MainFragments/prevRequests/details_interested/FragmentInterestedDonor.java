package com.example.blooddonation.MainFragments.prevRequests.details_interested;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.databinding.FragmentInterestedDonorBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentInterestedDonor extends Fragment {

    private static final String TAG = "FragmentInterestedDonor";
    Context mContext;
    FragmentInterestedDonorBinding binding;
    String bloodRequestID;
    private List<InterestedDonorItem> interestedDonorItems;
    private RecyclerView.Adapter interestedPeopleAdapter;

    String userID;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInterestedDonorBinding.inflate(inflater, container,false);

        //Get ID From 'StatusDetailsFragment'
//        blood_Reuqest_ID = getArguments().getString("blood_Reuqest_ID");
//        Log.d(TAG, "onCreate:@@@@@@@@@@@@@@@@@@@@  FragmentInterestedDonor Get ID : "+blood_Reuqest_ID);

//        bloodRequestID = getArguments().getString("bloodRequestID");
        bloodRequestID = "1";
        Log.d(TAG, "onCreateView: @@@@@@@@@@@@             Interested Hard Coded Blood Req ID : " + bloodRequestID);

        // Interested Donor Data
        interestedDonorItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.interestedPeopleRecyclerView.setLayoutManager(manager);
        interestedPeopleAdapter = new InterestedDonorAdapter(interestedDonorItems, mContext, this);
        binding.interestedPeopleRecyclerView.setAdapter(interestedPeopleAdapter);


        getInterestedDonorData();
        return  binding.getRoot();
    }

    private void getInterestedDonorData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/interested-donor/"+ bloodRequestID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    interestedDonorItems.clear();
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("interested_donor");

                    for (int i = 0; i < jsonArray.length(); i++){


                        JSONObject object = jsonArray.getJSONObject(i);

                        userID = object.getString("id");

                        JSONObject object1 = object.getJSONObject("user");

                        InterestedDonorItem data = new InterestedDonorItem(
                                object1.getString("name"),
                                object.getString("blood_request_id"),
                                object.getString("user_id")); // 3

                        interestedDonorItems.add(data);

                        //Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@@@@             Donor Data" + interestedDonorItems);
                    }

                    //Toast.makeText(mContext, "Interested : " + interestedDonorItems.toString(), Toast.LENGTH_SHORT).show();

                    interestedPeopleAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@@@@     Volly Error : " + error);
            }
        });

        queue.add(stringRequest);
    }
}