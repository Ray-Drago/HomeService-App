package com.example.hometech;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PayonDelivery extends AppCompatActivity {
    Button btnBuy,btnCOD;
    String name,price, quantity,image, username, phone;
    String mQuantity, mPrice;
    String status="";
    String url = Config.baseURL+"new/add_payment.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payon_delivery);

        btnBuy = findViewById(R.id.btnBuy);
        btnCOD = findViewById(R.id.btnpay);


        HashMap<String, String> user = new UserSession(this).getUserDetails();
        username = user.get("username");


        Intent i = getIntent();
        name = i.getStringExtra("product_name");
        price = i.getStringExtra("product_price");
        quantity = i.getStringExtra("product_quantity");
        image = i.getStringExtra("product_image");
        phone = i.getStringExtra("phone");

        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                proceedPay();
                Toast.makeText(PayonDelivery.this, "Getting payment details...", Toast.LENGTH_SHORT).show();
                addData();
            }
        });

        btnCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PayonDelivery.this, "Uploading buying details", Toast.LENGTH_SHORT).show();
                COD();
            }
        });

    }




    private void COD() {

        StringRequest s = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Toast.makeText(PayonDelivery.this, response, Toast.LENGTH_SHORT).show();

                            JSONObject c = new JSONObject(response);
                            status = c.getString("StatusID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (status.equals("1")) {

                            Toast.makeText(PayonDelivery.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();

                            if (!phone.equals("")) {
                                checkPermission();
                            }

//                            proceedPay();

                        } else {
                            Toast.makeText(PayonDelivery.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PayonDelivery.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("product_name", name);
                m.put("product_quantity", quantity);
                m.put("product_price", price);
                m.put("product_image", image);
                m.put("username", username);
                return m;
            }
        };
        Volley.newRequestQueue(this).add(s);
    }




    private void addData() {

        StringRequest s = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

//                            Toast.makeText(PayonDelivery.this, response, Toast.LENGTH_SHORT).show();
                            JSONObject c = new JSONObject(response);
                            status = c.getString("StatusID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (status.equals("1")) {
                            Intent intent=new Intent(getApplicationContext(),PayActivity.class);
                            startActivity(intent);
                            if (!phone.equals("")) {
                                checkPermission();
                            }


                        } else {
                            Toast.makeText(PayonDelivery.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PayonDelivery.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("product_name", name);
                m.put("product_quantity", quantity);
                m.put("product_price", price);
                m.put("product_image", image);
                m.put("username", username);
                return m;
            }
        };
        Volley.newRequestQueue(this).add(s);
    }




    public void sendOTP() {

        String msg = username + " has brought " + name + " which was uploaded by you.\nQuantity brought: " + mQuantity;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);

//        Intent i = new Intent(ConfirmBuyActivity.this, OTPActivity.class);
//        i.putExtra("otp", Integer.toString(otp));
//        i.putExtra("phone", phone);
//        startActivity(i);
//        finish();
    }


    // Function to check and request permission.
    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(PayonDelivery.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(PayonDelivery.this, new String[] { Manifest.permission.SEND_SMS }, 1);
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
                Toast.makeText(PayonDelivery.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}