package com.example.hometech.user;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public UserSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("user", 0);
        editor = pref.edit();
    }


    public void createLoginSession(String id, String username, String password,
                                   String email, String phone, String image){

        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("email", email);
        editor.putString("phone", phone);
        editor.putString("image", image);

        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();

        user.put("id", pref.getString("id", null));
        user.put("username", pref.getString("username", null));
        user.put("password", pref.getString("password", null));
        user.put("email", pref.getString("email", null));
        user.put("phone", pref.getString("phone", null));
        user.put("image", pref.getString("image", null));

        return user;
    }

}
