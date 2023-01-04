package com.example.blooddonation.MainFragments.UserProfile;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.blooddonation.LoadingDialog;
import com.example.blooddonation.databinding.FragmentProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragmentProfile extends Fragment {

    FragmentProfileBinding binding;
    Context mContext;
    String token;
    String user_ID;


    public FragmentProfile() {
        // Required empty public constructor
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = mContext.getSharedPreferences("authToken", Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token","");
//        name = sharedPreferences.getString("name", "");
//        number = sharedPreferences.getString("number", "");

        getProfileData();

        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, ActivityEditProfile.class));
            }
        });


        binding.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                bottomSheetLayout();

                FragmentBottomSheet fragmentBottomSheet = new FragmentBottomSheet();
                fragmentBottomSheet.show(getActivity().getSupportFragmentManager(),fragmentBottomSheet.getTag());

            }
        });

        return binding.getRoot();
    }

    private void getProfileData() {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = "https://blood.dreamitdevlopment.com/public/api/personal-profile-view?token=" + token;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                //Log.d(TAG, "onResponse: @@@@@@@@@@@@@@@@@               Full Response : " + response);

                try {

                    for (int i = 0; i< response.length(); i++){
                        JSONObject profileObject = response.getJSONObject(i);
                        //JSONObject donationDataObject = profileObject.getJSONObject("blood_donation");

                        binding.tvUserName.setText(profileObject.getString("name"));
                        binding.tvUserPhone.setText(profileObject.getString("number"));
                        binding.tvDivision.setText(profileObject.getString("division"));
                        binding.tvDistrict.setText(profileObject.getString("district"));
                        binding.tvUpazila.setText(profileObject.getString("upazila"));

                        String blood = profileObject.getString("blood_donation");
                        //Log.d(TAG, "onResponse: @@@@@@@@@@           Blood : " + blood);

                        if (blood.equals("null")){
                            Log.d(TAG, "onResponse: @@@@@@@@@@@@               No Record");
                        }else {
                            //JSONObject donationDataObject = profileObject.getJSONObject("blood_donation");

                            JSONObject donationDataObject = new JSONObject(blood);

                            binding.tvUserLastDonation.setText(donationDataObject.getString("donation_date"));
                            binding.tvUserTotalDonation.setText(donationDataObject.getString("total_donation"));


                            //Log.d(TAG, "onResponse: @@@@@@@@@@@           Record : " + blood);
                        }


                        Log.d(TAG, "onResponse: @@@@@@@@@@           My Object : " + profileObject);
                        //Log.d(TAG, "onResponse: @@@@@@@@@@           Blood Donation : " + donationDataObject);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }

//    private void bottomSheetLayout() {
//        final Dialog dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.id.bottom_sheet_layout);
//
//        LinearLayout supportLayout = dialog.findViewById(R.id.layoutSupport);
//        LinearLayout shareLayout = dialog.findViewById(R.id.layoutShare);
//        LinearLayout contactLayout = dialog.findViewById(R.id.layoutContact);
//        LinearLayout logoutLayout = dialog.findViewById(R.id.layoutLogout);
//
//        supportLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Support Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        shareLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Share Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        contactLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Contact Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        logoutLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(mContext, "Logout Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        dialog.show();
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.getWindow().setGravity(Gravity.BOTTOM);
//    }
}