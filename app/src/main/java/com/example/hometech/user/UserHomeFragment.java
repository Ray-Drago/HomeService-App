package com.example.hometech.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.hometech.R;

import java.util.HashMap;

public class UserHomeFragment extends Fragment {

    String username, email, phone;
    TextView tvUsername, tvEmail, tvPhone;
    Button btnEdit;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user_home, container, false);

        tvEmail = root.findViewById(R.id.tvEmail);
        tvUsername = root.findViewById(R.id.tvUsername);
        tvPhone = root.findViewById(R.id.tvPhone);
        btnEdit = root.findViewById(R.id.btnEdit);

        HashMap<String, String> m = new UserSession(getActivity()).getUserDetails();
        username = m.get("username");
        email = m.get("email");
        phone = m.get("phone");

        tvEmail.setText(email);
        tvPhone.setText(phone);
        tvUsername.setText(username);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UserEditProfileActivity.class));
            }
        });

        return root;
    }

}