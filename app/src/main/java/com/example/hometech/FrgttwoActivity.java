package com.example.hometech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goodiebag.pinview.Pinview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrgttwoActivity extends AppCompatActivity {
    Button otp;
    Pinview otp1;
    String mobile,otpp,ot,type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgttwo);
        otp=findViewById(R.id.otp);
        otp1=findViewById(R.id.otp1);


        Intent i=getIntent();
        ot=i.getStringExtra("otp");
        mobile=i.getStringExtra("phone");
        type=i.getStringExtra("type");

        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpp=otp1.getValue();
                if (otpp.equals(ot)) {
                    Intent i=new Intent(FrgttwoActivity.this,FrgtthreeActivity.class);
                    i.putExtra("phone",mobile);
                    i.putExtra("type",type);
                    startActivity(i);
                }
                else {
                    Toast.makeText(FrgttwoActivity.this, "incorrect OTP", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
