package com.example.blooddonation.MainFragments.prevRequests.managed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.MainFragments.prevRequests.fragments.FragmentManaged;
import com.example.blooddonation.databinding.ManagedRequestRecyclerItemBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagedRequestAdapter extends RecyclerView.Adapter<ManagedRequestAdapter.ViewHolder> {
    private static final String TAG = "ManagedRequestAdapter";
    private final List<ManagedRequestItem> managedRequestItems;
    private final Context context;
    private final FragmentManaged parent;

    public ManagedRequestAdapter(List<ManagedRequestItem> managedRequestItems, Context context, FragmentManaged parent) {
        this.managedRequestItems = managedRequestItems;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ManagedRequestRecyclerItemBinding binding = ManagedRequestRecyclerItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    String blood_request_id;

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ManagedRequestItem data = managedRequestItems.get(position);
        holder.binding.patientName.setText(data.getPatient_name());
        holder.binding.hospitalName.setText(data.getHospital_name());
        holder.binding.bloodGroup.setText(data.getBlood_group());
        holder.binding.tvDate.setText(data.getDate());

//holder.binding.getRoot();
        holder.binding.bloodRequestCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                blood_request_id = data.getId();

                managedToCompletedStatusChanger();

/*
                Fragment fragment = new BloodRequestDetailsFragment();
                Bundle arguments = new Bundle();
                arguments.putString("id",data.getId());
                fragment.setArguments(arguments);

                FragmentTransaction fragmentTransaction = parent.requireActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment).commit();
                fragmentTransaction.addToBackStack(null);
 */
            }
        });
    }

    private void managedToCompletedStatusChanger() {


        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://blood.dreamitdevlopment.com/public/api/donation/complete/"+ blood_request_id;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d(TAG, "onResponse: @@@@@@@@@@@      MarkAsManaged response : " + response);

                    JSONObject jsonObject = new JSONObject(response);

                    String status;

                    status = jsonObject.getString("status");

                    if (status.equals("success")){
                        Toast.makeText(context, "Status Changed", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context, "Something went wrong. Try Again Letter", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse:    " + error);
                Toast.makeText(context, error.toString() , Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){


                Log.d(TAG, "getParams: ..........................." + blood_request_id);

                Map<String, String> params = new HashMap<>();
                params.put("blood_request_id", blood_request_id);
                return params;
            }
        };
        queue.add(stringRequest);
    }


    @Override
    public int getItemCount() {
        return managedRequestItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ManagedRequestRecyclerItemBinding binding;
        public ViewHolder(@NonNull ManagedRequestRecyclerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }

}
