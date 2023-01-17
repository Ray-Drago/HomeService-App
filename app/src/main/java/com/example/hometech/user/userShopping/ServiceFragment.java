package com.example.hometech.user.userShopping;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hometech.R;
import com.example.hometech.user.toolsList.ToolsDataModel;

import java.util.List;

public class ServiceFragment extends Fragment {


    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_service, container, false);
        imageView = root.findViewById(R.id.onshop);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getActivity(),userShoppingList.class);
                startActivity(intent);

            }
        },1000);
        return root;


    }
}
