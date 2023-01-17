package com.example.blooddonation.MainFragments.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.blooddonation.R;
import com.example.blooddonation.databinding.ActivitySupportBinding;

public class ActivitySupport extends AppCompatActivity {

    private static final String TAG = "SupportActivity";
    final String donationNumber = "01712508520";
    ActivitySupportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        binding = ActivitySupportBinding.inflate(getLayoutInflater());

        binding.donateViaBikash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DonationNumber", donationNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ActivitySupport.this, "কপি সম্পন্ন : " + donationNumber, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@              Bikash Donation Number Copy Complete");
            }
        });

        binding.donateViaNagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DonationNumber", donationNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(ActivitySupport.this, "কপি সম্পন্ন : " + donationNumber, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@              Nagad Donation Number Complete");
            }
        });

    }
}