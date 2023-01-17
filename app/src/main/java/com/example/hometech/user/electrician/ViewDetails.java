package com.example.hometech.user.electrician;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.R;
import com.example.hometech.user.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;


public class ViewDetails extends AppCompatActivity {
    TextView uname, wages, day, places, phon, categ, subs;
    Button button, button1;
    String name, phone, place, days, wage, category, subcategory, username,
            userphone, status, error, date, strDate;
    String url = Config.baseURL + "new/book.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);
        uname = findViewById(R.id.listname);
        phon = findViewById(R.id.listphone);
        places = findViewById(R.id.listplace);
        day = findViewById(R.id.listday);
        wages = findViewById(R.id.listwage);
        categ = findViewById(R.id.listcategory);
        subs = findViewById(R.id.listsubcategory);
        button = findViewById(R.id.btnbook);
        button1 = findViewById(R.id.btnCall);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + userphone));
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book();
            }
        });
        HashMap<String, String> user = new UserSession(ViewDetails.this).getUserDetails();

        username = user.get("username");
        userphone = user.get("phone");


        Intent in = getIntent();
        name = in.getStringExtra("name");
        phone = in.getStringExtra("phone");
        place = in.getStringExtra("place");
       // userphone = in.getStringExtra("phone");
        days = in.getStringExtra("days");
        wage = in.getStringExtra("wage");
        category = in.getStringExtra("category");
        subcategory = in.getStringExtra("subcategory");


        uname.setText( name);
        places.setText( place);
        phon.setText(userphone);
        day.setText( days);
        wages.setText(wage);
        categ.setText(category);
        subs.setText( subcategory);


        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyy-MM-dd");
        date = df.format(new Date());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        strDate = mdformat.format(calendar.getTime());

    }




    private void book() {

        StringRequest st = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("StatusId");
                            error = jsonObject.getString("Error");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if ("0".equals(status)) {
                            Toast.makeText(ViewDetails.this, error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ViewDetails.this, "Registered successfully", Toast.LENGTH_SHORT).show();


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ViewDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("techname", name);
                map.put("techphone", phone);
                map.put("techplace", place);
                map.put("techdays", days);
                map.put("techwage", wage);
                map.put("techcategory", category);
                map.put("techsub", subcategory);
                map.put("username", username);
                map.put("contact", userphone);
                map.put("currentdate", date);
                map.put("currenttime", strDate);
                return map;

            }
        };
        RequestQueue q = Volley.newRequestQueue(this);
        q.add(st);

        //validation
    }
}

