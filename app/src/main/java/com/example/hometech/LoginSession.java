package com.example.hometech;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginSession {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public LoginSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("login", 0);
        editor = pref.edit();
    }


    public void setUserType(String user) {
        editor.putString("user", user);
        editor.commit();
    }


    public String getUserType() {
        return pref.getString("user", null);
    }

    public void setStatus() {
        editor.putBoolean("status", true);
        editor.commit();
    }

    public boolean getStatus() {
        return pref.getBoolean("status", false);
    }

    public void clearData() {
        editor.clear();
        editor.commit();
    }

}
