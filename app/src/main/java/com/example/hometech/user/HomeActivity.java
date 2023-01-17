package com.example.hometech.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hometech.LoginSession;
import com.example.hometech.R;
import com.example.hometech.tech.TechToolList;
import com.example.hometech.user.toolsList.UserToolList;
import com.example.hometech.user.user_buyed_items.UserBuyedItemsActivity;
import com.example.hometech.user_cartList.ListCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_userhome);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_cat,
                R.id.navigation_home, R.id.navigation_technician, R.id.navigation_videos,R.id.navigation_services)
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
                        new LoginSession(HomeActivity.this).clearData();
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
        inflater.inflate(R.menu.menus, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.nearby) {
            Intent intent=new Intent(getApplicationContext(),Map.class);
            startActivity(intent);
            //do your function here
        }
        if (id == R.id.mycart) {
            Intent intent=new Intent(getApplicationContext(), ListCartActivity.class);
            startActivity(intent);
            //do your function here
        }

        if (id == R.id.tools) {
            Intent intent=new Intent(getApplicationContext(), UserToolList.class);
            startActivity(intent);
            //do your function here
        }
        if (id == R.id.buys) {
            Intent intent = new Intent(getApplicationContext(), UserBuyedItemsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
