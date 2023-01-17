package com.example.hometech.tech;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hometech.R;
import com.example.hometech.user.UserEditProfileActivity;
import com.example.hometech.user.UserSession;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class TechHomeFragment extends Fragment {

    String username, email, phone;
    TextView tvUsername, tvEmail, tvPhone;
    Button btnEdit;
    String pass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_tech_home, container, false);

        tvEmail = root.findViewById(R.id.te);
        tvUsername = root.findViewById(R.id.tu);
        tvPhone = root.findViewById(R.id.tp);
        btnEdit = root.findViewById(R.id.tb);

        HashMap<String, String> m = new TechSession(getActivity()).getUserDetails();
        username = m.get("username");
        email = m.get("emailid");
        phone = m.get("phone");
        pass=m.get("password");


        tvEmail.setText(pass);
        tvPhone.setText(phone);
        tvUsername.setText(username);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TechEditProfileActivity.class));

            }
        });

        return root;
    }

}
