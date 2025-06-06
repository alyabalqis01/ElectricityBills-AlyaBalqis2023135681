package com.example.electricitybills_alyabalqis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Button githubButton = findViewById(R.id.btnGitHub);
        githubButton.setOnClickListener(v -> {
            String url = "https://github.com/alyabalqis01/ElectricityBills-AlyaBalqis";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}
