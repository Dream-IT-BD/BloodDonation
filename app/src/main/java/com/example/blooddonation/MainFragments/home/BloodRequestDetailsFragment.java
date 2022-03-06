package com.example.blooddonation.MainFragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.blooddonation.databinding.FragmentBloodRequestDetailsViewBinding;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BloodRequestDetailsFragment extends Fragment {

    private static final String TAG = "Blood Request";
    Context mContext;
    String id;

    // ViewBinding
    FragmentBloodRequestDetailsViewBinding binding;

    @Override
    public void onAttach(@NonNull Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBloodRequestDetailsViewBinding.inflate(inflater,container,false);
        id = getArguments().getString("id");
        Log.d(TAG, "onCreate: fragment id pass ..."+id);

        fetchMadrasaDetails();

        binding.donateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interestedToDonate();
            }
        });

        return binding.getRoot();
    }

    private void fetchMadrasaDetails() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");

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

    private void interestedToDonate() {
        RequestQueue queue = Volley.newRequestQueue(mContext);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token","");

        String url ="https://blood.dreamitdevlopment.com/public/api/interested-donor?token="+token;

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


}