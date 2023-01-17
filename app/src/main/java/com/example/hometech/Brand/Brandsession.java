package com.example.hometech.Brand;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class Brandsession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public Brandsession(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("Brand", 0);
        editor = pref.edit();
    }


    public void createLoginSession(String id, String brandname, String location,
                                   String contactno, String email){

        editor.putString("id", id);
        editor.putString("brandname", brandname);
        editor.putString("location", location);
        editor.putString("contactno", contactno);
        editor.putString("email", email);
//        editor.putString("image", image);

        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();

        user.put("id", pref.getString("id", null));
        user.put("brandname", pref.getString("brandname", null));
        user.put("location", pref.getString("location", null));
        user.put("contactno", pref.getString("contactno", null));
        user.put("email", pref.getString("email", null));
//        user.put("phone", pref.getString("phone", null));
//        user.put("category", pref.getString("category", null));
//        user.put("subcategory", pref.getString("subcategory", null));
//        user.put("image", pref.getString("image", null));

        return user;
    }

}
