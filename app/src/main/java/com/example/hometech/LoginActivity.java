package com.example.hometech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.tech.TechHomeActivity;
import com.example.hometech.tech.TechSession;
import com.example.hometech.user.HomeActivity;
import com.example.hometech.user.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView Register;
    EditText ed;
    EditText psw;
    Button log;
    TextView fp;
    Spinner spinner;
    ArrayList<String> type;

    String tech_url = Config.baseURL + "login.php";
    String user_url = Config.baseURL + "user_login.php";

    String statusid = "", error = "", id, username, phone, category, image,subcategory,email1,password1,location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Register = findViewById(R.id.reg);
        ed = findViewById(R.id.ed);
        psw = findViewById(R.id.psw);
        log = findViewById(R.id.log);
        fp = findViewById(R.id.fp);

        spinner = findViewById(R.id.spinner);
        type = new ArrayList<>();
        type.add("User");
        type.add("Technician");
//        type.add("Brand");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, type);
        spinner.setAdapter(adapter);

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=spinner.getSelectedItem().toString();
                if (type.equals("User")){
                    Intent i = new Intent(LoginActivity.this, FrgtoneActivity.class);
                    i.putExtra("type","user");
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(LoginActivity.this, FrgtoneActivity.class);
                    i.putExtra("type","tech");
                    startActivity(i);
                }


            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);

            }
        });
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type=spinner.getSelectedItem().toString();
                if (type.equals("User")){
                    userlogin();
                }
                if(type.equals("Technician")) {
                    techlogin();
                }
            }
        });
    }




    public void userlogin() {
        final String email, password;
        email = ed.getText().toString();
        password = psw.getText().toString();

        if (TextUtils.isEmpty(email)) {
            ed.setError("ENTER EMAIL");
            ed.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            psw.setError("ENTER PASSWORD");
            psw.requestFocus();
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

                            id = j.getString("id");
                            username = j.getString("username");
                            phone = j.getString("phone");
                            image = j.getString("image");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0")) {
                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            new UserSession(LoginActivity.this).createLoginSession(id, username, password, email, phone, image);
                            LoginSession s = new LoginSession(LoginActivity.this);
                            s.setStatus();
                            s.setUserType("user");

                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("password", password);
                m.put("emailid", email);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);

    }


    public void techlogin() {

        final String email, password;
        email = ed.getText().toString();
        password = psw.getText().toString();

        if (TextUtils.isEmpty(email)) {
            ed.setError("ENTER EMAIL");
            ed.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            psw.setError("ENTER PASSWORD");
            psw.requestFocus();
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

                            id = j.getString("id");
                            username = j.getString("username");
                            email1=j.getString("emailid");
                            password1=j.getString("password");
                            phone = j.getString("phone");
                            category = j.getString("category");
                            image = j.getString("image");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0")) {
                            Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                            new TechSession(LoginActivity.this).createLoginSession(id, username, email1, password1, phone, category,subcategory);
                            LoginSession s = new LoginSession(LoginActivity.this);
                            s.setStatus();
                            s.setUserType("tech");

                            startActivity(new Intent(LoginActivity.this, TechHomeActivity.class));
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("password", password);
                m.put("emailid", email);
                return m;

            }
        };
        RequestQueue r = Volley.newRequestQueue(this);
        r.add(s);
    }
}