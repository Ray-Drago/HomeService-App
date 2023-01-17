package com.example.hometech.user;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import java.util.List;
import java.util.Map;

import mumayank.com.airlocationlibrary.AirLocation;

public class UserTechnicianFragment extends Fragment {

    RecyclerView recyclerView;
    static ProgressDialog progressDialog;
    String url =  Config.baseURL + "new/list_technicians.php";
    TechnicianAdapter technicianAdapter;
    ArrayList<TechnicianDataModel>arrayList;
    Context c ;
    AirLocation airLocation;
    String latitude, longtitude;

    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_technician, container, false);
        recyclerView=view.findViewById(R.id.recycles);
        fetchLocation();
        return view;
    }

    private void fetchLocation() {

        showSimpleProgressDialog(getActivity(), "Loading",
                "Fetching current location coordinates", true);

        //AirLocation fetching process...
        airLocation = new AirLocation(getActivity(), true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NonNull Location location) {
                removeSimpleProgressDialog();
                Toast.makeText(getActivity(), "Location fetched successfully", Toast.LENGTH_LONG).show();
                double lat, lng;
                lat = location.getLatitude();
                lng = location.getLongitude();
                latitude=Double.toString(lat);
                longtitude=Double.toString(lng);
//                Toast.makeText(getActivity(), latitude+longtitude, Toast.LENGTH_SHORT).show();
                fetchingJSON();
//                Toast tvLat;
//                tvLat.setText(Double.toString(lat));
//                Toast tvLng;
//                tvLng.setText(Double.toString(lng));
            }

            @Override
            public void onFailed(@NonNull AirLocation.LocationFailedEnum locationFailedEnum) {
                removeSimpleProgressDialog();
                Toast.makeText(getActivity(), "Location fetching failed. Please check your internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }


    // override and call airLocation object's method by the same name
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        airLocation.onActivityResult(requestCode, resultCode, data);
    }


    // override and call airLocation object's method by the same name
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
      searchView=(SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    technicianAdapter.getFilter().filter(newText);
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    technicianAdapter.getFilter().filter(query);
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }
    private void fetchingJSON() {

//        showSimpleProgressDialog(getActivity(), null, "Loading...", false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        removeSimpleProgressDialog();
//                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

                        String status, data;

                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            data = c.getString("data");

                            if (status.equals("1")) {
//                                /Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                 Map <String,String> map=new HashMap<>();
                map.put("lat",latitude);
                map.put("lng",longtitude);
                return map;
            }

        };

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    private void setupRecycler(){

        technicianAdapter = new TechnicianAdapter(getActivity(),arrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(technicianAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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