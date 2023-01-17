package com.example.hometech.user;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mumayank.com.airlocationlibrary.AirLocation;


public class UsertechList extends AppCompatActivity {
    RecyclerView recyclerView;
    static ProgressDialog progressDialog;
    String url =  Config.baseURL + "new/list_technicians.php";
    TechnicianAdapter technicianAdapter;
    ArrayList<TechnicianDataModel>arrayList;
    Context c ;
    SearchView searchView;
    AirLocation airLocation;
    String lat, lng;

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                technicianAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                technicianAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertech_list);
        recyclerView=findViewById(R.id.recycles);
        getLocation();
    }

    private void getLocation() {
            showSimpleProgressDialog(UsertechList.this, null, "Fetching location...", false);

            airLocation = new AirLocation(UsertechList.this, true, true, new AirLocation.Callbacks() {
                @Override
                public void onSuccess(@NonNull Location location) {
                    removeSimpleProgressDialog();

                    lat = String.valueOf(location.getLatitude());
                    lng = String.valueOf(location.getLongitude());

                    fetchingJSON();
                }

                @Override
                public void onFailed(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
                    removeSimpleProgressDialog();
                    Toast.makeText(UsertechList.this, "Location fetching failed. Try again", Toast.LENGTH_LONG).show();
                }
            });
        }

    private void fetchingJSON() {

        showSimpleProgressDialog(UsertechList.this, null, "Loading...", false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        removeSimpleProgressDialog();

                        String status, data;

                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            data = c.getString("data");

                            if (status.equals("1")) {
                               // Toast.makeText(UsertechList.this, response, Toast.LENGTH_SHORT).show();
                                //converting the string to json array object


                                arrayList = new ArrayList<>();
                                JSONArray array = new JSONArray(data);

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);

                                    //adding the product to product list
                                    arrayList.add(new TechnicianDataModel(
                                            product.getString("id"),
                                            product.getString("username"),
                                            product.getString("emailid"),
                                            product.getString("phone"),
                                            product.getString("distance"),
                                            product.getString("place"),
                                            product.getString("wage"),
                                            product.getString("available_days")
                                    ));
                                }
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
                        Log.e("Log", "inside onErrorResponse");
                        removeSimpleProgressDialog();
                        //displaying the error in toast if occurrs
                        Toast.makeText(UsertechList.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map <String,String> map=new HashMap<>();
                map.put("lat",lat);
                map.put("lng",lng);
                return map;
            }

        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void setupRecycler(){

        technicianAdapter = new TechnicianAdapter(this,arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(technicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public static void removeSimpleProgressDialog() {
        try {
            if (progressDialog != null) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            Log.e("Log", "inside catch IllegalArgumentException");
            ie.printStackTrace();

        } catch (RuntimeException re) {
            Log.e("Log", "inside catch RuntimeException");
            re.printStackTrace();
        } catch (Exception e) {
            Log.e("Log", "Inside catch Exception");
            e.printStackTrace();
        }

    }

    public static void showSimpleProgressDialog(Context context, String title,
                                                String msg, boolean isCancelable) {
        try {
            if (progressDialog == null) {
                progressDialog = ProgressDialog.show(context, title, msg);
                progressDialog.setCancelable(isCancelable);
            }

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}