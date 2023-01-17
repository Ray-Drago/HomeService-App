package com.example.hometech;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mumayank.com.airlocationlibrary.AirLocation;

public class UploadLocationActivity extends AppCompatActivity {

    EditText location;
    Button entered, current, upload;
    TextView tlat, tlon;
    AirLocation airLocation;
    String status,error;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_location);

        location = findViewById(R.id.location);
        entered = findViewById(R.id.entered);
        current = findViewById(R.id.current);
        upload = findViewById(R.id.upload);
        tlat = findViewById(R.id.lat);
        tlon = findViewById(R.id.lon);

        Intent i=getIntent();
        user=i.getStringExtra("username");

        entered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location1 = location.getText().toString();

                Geocoder geoCoder = new Geocoder(UploadLocationActivity.this);

                try {
                    List<Address> locations = geoCoder.getFromLocationName(location1, 1);
                    Log.e("loc","loc= "+locations);
                    for (Address a : locations) {
                        double lat = a.getLatitude();
                        double lng = a.getLongitude();
                        tlat.setText(Double.toString(lat));
                        tlon.setText(Double.toString(lng));
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        });
        current.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           // Fetch location simply like this whenever you need
                                           airLocation = new AirLocation(UploadLocationActivity.this, true, true, new AirLocation.Callbacks() {
                                               @Override
                                               public void onFailed(AirLocation.LocationFailedEnum locationFailedEnum) {

                                               }

                                               @Override
                                               public void onSuccess(Location location) {
                                                   Toast.makeText(UploadLocationActivity.this, "Location fetched successfully: " + location, Toast.LENGTH_LONG).show();
                                                   double lat1, lng1;
                                                   lat1 = location.getLatitude();
                                                   lng1 = location.getLongitude();

                                                   tlat.setText(Double.toString(lat1));
                                                   tlon.setText(Double.toString(lng1));


                                               }
                                           });
                                       }
                                   });
                upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upload();
                    }
                });
            }


    private void upload() {
        final String lat = tlat.getText().toString();
        final String lng = tlon.getText().toString();
        final String place = location.getText().toString();
        String url= Config.baseURL + "locationupdate.php";

        if (TextUtils.isEmpty(lat) || TextUtils.isEmpty(lng)) {
            Toast.makeText(this, "Coordinates are not fetched successfully",
                    Toast.LENGTH_LONG).show();
            return;
        } else if (TextUtils.isEmpty(place)) {
            location.setError("Enter place name");
            location.requestFocus();
            return;
        }

        StringRequest s = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("StatusID");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (status.equals("1")) {
                            Toast.makeText(UploadLocationActivity.this, "Registration completed successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UploadLocationActivity.this, "Uploading error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UploadLocationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("username", user);
                map.put("location", place);
                map.put("lat", lat);
                map.put("lng", lng);
                return map;
            }
        };
        RequestQueue q = Volley.newRequestQueue(this);
        q.add(s);
    }
}


