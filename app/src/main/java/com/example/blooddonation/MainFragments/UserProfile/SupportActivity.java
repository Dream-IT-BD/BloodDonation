package com.example.blooddonation.MainFragments.UserProfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.R;

public class SupportActivity extends AppCompatActivity {

    private static final String TAG = "SupportActivity";
    LinearLayout donateViaBikash, donateViaNagad;
    final String donationNumber = "01712508520";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        donateViaBikash = findViewById(R.id.donateViaBikash);
        donateViaNagad = findViewById(R.id.donateViaNagad);

        donateViaBikash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DonationNumber", donationNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(SupportActivity.this, "কপি সম্পন্ন : " + donationNumber, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@              Bikash Donation Number Copy Complete");
            }
        });

        donateViaNagad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("DonationNumber", donationNumber);
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(SupportActivity.this, "কপি সম্পন্ন : " + donationNumber, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick: @@@@@@@@@@@@@@@              Nagad Donation Number Complete");
            }
        });

    }
}