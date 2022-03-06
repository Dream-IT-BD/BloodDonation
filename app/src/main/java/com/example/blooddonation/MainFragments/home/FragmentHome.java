package com.example.blooddonation.MainFragments.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
    public NavigationView navigation;
    ExtendedFloatingActionButton btnFloatingButton;
    public DrawerLayout drawer;
    String token;
    LottieAnimationView animation;

    FragHomeBinding binding;
    private List<BloodRequestItem> bloodRequestItems;
    private RecyclerView.Adapter bloodRequestAdapter;


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

                try {
                    Log.d(TAG, "onResponse: Data..........."+response);

                    JSONArray array = new JSONArray(response);
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

}

// FloatingAction Button Java
        /*
                btnFloatingButton = view.findViewById(R.id.btnFloatingButton);

        btnFloatingButton.setVisibility(View.VISIBLE);

        btnFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragFindBloodDonor findBloodDonor = new fragFindBloodDonor();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragHomeXML, findBloodDonor, "testTestTest")
                        .addToBackStack(null)
                        .commit();

                btnFloatingButton.setVisibility(View.GONE);

//                activity.navigationListner();

//                navigation.setCheckedItem(R.id.findBloodDonorXML);
            }
        });
         */

// FloatingAction Button XML

        /*
        <com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
        android:id="@+id/btnFloatingButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:backgroundTint="@color/redBG"
        android:text="Blood Request"
        android:textStyle="bold"
        app:icon="@drawable/ic_blood"
        app:layout_constraintVertical_bias="1"
        app:layout_constraintHorizontal_bias="1"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
         */