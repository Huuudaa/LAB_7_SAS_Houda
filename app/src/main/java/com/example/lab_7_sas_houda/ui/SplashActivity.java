package com.example.lab_7_sas_houda.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lab_7_sas_houda.R;

public class SplashActivity extends AppCompatActivity {

    ImageView imgAccueil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imgAccueil = findViewById(R.id.imgAccueil);

        imgAccueil.animate().rotation(360f).setDuration(2000);
        imgAccueil.animate().scaleX(0.5f).scaleY(0.5f).setDuration(3000);
        imgAccueil.animate().translationYBy(1000f).setDuration(2000);
        imgAccueil.animate().alpha(0f).setDuration(6000);

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                startActivity(new Intent(SplashActivity.this, GalerieActivity.class));
                finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}