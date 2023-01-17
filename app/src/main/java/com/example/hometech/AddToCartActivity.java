package com.example.hometech;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.user.UserSession;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class AddToCartActivity extends AppCompatActivity {

    EditText etQuantity;
    Button btnBuy, btnPrice;
    TextView tvProduct, tvPrice;
    String name, price, quantity,image, username;
    String mQuantity, mPrice;
    String status="";
    String url = Config.baseURL+ "new/carts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        etQuantity = findViewById(R.id.etQuantity);
        btnBuy = findViewById(R.id.btnBuy);
        btnPrice = findViewById(R.id.btnPrice);
        tvProduct = findViewById(R.id.tvProduct);
        tvPrice = findViewById(R.id.tvPrice);


        HashMap<String, String> user = new UserSession(this).getUserDetails();
        username = user.get("username");


        Intent i = getIntent();
        name = i.getStringExtra("product_name");
        price = i.getStringExtra("product_price");
        quantity = i.getStringExtra("quantity");
        image= i.getStringExtra("product_image");
//        quantity = i.getStringExtra("quantity");
//        upload = i.getStringExtra("upload");
        btnPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuantity = etQuantity.getText().toString();

                if (TextUtils.isEmpty(mQuantity)) {
                    etQuantity.setError("Enter needed quantity");
                    return;
                }

                int q = Integer.parseInt(mQuantity);
                int p = Integer.parseInt(price);
                int pr = p * q;
                mPrice = String.valueOf(pr);
                tvPrice.setText("Price: " + mPrice);
            }
        });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuantity = etQuantity.getText().toString();
                int q = Integer.parseInt(mQuantity);
                int p = Integer.parseInt(price);
                int pr = p * q;
                mPrice = String.valueOf(pr);

                Toast.makeText(AddToCartActivity.this, "Adding to cart...", Toast.LENGTH_SHORT).show();
                addData();
            }
        });

    }


    private void addData() {

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
                            Toast.makeText(AddToCartActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(AddToCartActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddToCartActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> m = new HashMap<>();
                m.put("product_name",name);
                m.put("product_price",mPrice);
                m.put("product_quantity",mQuantity);
                m.put("product_image",image);
                m.put("username",username);
                return m;
            }
        };
        Volley.newRequestQueue(this).add(s);
    }

}
