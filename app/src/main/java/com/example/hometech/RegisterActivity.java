package com.example.hometech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.tech.TechHomeActivity;
import com.example.hometech.user.HomeActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    EditText em;
    EditText ps,pn,un;
    Button bb;
    String url= Config.baseURL + "reg.php";
    String userurl= Config.baseURL + "user_reg.php";
    String statusid="",error="";
    String approve="Not Approve";
    Spinner spinner;
    ArrayList<String> type;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);

        em=findViewById(R.id.em);
        ps=findViewById(R.id.ps);
        bb=findViewById(R.id.bb);
        pn=findViewById(R.id.pn);
        un=findViewById(R.id.un);
        progress=findViewById(R.id.progress);
        spinner=findViewById(R.id.spinner);
        type=new ArrayList<>();
        type.add("User");
        type.add("Technician");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,type);
        spinner.setAdapter(adapter);


        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type=spinner.getSelectedItem().toString();
                if (type.equals("User")){
                    userRegister();
                }
                else {
                    technicianRegister();
                }
            }
        });
    }


    public void technicianRegister() {
        final String email, password, username, phonenumber;
        email = em.getText().toString();
        password = ps.getText().toString();
        username = un.getText().toString();
        phonenumber = pn.getText().toString();

        if (TextUtils.isEmpty(email)) {
            em.setError("EMAIL REQUIRED");
            em.requestFocus();
            return;
        }
        else if (!isEmailValid(email)){
            em.setError("Invalid EMAIL");
            em.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ps.setError("PASSWORD REQUIRED");
            ps.requestFocus();
            return;
        }
        else if (password.length()<8){
            ps.setError("characters atleast must 8 characters");
            ps.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username))
        {
            ps.setError("USERNAME REQUIRED");
            ps.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phonenumber))
        {
            ps.setError("PHONENUMBER REQUIRED");
            ps.requestFocus();
            return;
        }
        else if (!isPhoneValid(phonenumber)){
            ps.setError("Invalid number");
            ps.requestFocus();
            return;


        }
        progress.setVisibility(View.VISIBLE);
        StringRequest s=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.setVisibility(View.GONE);
                        try {
                            JSONObject j=new JSONObject(response);
                            statusid=j.getString("StatusID");
                            error=j.getString("Error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0"))
                        {
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(RegisterActivity.this, UploadLocationActivity.class);
                            i.putExtra("username", username);
                            startActivity(i);
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> m=new HashMap<>();
                m.put("username",username);
                m.put("password",password);
                m.put("phone",phonenumber);
                m.put("email",email);
                m.put("approve",approve);
                return m;

            }
        };
        RequestQueue r= Volley.newRequestQueue(this);
        r.add(s);


    }


    public void userRegister(){
        final String email, password, username, phonenumber;
        email = em.getText().toString();
        password = ps.getText().toString();
        username = un.getText().toString();
        phonenumber = pn.getText().toString();
        if (TextUtils.isEmpty(email)) {
            em.setError("EMAIL REQUIRED");
            em.requestFocus();
            return;
        }
        else if (!isEmailValid(email)){
            em.setError("Invalid EMAIL");
            em.requestFocus();
            return;

        }
        if (TextUtils.isEmpty(password)) {
            ps.setError("PASSWORD REQUIRED");
            ps.requestFocus();
            return;
        }
        else if (password.length()<8){
            ps.setError("characters atleast must 8 characters");
            ps.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(username))
        {
            ps.setError("USERNAME REQUIRED");
            ps.requestFocus();
            return;
//
        }
        if (TextUtils.isEmpty(phonenumber))
        {
            ps.setError("PHONENUMBER REQUIRED");
            ps.requestFocus();
            return;
        }
        else if (!isPhoneValid(phonenumber)){
            ps.setError("Invalid number");
            ps.requestFocus();
            return;


        }
        progress.setVisibility(View.VISIBLE);

        StringRequest s=new StringRequest(Request.Method.POST, userurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.setVisibility(View.GONE);
                        try {
                            JSONObject j=new JSONObject(response);
                            statusid=j.getString("StatusID");
                            error=j.getString("Error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (statusid.equals("0"))
                        {
                            Toast.makeText(RegisterActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String> m=new HashMap<>();
                m.put("username",username);
                m.put("password",password);
                m.put("phone",phonenumber);
                m.put("email",email);
                return m;

            }
        };
        RequestQueue r= Volley.newRequestQueue(this);
        r.add(s);


    }
    public static boolean isPhoneValid(String s) {
        Pattern p = Pattern.compile("(0/91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(s);
        return (m.find() && m.group().equals(s));
    }

    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }
}
