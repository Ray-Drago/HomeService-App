package com.example.hometech.tech;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hometech.LoginSession;
import com.example.hometech.R;
import com.example.hometech.tech.view_buyed_items.BuyedItemsActivity;
import com.example.hometech.tech_cartList.TechListCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class TechHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tools, R.id.navigation_notifications,R.id.navigation_shopping,R.id.navigation_feed)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public void onBackPressed() {
        showExitAlert();
    }


    private void showExitAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout from your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new LoginSession(TechHomeActivity.this).clearData();
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mmenus, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.booking) {
            Intent intent=new Intent(TechHomeActivity.this,ViewbookingDetails.class);
            startActivity(intent);
            //do your function here
        }
        if (id == R.id.myc) {
            Intent intent=new Intent(TechHomeActivity.this, TechListCartActivity.class);
            startActivity(intent);
            //do your function here
        }
        if (id == R.id.tool) {
            Intent intent=new Intent(TechHomeActivity.this,TechToolList.class);
            startActivity(intent);
            //do your function here
        }
        if (id == R.id.buy) {
            Intent intent=new Intent(TechHomeActivity.this, BuyedItemsActivity.class);
            startActivity(intent);
            //do your function here
        }
        return super.onOptionsItemSelected(item);
    }

}
