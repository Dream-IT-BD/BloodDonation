package com.example.blooddonation.MainFragments.prevRequests.details_managed;

import static android.content.ContentValues.TAG;

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
import com.example.blooddonation.MainFragments.prevRequests.details_interested.InterestedDonorAdapter;
import com.example.blooddonation.MainFragments.prevRequests.details_interested.InterestedDonorItem;
import com.example.blooddonation.MainFragments.prevRequests.importantData;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentManagedBinding;
import com.example.blooddonation.databinding.FragmentManagedDonorBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentManagedDonor extends Fragment {

    Context mContext;
    FragmentManagedDonorBinding binding;
    String bloodRequestID;

    private List<ManagedDonorItem> managedDonorItems;
    private RecyclerView.Adapter managedDonorAdapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManagedDonorBinding.inflate(inflater, container, false);
        //return inflater.inflate(R.layout.fragment_managed_donor, container, false);

        // Managed Donor Data
        managedDonorItems = new ArrayList<>();

        bloodRequestID = importantData.BLOOD_REQUEST_ID;;
        Log.d(TAG, "onCreateView: @@@@@@@@@@@@             Managed Hard Coded Blood Req ID : " + bloodRequestID);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.managedPeopleRecyclerView.setLayoutManager(manager);
        managedDonorAdapter = new ManagedDonorAdapter(managedDonorItems, mContext, this);
        binding.managedPeopleRecyclerView.setAdapter(managedDonorAdapter);

        getManagedDonorData();

        return binding.getRoot();
    }

    private void getManagedDonorData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/managed-donor/" + bloodRequestID;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    managedDonorItems.clear();
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("managed_donor");

                    for (int i = 0; i < jsonArray.length(); i++){


                        JSONObject object = jsonArray.getJSONObject(i);

//                        userID = object.getString("id");

                        JSONObject object1 = object.getJSONObject("user");

//                        InterestedDonorItem data = new InterestedDonorItem(
//                                object1.getString("name"),
//                                object.getString("blood_request_id"),
//                                object.getString("user_id")); // 3

                        ManagedDonorItem item = new ManagedDonorItem(
                                object1.getString("name"),
                                object1.getString("number"));

                        managedDonorItems.add(item);

                        //Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@@@@             Donor Data" + interestedDonorItems);
                    }

                    //Toast.makeText(mContext, "Interested : " + interestedDonorItems.toString(), Toast.LENGTH_SHORT).show();

                    managedDonorAdapter.notifyDataSetChanged();
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