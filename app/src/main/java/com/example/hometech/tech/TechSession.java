package com.example.hometech.tech;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class TechSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public TechSession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("tech", 0);
        editor = pref.edit();
    }


    public void createLoginSession(String id, String username, String password,
                                   String email, String phone, String category,String subcat){

        editor.putString("id", id);
        editor.putString("username", username);
        editor.putString("emailid", email);
        editor.putString("password", password);
        editor.putString("phone", phone);
        editor.putString("category", category);
        editor.putString("subcategory", subcat);

        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put("id", pref.getString("id", null));
        user.put("username", pref.getString("username", null));
        user.put("emailid", pref.getString("emailid", null));
        user.put("password", pref.getString("password", null));
        user.put("phone", pref.getString("phone", null));
        user.put("category", pref.getString("category", null));
        user.put("subcategory", pref.getString("subcategory", null));

        return user;
    }

}
