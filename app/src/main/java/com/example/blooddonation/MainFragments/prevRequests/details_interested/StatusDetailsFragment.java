package com.example.blooddonation.MainFragments.prevRequests.details_interested;

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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentStatusDetailsBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusDetailsFragment extends Fragment {

    private static final String TAG = "StatusDetailsFragment";
    Context mContext;
    String id;
    FragmentStatusDetailsBinding binding;
    String token;

    private List<InterestedDonorItem> interestedDonorItems;
    private RecyclerView.Adapter interestedPeopleAdapter;


    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusDetailsBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        interestedDonorItems = new ArrayList<>();
        id = getArguments().getString("id");
        Log.d(TAG, "onCreate: fragment id pass ..."+id);

        fetchRequestDetails();



        // Interested People Work

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.interestedPeopleRecyclerView.setLayoutManager(manager);
        interestedPeopleAdapter = new InterestedDonorAdapter(interestedDonorItems, mContext, this);
        binding.interestedPeopleRecyclerView.setAdapter(interestedPeopleAdapter);
        binding.btnMarkAsManaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runningToManagedStatusChanger();
            }
        });

        getInterestedDonorData();


        return binding.getRoot();
    }

    private void runningToManagedStatusChanger() {

        RequestQueue queue = Volley.newRequestQueue(mContext);

//        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/view/"+id+"?token="+token;
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/got-blood-donor";

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

    private void fetchRequestDetails() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

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
                    binding.district.setText(jsonObject.getString("district"));
                    binding.upazila.setText(jsonObject.getString("upazila"));

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

    private void getInterestedDonorData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/user-view/"+id+"?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            interestedDonorItems.clear();
                            JSONObject jsonObject = new JSONObject(response);

                            JSONArray jsonArray = jsonObject.getJSONArray("interested_donor");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);

                                JSONObject object1 = object.getJSONObject("user");

                                InterestedDonorItem data = new InterestedDonorItem(
                                        object1.getString("name")
                                );

                                interestedDonorItems.add(data);

                                Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@@@@             Donor Data" + interestedDonorItems);

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
                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@@@@     Volly Error : " + error);
            }
        });

        queue.add(stringRequest);
    }
}