package com.example.hometech.user.user_buyed_items;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hometech.Config;
import com.example.hometech.R;
import com.squareup.picasso.Picasso;
public class Orders extends AppCompatActivity {
    String price,user_name,user_mobile,bdate,btime,user_id,product_name,total,stock,
            quantity,product_id,payment,place,district,landmark,pincode,image,status,loc,dates;
    ImageView img;
    TextView vm_product,vm_qty,vm_price,vm_name,vm_mobile,vm_email,vm_place,vm_district,vm_landmark,vm_pin;
    TextView oview,oconform,opacked,odispatched,oout,odeliver;
    LinearLayout linearLayout1,linearLayout2,linearLayout3,linearLayout4;
    TextView locc,datt,locp,datep,locdis,datedis,locde,datede;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_orders );
        img=findViewById( R.id.image);
        vm_product=findViewById( R.id.vm_product );
        vm_qty=findViewById( R.id.vm_qty );
        vm_price=findViewById( R.id.vm_price);
        vm_name=findViewById( R.id.vm_name);
        vm_place=findViewById( R.id.vm_place);
        vm_district=findViewById( R.id.vm_district );
        locc=findViewById( R.id.loc);
        datt=findViewById( R.id.date);

        locp=findViewById( R.id.locp);
        datep=findViewById( R.id.datep);

        locdis=findViewById( R.id.locd);
        datedis=findViewById( R.id.dated);

        locde=findViewById( R.id.locde);
        datede=findViewById( R.id.datede);



        oconform=findViewById( R.id.oconform );
        opacked=findViewById( R.id.opacked );
        odispatched=findViewById( R.id.odispatched );
        odeliver=findViewById( R.id.odeliver);

        linearLayout1=findViewById( R.id.lconform);
        linearLayout2=findViewById( R.id.lpacked);
        linearLayout3=findViewById( R.id.ldis);
        linearLayout4=findViewById( R.id.ldelivered);




        user_name=getIntent().getStringExtra( "name" );
        bdate=getIntent().getStringExtra( "bdate" );
        btime=getIntent().getStringExtra( "btime" );
        product_name=getIntent().getStringExtra( "prname" );
        quantity=getIntent().getStringExtra( "prqua" );
        price=getIntent().getStringExtra( "price" );
        image=getIntent().getStringExtra( "image" );
        status=getIntent().getStringExtra( "status");
        loc=getIntent().getStringExtra( "location");
        dates=getIntent().getStringExtra( "datestatus");

        Picasso.get().load(image).into( img );
        vm_product.setText( product_name );
        vm_qty.setText( quantity );
        vm_price.setText( price );
        vm_name.setText( user_name );
        vm_place.setText( bdate );
        vm_district.setText( btime );
        locc.setText( loc );
        datt.setText( dates );

        locp.setText( loc );
        datep.setText( dates );

        locdis.setText( loc );
        datedis.setText( dates );

        locde.setText( loc );
        datede.setText( dates );


        if(status.equals("Order Confirmed")){

            oconform.setTextColor(Color.parseColor("#ff0000"));
            linearLayout1.setVisibility(View.VISIBLE);


        }
        if(status.equals( "Order Packed" )){

            opacked.setTextColor(Color.parseColor("#ff0000"));
            linearLayout2.setVisibility(View.VISIBLE);
        }
        if(status.equals( "Order Dispatched" )){

            odispatched.setTextColor(Color.parseColor("#ff0000"));
linearLayout3.setVisibility(View.VISIBLE);
        }

        if(status.equals( "Delivered" )){

            odeliver.setTextColor(Color.parseColor("#ff0000"));
linearLayout4.setVisibility(View.VISIBLE);
        }



    }
}