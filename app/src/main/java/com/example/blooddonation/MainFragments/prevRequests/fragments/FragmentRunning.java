package com.example.blooddonation.MainFragments.prevRequests.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.home.BloodRequestItem;
import com.example.blooddonation.MainFragments.prevRequests.running.RunningRequestAdapter;
import com.example.blooddonation.MainFragments.prevRequests.running.RunningRequestItem;
import com.example.blooddonation.databinding.FragmentRunningBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentRunning extends Fragment {

    private static final String TAG = "FragmentRunning";
    Context mContext;
    FragmentRunningBinding binding;

    private List<RunningRequestItem> runningRequestItems;
    private RecyclerView.Adapter runningRequestAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    String token;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentRunningBinding.inflate(inflater, container, false);
        swipeRefreshLayout = binding.fragRunningBloodRequests;
        binding.shimmer.startShimmer();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRunningData();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getRunningData();
        runningRequestItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.runningBloodRequestRecycler.setLayoutManager(manager);
        runningRequestAdapter = new RunningRequestAdapter(runningRequestItems, mContext, this);
        binding.runningBloodRequestRecycler.setAdapter(runningRequestAdapter);

        return binding.getRoot();
    }

    private void getRunningData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/user-running?token="+token;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.fragRunningBloodRequests.setVisibility(View.VISIBLE);

                    runningRequestItems.clear();
                    JSONArray array = new JSONArray(response);

                    Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@@ " + response);
                    for (int i = 0; i <array.length(); i++){

                        JSONObject jsonObject = array.getJSONObject(i);

                        String date = jsonObject.getString("created_at");
                        String newDate = date.substring(0,10);

                        RunningRequestItem data = new RunningRequestItem(
                                jsonObject.getString("id"),
                                jsonObject.getString("patient_name"),
                                jsonObject.getString("hospital_name"),
                                jsonObject.getString("blood_group"),
                                newDate
                        );
                        runningRequestItems.add(data);
                    }
                    runningRequestAdapter.notifyDataSetChanged();

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


}