package com.example.hometech;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FrgtoneActivity extends AppCompatActivity {
    Button mb;
    EditText mb1;
    String mobile, type,statusid="",error="";
    String user_url= Config.baseURL + "user_check_phone.php";
    String tech_url= Config.baseURL + "tech_check_phone.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgtone);
        mb1=findViewById(R.id.mb1);
        mb=findViewById(R.id.mb);

        Intent i=getIntent();
        type=i.getStringExtra("type");

        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("user")){
                    usercheckphone();
                }
                else {
                    techcheckphone();
                }


            }
        });

    }
    private void usercheckphone(){
        mobile=mb1.getText().toString();
        if (!isPhoneValid(mobile)){
            mb1.setError("invalid phonenumber");
            return;
        }
        StringRequest s = new StringRequest(Request.Method.POST, user_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            statusid = j.getString("StatusID");
                            error = j.getString("Error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0")) {
                            Toast.makeText(FrgtoneActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                           sentsms();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FrgtoneActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("phone", mobile);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);


    }

    private void techcheckphone(){
        mobile=mb1.getText().toString();
        if (!isPhoneValid(mobile)){
            mb1.setError("invalid phonenumber");
            return;
        }
        StringRequest s = new StringRequest(Request.Method.POST, tech_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject j = new JSONObject(response);
                            statusid = j.getString("StatusID");
                            error = j.getString("Error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0")) {
                            Toast.makeText(FrgtoneActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            sentsms();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FrgtoneActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("phone", mobile);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);


    }

    private void sentsms(){
        Random r = new Random();
        int otp = r.nextInt((9999 - 1000) + 1) + 1000;  //Range: [0,8999]+1000 = [1000,9999]
        String msg = "Welcome to Hometech app. Your OTP for changing the password is " + otp;
        Intent intent = new Intent(FrgtoneActivity.this,FrgttwoActivity .class);
        intent.putExtra("otp", Integer.toString(otp));
        intent.putExtra("phone", mobile);
        intent.putExtra("type",type);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(mobile, null, msg, pi, null);

    }
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }
}
