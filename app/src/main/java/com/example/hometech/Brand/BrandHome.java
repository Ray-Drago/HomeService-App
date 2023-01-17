package com.example.hometech.Brand;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hometech.Brand.Brandlist.Brandlitst;
import com.example.hometech.R;

public class BrandHome extends AppCompatActivity {
CardView appliance,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_home);
        appliance=findViewById(R.id.appliance);
        profile=findViewById(R.id.profile);

        appliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Brandlitst.class));
            }
        });

    }
}