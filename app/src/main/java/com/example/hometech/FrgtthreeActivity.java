package com.example.hometech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class FrgtthreeActivity extends AppCompatActivity {
    Button rpsw;
    EditText rpsw1;
    EditText rpsw2;
    String user_url= Config.baseURL + "user_reset_password.php",type,phone,statusid="",error="";
    String tech_url= Config.baseURL + "tech_reset_password.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgtthree);
        rpsw=findViewById(R.id.rpsw);
        rpsw1=findViewById(R.id.rpsw1);
        rpsw2=findViewById(R.id.rpsw2);
        Intent i=getIntent();
        type=i.getStringExtra("type");
        phone=i.getStringExtra("phone");

        rpsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("user")){
                    user_reset();
                }
                else {
                    tech_reset();
                }
            }
        });

    }
    private void user_reset(){
        final String pass,cpass;
        pass=rpsw1.getText().toString();
        cpass=rpsw2.getText().toString();
        if (!pass.equals(cpass)) {
            Toast.makeText(this, "password miss match", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(FrgtthreeActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FrgtthreeActivity.this, "Password SucessesFully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FrgtthreeActivity.this, LoginActivity.class));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FrgtthreeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("password", pass);
                m.put("phone", phone);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);

    }


    private void tech_reset(){
        final String pass,cpass;
        pass=rpsw1.getText().toString();
        cpass=rpsw2.getText().toString();
        if (!pass.equals(cpass)) {
            Toast.makeText(this, "password miss match", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(FrgtthreeActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(FrgtthreeActivity.this, "Password SucessesFully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FrgtthreeActivity.this, LoginActivity.class));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FrgtthreeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("password", pass);
                m.put("phone", phone);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);

    }
}
