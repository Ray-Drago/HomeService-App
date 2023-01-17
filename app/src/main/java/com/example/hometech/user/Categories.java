package com.example.hometech.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hometech.R;
import com.example.hometech.user.List.UserListActivity;
import com.example.hometech.user.Mechanic.MechList;
import com.example.hometech.user.Plumbing.PlumbList;
import com.example.hometech.user.computer.CmpList;
import com.example.hometech.user.electrician.ElectricianList;
import com.example.hometech.user.laundry.LaundryList;

public class Categories extends AppCompatActivity {
ImageView img1,img2,img3,img4,img5,img6;
String menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        img1=findViewById(R.id.construction);
        img2=findViewById(R.id.laundry);
        img4=findViewById(R.id.cmp);
        img3=findViewById(R.id.elect);
        img5=findViewById(R.id.mechanic);
        img6=findViewById(R.id.plumb);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Construction";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Laundry";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Electrical Technician";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Computer Technician";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Mechanic";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu = "Plumbing";
                Intent i = new Intent(getApplicationContext(), UserListActivity.class);
                i.putExtra("menu", menu);
                Toast.makeText(getApplicationContext(),menu, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }
}