package com.example.blooddonation.MainFragments.prevRequests.fragments;

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
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.prevRequests.completed.CompletedRequestAdapter;
import com.example.blooddonation.MainFragments.prevRequests.completed.CompletedRequestItem;
import com.example.blooddonation.databinding.FargmentCompletedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FargmentCompleted extends Fragment {

    private static final String TAG = "FragmentCompleted";
    Context mContext;
    String token;
    private List<CompletedRequestItem> completedRequestItems;
    private RecyclerView.Adapter completedRequestAdapter;
    FargmentCompletedBinding binding;
    LoadingDialog loadingDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FargmentCompletedBinding.inflate(inflater, container, false);
        loadingDialog = new LoadingDialog(mContext);
        loadingDialog.show();

        getCompletedData();
        completedRequestItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.completedBloodRequestRecycler.setLayoutManager(manager);
        completedRequestAdapter = new CompletedRequestAdapter(completedRequestItems, mContext, this);
        binding.completedBloodRequestRecycler.setAdapter(completedRequestAdapter);

        return binding.getRoot();
    }

    private void getCompletedData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/all?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    loadingDialog.hide();

                    Log.d(TAG, "onResponse: Data..........."+response);

                    JSONArray array = new JSONArray(response);
                    completedRequestItems.clear();
                    for (int i=0;i<array.length();i++){
                        JSONObject blooRequestData = array.getJSONObject(i);
                        String date = blooRequestData.getString("created_at");
                        String newDate = date.substring(0,10);

                        String requestStatus = blooRequestData.getString("status");

                        CompletedRequestItem data = new CompletedRequestItem(
                                blooRequestData.getString("id"),
                                blooRequestData.getString("patient_name"),
                                blooRequestData.getString("hospital_name"),
                                blooRequestData.getString("blood_group"),
                                newDate
                        );

                        if (requestStatus.equals("completed")){
                            completedRequestItems.add(data);
                        }
                    }
                    completedRequestAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) ;
        queue.add(stringRequest);
    }
}