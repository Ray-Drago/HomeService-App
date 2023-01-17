package com.example.hometech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.payment.checksum;
import com.example.hometech.user.Gpay;
import com.example.hometech.user.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.zip.Checksum;

public class ConfirmBuyActivity extends AppCompatActivity {

    EditText etQuantity;
    Button btnCON, btnPrice;
    TextView tvProduct, tvPrice;
    String date,name,price, quantity, image, username, phone;
    String mQuantity, mPrice,mPhnumber,mAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_buy);

        etQuantity = findViewById(R.id.etQuantity);
        btnCON = findViewById(R.id.btnCON);
        btnPrice = findViewById(R.id.btnPrice);
        tvProduct = findViewById(R.id.tvProduct);
        tvPrice = findViewById(R.id.tvPrice);



        HashMap<String, String> user = new UserSession(this).getUserDetails();
        username = user.get("username");
        phone = user.get("phone");


        Intent i = getIntent();
        name = i.getStringExtra("product_name");
        price = i.getStringExtra("product_price");
        quantity = i.getStringExtra("product_quantity");
        image = i.getStringExtra("product_image");

        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuantity = etQuantity.getText().toString();

                if (TextUtils.isEmpty(mQuantity)) {
                    etQuantity.setError("Enter needed quantity");
                    return;
                }

                int q = Integer.parseInt(mQuantity);
                int p = Integer.parseInt(price);
                int pr = p * q;
                mPrice = String.valueOf(pr);
                tvPrice.setText("Price: " + mPrice);
            }
        });
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        date = df.format(new Date());


        btnCON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ConfirmBuyActivity.this, "Uploading buying details", Toast.LENGTH_SHORT).show();

                checkPermission();
            }
        });


    }


    private void proceedPay() {
        Random r = new Random();
        int i = r.nextInt(10000);

        Intent intent = new Intent(getApplicationContext(), checksum.class);
        intent.putExtra("orderid", String.valueOf(i));
        intent.putExtra("custid", "1");
        intent.putExtra("amount", mPrice);
        startActivity(intent);
    }



    public void sendOTP() {



        Random r = new Random();
        int otp = r.nextInt((9999 - 1000) + 1) + 1000;  //Range: [0,8999]+1000 = [1000,9999]

        String msg = "Welcome to Hometech. Your OTP for confirm your oder  " + otp;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);

        Intent i = new Intent(ConfirmBuyActivity.this, OtpActivity.class);
        i.putExtra("otp", Integer.toString(otp));
        i.putExtra("product_name", name);
        i.putExtra("product_price", mPrice);
        i.putExtra("product_quantity", mQuantity);
        i.putExtra("product_image", image);
        i.putExtra("phone", phone);
        i.putExtra("date", date);

        startActivity(i);
        finish();


    }


    // Function to check and request permission.
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(ConfirmBuyActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(ConfirmBuyActivity.this, new String[] { Manifest.permission.SEND_SMS }, 1);
        }
        else {
            sendOTP();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOTP();
            }
            else {
                Toast.makeText(ConfirmBuyActivity.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
