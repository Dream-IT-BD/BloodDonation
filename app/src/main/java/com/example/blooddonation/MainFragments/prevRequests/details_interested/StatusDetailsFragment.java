package com.example.blooddonation.MainFragments.prevRequests.details_interested;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.MainFragments.prevRequests.InterestedAndManagedAdapter;
import com.example.blooddonation.MainFragments.prevRequests.importantData;
import com.example.blooddonation.R;
import com.example.blooddonation.databinding.FragmentStatusDetailsBinding;
import com.google.android.material.tabs.TabLayout;

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
    int blood_managed;
    String token,blood_need;
    FragmentStatusDetailsBinding binding;
    LoadingDialog loadingDialog;
    private List<InterestedDonorItem> interestedDonorItems;
    private RecyclerView.Adapter interestedPeopleAdapter;
    InterestedAndManagedAdapter adapter;

//    For Confirmation PopUp
    TextView tvBloodNeed, tvBloodManaged;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    SwipeButton swipe_btn;


    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentStatusDetailsBinding.inflate(inflater, container, false);
        //View view = inflater.inflate(R.layout.fragment_status_details, container, false);
        loadingDialog = new LoadingDialog(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        interestedDonorItems = new ArrayList<>();
        //Get ID From 'RunningRequestAdapter'
        id = importantData.BLOOD_REQUEST_ID;

        // Send ID to FragmentInterestedDonor
        Fragment fragmentInterestedDonor = new FragmentInterestedDonor();
        Bundle arguments = new Bundle();
        arguments.putString("idTwo",id);
        fragmentInterestedDonor.setArguments(arguments);

        // TabLayout Fragment Handle
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        adapter = new InterestedAndManagedAdapter(fragmentManager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Interested"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Managed"));

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

        // Interested People

//        LinearLayoutManager manager = new LinearLayoutManager(mContext);
//        binding.interestedPeopleRecyclerView.setLayoutManager(manager);
//        interestedPeopleAdapter = new InterestedDonorAdapter(interestedDonorItems, mContext, this);
//        binding.interestedPeopleRecyclerView.setAdapter(interestedPeopleAdapter);


        binding.btnMarkAsManaged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertPopup();
                //runningToManagedStatusChanger();
            }
        });

//        getInterestedDonorData();
        fetchRequestDetails();

        return binding.getRoot();
    }

    private void fetchRequestDetails() {
        //loadingDialog.show();
        RequestQueue queue = Volley.newRequestQueue(mContext);

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/view/"+id+"?token="+token;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //loadingDialog.hide();
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

/*    private void getInterestedDonorData() {

        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/interested-donor/"+id;

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
                                        object1.getString("name"),
                                        object.getString("blood_request_id"),
                                        object.getString("user_id"));

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
    }*/

    private void totalBloodNeedCounterForPopup() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/view/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    blood_need = jsonObject.getString("blood_amount");
                    tvBloodNeed.setText("রক্ত লাগবেঃ " + blood_need + " ব্যাগ");

                    Log.d(TAG, "onResponse: @@@@@@@@@@@@@@            Blood need on popup : " + tvBloodNeed);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: @@@@@@@@@@@@@@@@                Error : " + error);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void totalManagedDonorCounterForPopup() {

        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/blood-request/managed-donor/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@              Managed Response : " + response);
                loadingDialog.hide();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("managed_donor");

                    Log.d(TAG, "onResponse: @@@@@@@@@@         Managed Data : " + jsonArray);

                    blood_managed = jsonArray.length();

                    tvBloodManaged.setText("রক্ত পাওয়া গেছেঃ " + blood_managed + " ব্যাগ");

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
        queue.add(stringRequest);
    }

    private void alertPopup(){
        loadingDialog.show();

        totalManagedDonorCounterForPopup();
        totalBloodNeedCounterForPopup();

        dialogBuilder = new AlertDialog.Builder(mContext);
        final View windowView = getLayoutInflater().inflate(R.layout.running_to_managed_alert, null);

        swipe_btn = windowView.findViewById(R.id.swipe_btn);
        tvBloodNeed = windowView.findViewById(R.id.tvBloodNeed);
        tvBloodManaged = windowView.findViewById(R.id.tvBloodManaged);

        swipe_btn.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean active) {
                Toast.makeText(mContext, "Completed", Toast.LENGTH_SHORT).show();

                //runningToManagedStatusChanger();

                Toast.makeText(mContext, "POST API is Commented", Toast.LENGTH_LONG).show();
                dialog.hide();

//                Fragment fragment = new FragmentManaged();
//
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.RequestStatusDetails, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();

            }
        });

        dialogBuilder.setView(windowView);
        dialog = dialogBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}