package com.example.hometech.tech.techShopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hometech.Config;
import com.example.hometech.R;
import com.example.hometech.user.userShopping.ProductListModel;
import com.example.hometech.user.userShopping.Product_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShoppingFragment extends Fragment {
   ImageView imageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_shopping, container, false);
   imageView=root.findViewById(R.id.imgss);
//   imageView.setOnClickListener(new View.OnClickListener() {
//       @Override
//       public void onClick(View v) {
//           Intent in = new Intent(getActivity(),TechShoppingList.class);
//           startActivity(in);
//       }
//   });
          new Handler().postDelayed(new Runnable() {
    @Override
    public void run() {
           Intent in = new Intent(getActivity(),TechShoppingList.class);
           startActivity(in);

    }
},1000);

        return root;
    }

}