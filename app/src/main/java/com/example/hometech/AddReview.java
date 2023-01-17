package com.example.hometech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.user.Categories;
import com.example.hometech.user.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddReview extends AppCompatActivity {
    EditText editfeed;
    RatingBar ratingBar;
    Button add;
    String editfeeds,ratingvalue,techid,techname,techphn,userid,username,userphn,status,message;
    float ratings;
    String urlrating=Config.baseURL+"review.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        editfeed=findViewById( R.id.feed);
        ratingBar=findViewById( R.id.raingbar);
        add=findViewById( R.id.btnsub);

        HashMap<String, String> user = new UserSession(AddReview.this).getUserDetails();
        userid = user.get("id");
        username = user.get("username");
        userphn = user.get("phone");


        Intent intent = getIntent();
        techid = intent.getStringExtra("techid");
        techname = intent.getStringExtra("techname");
        techphn = intent.getStringExtra("techphn");




        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rating();
            }
        });
    }

    private void rating() {
        ratings=ratingBar.getRating();

        editfeeds=editfeed.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlrating,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            message = c.getString("message");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status.equals("1")) {
                            Toast.makeText(AddReview.this, "Review Added", Toast.LENGTH_SHORT).show();
startActivity(new Intent(getApplicationContext(), Categories.class));
                        } else {
                            Toast.makeText(AddReview.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddReview.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("userid",userid);
                map.put("username",username);
                map.put("userphn",userphn);
                map.put("techid",techid);
                map.put("techname",techname);
                map.put("techphn",techphn);
                map.put("feedback",editfeeds);
                map.put("ratingvalue", String.valueOf(ratings));
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
