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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.prevRequests.completed.CompletedRequestItem;
import com.example.blooddonation.MainFragments.prevRequests.managed.ManagedRequestAdapter;
import com.example.blooddonation.MainFragments.prevRequests.managed.ManagedRequestItem;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentManagedBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentManaged extends Fragment {

    private static final String TAG = "FragmentManaged";
    FragmentManagedBinding binding;
    Context mContext;
    String token;
    LoadingDialog loadingDialog;
    private List<ManagedRequestItem> managedRequestItems;
    private RecyclerView.Adapter managedRequestAdapter;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentManagedBinding.inflate(inflater, container, false);
        loadingDialog = new LoadingDialog(mContext);
        loadingDialog.show();

        swipeRefreshLayout = binding.fragManagedBloodRequest;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getManagedData();

                loadingDialog.show();

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        getManagedData();

        managedRequestItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.managedBloodRequestRecycler.setLayoutManager(manager);
        managedRequestAdapter = new ManagedRequestAdapter(managedRequestItems, mContext, this);
        binding.managedBloodRequestRecycler.setAdapter(managedRequestAdapter);

        return binding.getRoot();
    }

    private void getManagedData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/user-managed?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    loadingDialog.hide();

                    Log.d(TAG, "onResponse: Data..........."+response);

                    JSONArray array = new JSONArray(response);
                    managedRequestItems.clear();
                    for (int i=0;i<array.length();i++){

                        JSONObject jsonObject = array.getJSONObject(i);
                        String date = jsonObject.getString("created_at");
                        String newDate = date.substring(0,10);

                        String requestStatus = jsonObject.getString("status");

                        ManagedRequestItem data = new ManagedRequestItem(
                                jsonObject.getString("id"),
                                jsonObject.getString("patient_name"),
                                jsonObject.getString("hospital_name"),
                                jsonObject.getString("blood_group"),
                                newDate
                        );
                        managedRequestItems.add(data);
                    }
                    managedRequestAdapter.notifyDataSetChanged();

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