package com.example.hometech.user.user_buyed_items;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.R;
import com.example.hometech.tech.TechSession;
import com.example.hometech.user.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserBuyedItemsActivity extends AppCompatActivity {

    private String url = Config.baseURL+ "new/list_buyed.php", username;
    private ArrayList<UserItemsDataModel> dataModelArrayList;
    private UserItemsAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private ProgressBar p;
    Button btnBuy;
    int price=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_buyed_items);

        recyclerView = findViewById(R.id.recycler);
        p = findViewById(R.id.progress);
        btnBuy = findViewById(R.id.btnBuy);

        HashMap<String, String> user = new UserSession(UserBuyedItemsActivity.this).getUserDetails();
        username = user.get("username");

        fetchingJSON();
    }


    private void fetchingJSON() {

        p.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
//                            Toast.makeText(UserBuyedItemsActivity.this, response, Toast.LENGTH_SHORT).show();
                            p.setVisibility(View.GONE);

                            dataModelArrayList = new ArrayList<>();
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {

                                JSONObject dataobj = array.getJSONObject(i);

                                dataModelArrayList.add(new UserItemsDataModel(
                                        dataobj.getString("id"),
                                        dataobj.getString("product_name"),
                                        dataobj.getString("product_price"),
                                        dataobj.getString("product_quantity"),
                                        dataobj.getString("product_image"),
                                        dataobj.getString("username"),
                                        dataobj.getString("status"),
                                        dataobj.getString("bdate"),
                                        dataobj.getString("btime"),
                                        dataobj.getString("location"),
                                        dataobj.getString("datestatus")
                                ));
                            }
                            setupRecycler();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        p.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("username", username);
                return m;
            }
        };

        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 20000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 20000;
            }

            @Override
            public void retry(VolleyError error) {
                p.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void setupRecycler(){
        itemsAdapter = new UserItemsAdapter(this, dataModelArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(itemsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }}
