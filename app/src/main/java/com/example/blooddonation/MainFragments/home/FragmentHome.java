package com.example.blooddonation.MainFragments.home;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.UserProfile.ActivityEditProfile;
import com.example.blooddonation.databinding.FragHomeBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private static final String TAG = "fragHome";
    Context mContext;
    String token;
    private ArrayList<String> upazilas;
    String userSelectedUpazila;

    FragHomeBinding binding;
    private List<BloodRequestItem> bloodRequestItems;
    private RecyclerView.Adapter bloodRequestAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragHomeBinding.inflate(inflater,container,false);

        swipeRefreshLayout = binding.swipeRefreshLayout;
        upazilas = new ArrayList<String>();

        getUpazilaData();

        binding.shimmer.startShimmer();



        binding.upazilaSelectorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userSelectedUpazila = upazilas.get(position);
                Log.d(TAG, "onItemSelected: @@@@@@@@@@@              User Selected : " + userSelectedUpazila);

                fetchBloodRequestData();
                bloodRequestItems.clear();
                binding.tvLoadingDataFor.setText("Loading blood requests for : " + userSelectedUpazila);
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: @@@@@@@@@@@               Nothing Selected !");
            }
        });


        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fetchBloodRequestData();

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        fetchBloodRequestData();
        bloodRequestItems = new ArrayList<>();

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        binding.bloodRequestRecycler.setLayoutManager(manager);
        bloodRequestAdapter = new BloodRequestAdapter(bloodRequestItems,mContext,this);
        binding.bloodRequestRecycler.setAdapter(bloodRequestAdapter);

        return binding.getRoot();
    }

    private void fetchBloodRequestData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/all?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@@@@@@             All Blood Requests : " + response);

                try {

                    binding.shimmer.stopShimmer();
                    binding.shimmer.setVisibility(View.GONE);
                    binding.swipeRefreshLayout.setVisibility(View.VISIBLE);

                    Log.d(TAG, "onResponse: Data..........."+response);

                    JSONArray array = new JSONArray(response);

                    if (array.length() == 0){
                        Log.d(TAG, "onResponse: @@@@@@@@@@@@@               Array Response : No Request Found !");
                        Toast.makeText(mContext, "No Request Found ! Please check back latter.", Toast.LENGTH_SHORT).show();
                    }

                    bloodRequestItems.clear();

                    for (int i=0;i<array.length();i++){

                        JSONObject blooRequestData = array.getJSONObject(i);
                        String date = blooRequestData.getString("created_at");
                        String newDate = date.substring(0,10);
                        BloodRequestItem data = new BloodRequestItem(
                                blooRequestData.getString("id"),
                                blooRequestData.getString("patient_name"),
                                blooRequestData.getString("hospital_name"),
                                blooRequestData.getString("blood_group"),
                                newDate
                        );
                        bloodRequestItems.add(data);
                    }
                    bloodRequestAdapter.notifyDataSetChanged();

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

    private void getUpazilaData(){

        upazilas.clear();
        //spinnerUpazila.setText(null);

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/upazila?district_id=বগুড়া";

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
                            //spinnerUpazila.setAdapter(upazilaAdapter);
                            binding.upazilaSelectorSpinner.setAdapter(upazilaAdapter);

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



}