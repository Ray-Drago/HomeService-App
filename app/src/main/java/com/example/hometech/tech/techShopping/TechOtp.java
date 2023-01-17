package com.example.hometech.tech.techShopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hometech.PayonDelivery;
import com.example.hometech.R;
import com.example.hometech.tech.TechPayonDelivery;
import com.example.hometech.tech.TechSession;
import com.goodiebag.pinview.Pinview;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TechOtp extends AppCompatActivity {
    String category, name, desc, price, quantity, upload, image, username, phone;
    String mUsername, mAddress,mEmail,date,mPhnumber;
    Pinview pnt;
    Button bt1;
    String status,error, otp;
    SimpleDateFormat df;
    int dayShift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        pnt=findViewById(R.id.ph);
        bt1=findViewById(R.id.bt);

        HashMap<String, String> m = new TechSession(TechOtp.this).getUserDetails();
        username = m.get("username");
        phone = m.get("phone");


        Intent i = getIntent();
        otp=i.getStringExtra("otp");
        name = i.getStringExtra("product_name");
        price = i.getStringExtra("product_price");
        quantity = i.getStringExtra("product_quantity");
        image = i.getStringExtra("product_image");
        phone = i.getStringExtra("phone");



        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otp.equals(pnt.getValue())){
                    Intent i=new Intent(TechOtp.this, TechPayonDelivery.class);
                    // Toast.makeText(OtpActivity.this, "Your oder id confirmed", Toast.LENGTH_SHORT).show();

                    i.putExtra("phone", phone);
                    i.putExtra("product_name", name);
                    i.putExtra("product_price", price);
                    i.putExtra("product_quantity", quantity);
                    i.putExtra("product_image", image);
                    i.putExtra("date", date);
                    startActivity(i);
                    finish();

                    checkPermission();
                }
                else{
                    Toast.makeText(TechOtp.this, "otp mismatches", Toast.LENGTH_SHORT).show();
                }

            }
        });
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(new Date());
        dayShift = 6; // Positive for next days, negative for previous days
        Calendar c = Calendar.getInstance();
        if (dayShift  != 0) {
            c.add(Calendar.DAY_OF_YEAR, dayShift);
        }

    }

    public void sendSms() {
//        date1=date;
//        Calender c.add(Calendar.DATE, 6);
//        date1 = df.format(c.getTime());
//
//        Log.v("NEXT DATE : ", date1);

//        Random r = new Random();
//        int otp = r.nextInt((9999 - 1000) + 1) + 1000;  //Range: [0,8999]+1000 = [1000,9999]

        String msg = "Welcome to plam square. Your order can cancel befor  " + dayShift + "days from product delivery date";

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);

//        Intent i = new Intent(OtpActivity.this, OtpActivity.class);
//        i.putExtra("otp", Integer.toString(otp));
//        i.putExtra("phone", phone);
//        i.putExtra("product_cat", category);
//        i.putExtra("shopname", name);
//        i.putExtra("productname", desc);
//        i.putExtra("productprice", mPrice);
//        i.putExtra("quantity", mQuantity);
//        i.putExtra("upload", upload);
//        i.putExtra("image", image);
//        i.putExtra("number", mPhnumber);
//        i.putExtra("date", date);
//
//        startActivity(i);
//        finish();


    }
    // Function to check and request permission.
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(TechOtp.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(TechOtp.this, new String[] { Manifest.permission.SEND_SMS }, 1);
        }
        else {
            sendSms();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            }
            else {
                Toast.makeText(TechOtp.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


}