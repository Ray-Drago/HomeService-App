package com.example.hometech.tech;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.R;
import com.example.hometech.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TechEditProfileActivity extends AppCompatActivity {

    String id, username, email, phone, password, category, image,subcategory,wage,avday;
    EditText etUsername, etEmail, etPhone,wa;
    Spinner available;
    Button btnEdit;
    String status, error;
    public Uri imguri;
    ImageView imageView;
    Spinner spCategory,sb;
    private static ProgressDialog mProgressDialog;
    String url = Config.baseURL + "new/update_tech_profile.php";

    String[]mechanic={"Ac mechanic","Vehicle automation","Auto glass mechanics","Race car mechanics"};
    String[]plumbing={"Master Plumber","Journeyman Plumber"};
    String[]electric={"wiring","Ac mechanic","hardware technician"};
    String[]computer={"Software engineer","hardware engineer","Database administrator"};
    String[]construction={"mesan","carpenter","engineer","plumber"};
    String[]laundry={"tailoring","ironing","drycleaning"};
    String []days={"select days","Mon-Sat","Mon-Sun","Sun"};
    String[] categories = {"Electrical Technician", "Computer Technician","Construction","Laundry","Mechanic","Plumbing"};
    String[] sbcategory = {"wiring","driver","Ac mechanic","hardware technician","mesan",
            "tailor","ironing","drycleaning","engineer","carpenter","hardware engineer","Database administrator"};

    private String upload_URL = Config.baseURL + "new/uploadfile.php";
    private RequestQueue rQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_edit_profile);

        etEmail = findViewById(R.id.uemail);
        etUsername = findViewById(R.id.uname);
        etPhone = findViewById(R.id.uphn);
        btnEdit = findViewById(R.id.uedit);
        spCategory = findViewById(R.id.spCategory);
        wa = findViewById(R.id.wages);
        available= findViewById(R.id.days);
        sb=findViewById(R.id.sbCategory);

 spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
     category=categories[position];

     if(category.equals("Electrical Technician")){
         ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, electric);
         sb.setAdapter(adapter2);
     }
        if(category.equals("Computer Technician")){
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, computer);
            sb.setAdapter(adapter2);
        }
        if(category.equals("Construction")){
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, construction);
            sb.setAdapter(adapter2);
        }
        if(category.equals("Laundry")){
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, laundry);
            sb.setAdapter(adapter2);
        }
        if(category.equals("Mechanic")){
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, mechanic);
            sb.setAdapter(adapter2);
        }
        if(category.equals("Plumbing")){
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(TechEditProfileActivity.this, R.layout.support_simple_spinner_dropdown_item, plumbing);
            sb.setAdapter(adapter2);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
});

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);
        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, days);
        available.setAdapter(adapter4);
        ArrayAdapter<String> sbadapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, sbcategory);
        sb.setAdapter(sbadapter);
        HashMap<String, String> m = new TechSession(this).getUserDetails();

        id = m.get("id");
        username = m.get("username");
        password = m.get("password");
        email = m.get("emailid");
        phone = m.get("phone");
        category = m.get("category");
        subcategory=m.get("subcategory");
        image = m.get("image");
        wage=m.get("wage");
        avday=m.get("avaday");

//        Toast.makeText(this, category, Toast.LENGTH_SHORT).show();

//        if (!TextUtils.isEmpty(image)) {
//            Picasso.get().load(image).into(imageView);
//        }

        if (category.equals("Electrical Technician")) {
            spCategory.setSelection(0);
        } else if (category.equals("Computer Technician")) {
            spCategory.setSelection(1);
        } else if (category.equals("Plumber")) {
            spCategory.setSelection(2);
        }

        etEmail.setText(password);
        etPhone.setText(phone);
        etUsername.setText(username);

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,1);
//            }
//        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        final String username = etUsername.getText().toString();
        final String email = etEmail.getText().toString();
        final String phone = etPhone.getText().toString();
         avday = available.getSelectedItem().toString();
         wage = wa.getText().toString();
        subcategory=sb.getSelectedItem().toString();
        category = spCategory.getSelectedItem().toString();

        showSimpleProgressDialog(this, null, "Loading...", false);

        StringRequest s = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        removeSimpleProgressDialog();
                        try {
                            JSONObject c = new JSONObject(response);
                            status = c.getString("status");
                            error = c.getString("error");

                            Toast.makeText(TechEditProfileActivity.this, status, Toast.LENGTH_SHORT).show();

                            if (status.equals("1")) {
                                Toast.makeText(TechEditProfileActivity.this, "Changes saved successfully", Toast.LENGTH_SHORT).show();
                                new TechSession(TechEditProfileActivity.this).createLoginSession(id, username, email, password, phone, category,subcategory);
                                startActivity(new Intent(getApplicationContext(), TechHomeActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(TechEditProfileActivity.this, error, Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        removeSimpleProgressDialog();
                        Toast.makeText(TechEditProfileActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> m = new HashMap<>();
                m.put("username", username);
                m.put("email", email);
                m.put("phone", phone);
                m.put("id", id);
                m.put("wage",wage);
                m.put("available_days",avday);
                m.put("category", category);
                m.put("subcategory",subcategory);
                return m;
            }
        };
        RequestQueue q = Volley.newRequestQueue(this);
        q.add(s);
    }




    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            String uriString = uri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = this.getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        uploadPDF(displayName,uri);
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
        imguri = data.getData();
        imageView.setImageURI(imguri);

    }

    private void uploadPDF(final String pdfname, Uri pdffile){

        InputStream iStream = null;
        try {

            iStream = getContentResolver().openInputStream(pdffile);
            final byte[] inputData = getBytes(iStream);

            showSimpleProgressDialog(this, null, "Uploading image", false);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, upload_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            removeSimpleProgressDialog();
                            Log.d("ressssssoo",new String(response.data));
                            rQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                jsonObject.toString().replace("\\\\","");

                                if (jsonObject.getString("status").equals("true")) {
                                    Log.d("come::: >>>  ","yessssss");
                                    JSONArray dataArray = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < dataArray.length(); i++) {
                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        url = dataobj.optString("image");
//                                        Toast.makeText(TechEditProfileActivity.this, url, Toast.LENGTH_SHORT).show();
                                        image = url;
                                        Picasso.get().load(url).into(imageView);
                                        new TechSession(TechEditProfileActivity.this).createLoginSession(id, username, email, password, phone, category,subcategory);
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            removeSimpleProgressDialog();
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                     params.put("id", id);  //add string parameters
                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    params.put("filename", new DataPart(pdfname ,inputData));
                    return params;
                }
            };


            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            rQueue = Volley.newRequestQueue(TechEditProfileActivity.this);
            rQueue.add(volleyMultipartRequest);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }


    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
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
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
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