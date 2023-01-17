package com.example.hometech.tech.Booking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.PayonDelivery;
import com.example.hometech.R;
import com.example.hometech.tech.TechSession;
import com.example.hometech.user.electrician.ViewDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Book_details extends AppCompatActivity {
EditText usern,cont,bdate,btime;
Button recieve;
Spinner stat;
String name,phone,date,time,spin,statusid,error,techname;

String url= Config.baseURL+"new/update_book.php";

String [] status1={"select status","Approve","Reject"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        usern=findViewById(R.id.listname);
        cont=findViewById(R.id.listcnt);
        bdate=findViewById(R.id.listbookdate);
        btime=findViewById(R.id.listbooktime);
        stat=findViewById(R.id.status);
        recieve=findViewById(R.id.Recieved);
//
//        HashMap<String, String> user = new TechSession(this).getUserDetails();
//        techname=user.get("username");

        Intent in=getIntent();
        techname=in.getStringExtra("techname");
        name=in.getStringExtra("name");
        phone=in.getStringExtra("phone");
        date=in.getStringExtra("date");
        time=in.getStringExtra("time");

        usern.setText("Name: "+name);
        cont.setText("Contact: "+phone);
        bdate.setText("Booking date: "+date);
        btime.setText("Booking time: "+time);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, status1);
        stat.setAdapter(adapter);

        recieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               recieved();

            }
        });


    }

    private void recieved() {
        spin=stat.getSelectedItem().toString();
        if(spin.equals("select status")){
            Toast.makeText(Book_details.this, "Select status", Toast.LENGTH_SHORT).show();
        }
        StringRequest st =new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            Toast.makeText(Book_details.this, response, Toast.LENGTH_SHORT).show();

                            JSONObject jsonObject = new JSONObject(response);
                            statusid= jsonObject.getString("StatusID");

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0")) {
                            Toast.makeText(Book_details.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Book_details.this ,"Recieved",Toast.LENGTH_SHORT).show();
                            if (!phone.equals("")) {
                                checkPermission();
                            }


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Book_details.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();
                map.put("status",spin);
                map.put("techname",techname);
                return map;

            }
        };
        RequestQueue q = Volley.newRequestQueue(this);
        q.add(st);

        //validation


    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(Book_details.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(Book_details.this, new String[] { Manifest.permission.SEND_SMS }, 1);
        }
        else {
            sendOTP();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendOTP();
            } else {
                Toast.makeText(Book_details.this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendOTP() {
        String msg = "Your booking " + spin  + " Technician Name : "+techname;

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phone, null, msg, null, null);
    }
}