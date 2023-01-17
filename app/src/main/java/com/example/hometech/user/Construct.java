package com.example.hometech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hometech.R;
import com.example.hometech.tech.TechSession;

import java.util.HashMap;

public class Construct extends AppCompatActivity {
TextView eng,mes,carp;
String engi,mesi,carpp;
String subcategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construct);
        eng=findViewById(R.id.engineer);
        mes=findViewById(R.id.mesan);
        carp=findViewById(R.id.carpenter);
        HashMap<String, String> m = new TechSession(this).getUserDetails();
        subcategory=m.get("subcategory");

        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                engineer();

            }
        });
    }

    private void engineer() {
        engi=eng.getText().toString();
        mesi=mes.getText().toString();
        carpp=carp.getText().toString();


    }
}