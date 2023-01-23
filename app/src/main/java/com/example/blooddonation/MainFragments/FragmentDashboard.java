package com.example.blooddonation.MainFragments;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.DashboardElementContainerActivity;
import com.example.blooddonation.databinding.FragmentDashboardBinding;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentDashboard extends Fragment {

    FragmentDashboardBinding binding;
    Context mContext;

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
        // Inflate the layout for this fragment
        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        getDashboardTopItem();

        binding.viewBloodRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashboardElementContainerActivity.class);
                intent.putExtra("frag", "viewBloodRequest");
                startActivity(intent);
            }
        });

        binding.addBloodRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DashboardElementContainerActivity.class);
                intent.putExtra("frag", "addBloodRequest");
                startActivity(intent);
            }
        });
        binding.viewPreviousRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DashboardElementContainerActivity.class);
                intent.putExtra("frag", "previousBloodRequest");
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private void getDashboardTopItem() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/dashboard-counter";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: @@@@@@@@@@@@@             Full Response : " + response);

                try {

                    binding.tvTotalUserOnApp.setText("মোট ইউজারঃ " +  response.getString("totalUser"));
                    binding.tvTotalBloodDonateOnApp.setText("মোট রক্ত দানঃ " + response.getString("totalDonation"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }
}