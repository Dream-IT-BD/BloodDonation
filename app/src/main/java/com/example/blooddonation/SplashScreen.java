package com.example.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blooddonation.Auth.LoginActivity;
import com.example.blooddonation.OnlineStatus.CheckNetwork;
import com.example.blooddonation.OnlineStatus.NoInternet;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        int SPLASH_SCREEN = 5500;



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (CheckNetwork.isInternetAvailable(getApplicationContext())){
                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                    finish();
                }else{
//            getSupportFragmentManager().beginTransaction().add(R.id.loginActivity, new fragNoInternet()).commit();
                    Intent intent = new Intent(SplashScreen.this, NoInternet.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, SPLASH_SCREEN);

    }


    private void checkInternet() {

        if (CheckNetwork.isInternetAvailable(getApplicationContext())){
            Toast.makeText(this, "Internet Connection successful", Toast.LENGTH_SHORT).show();
        }else{
//            getSupportFragmentManager().beginTransaction().add(R.id.loginActivity, new fragNoInternet()).commit();
            Intent intent = new Intent(SplashScreen.this, NoInternet.class);
            startActivity(intent);
            finish();
        }
    }
}

// Previous splashScreen
/*
    <TextView
        android:id="@+id/tvText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginBottom="124dp"
        android:text="Donate Blood Safe Life."
        android:textColor="@color/black"
        android:textSize="30dp"
        android:textStyle="bold|italic"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/ivBlood"
        app:layout_constraintVertical_bias="0.299" />

    <ImageView
        android:id="@+id/ivBlood"
        android:layout_width="match_parent"
        android:layout_height="250dp"
        android:src="@drawable/blood"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
 */