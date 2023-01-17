package com.example.hometech.user.userShopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hometech.AddToCartActivity;
import com.example.hometech.Config;
import com.example.hometech.ConfirmBuyActivity;
import com.example.hometech.R;
import com.example.hometech.user.UserSession;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ViewProductActivity extends AppCompatActivity {
    TextView prname,prprice, prquantity;
    ImageView ivDisorder;
    String name,price, image,quantity,username;
Button btnBuy,btnCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        HashMap<String, String> user = new UserSession(ViewProductActivity.this).getUserDetails();
        username = user.get("username");

        prname = findViewById(R.id.tvname);
        prprice = findViewById(R.id.tvprice);
        prquantity = findViewById(R.id.tvQuantity);
        ivDisorder = findViewById(R.id.ivDisorder);
        btnBuy = findViewById(R.id.btnBuy);
        btnCart = findViewById(R.id.btnCart);

        Intent i = getIntent();
        name = i.getStringExtra("product_name");
        price = i.getStringExtra("product_price");
        quantity = i.getStringExtra("product_quantity");
        image = i.getStringExtra("product_image");
        Picasso.get().load(image).into(ivDisorder);


        prname.setText(name);
        prprice.setText(price);
        prquantity.setText(quantity);

//        setValues();


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddToCartActivity.class);
                i.putExtra("product_name", name);
                i.putExtra("product_image", image);
                i.putExtra("product_price", price);
                i.putExtra("product_quantity", quantity);
//                i.putExtra("upload",upload);
//                i.putExtra("img",image);
                startActivity(i);
            }
        });


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ConfirmBuyActivity.class);
                i.putExtra("product_price", price);
                i.putExtra("product_name", name);
                i.putExtra("product_image", image);
                i.putExtra("product_quantity", quantity);
                startActivity(i);
            }
        });
    }
}
