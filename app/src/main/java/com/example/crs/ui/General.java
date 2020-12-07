package com.example.crs.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.crs.model.MenuInfo;

import java.util.ArrayList;

public class General {
    Context context;
    public static final String shared_name = "UserInfo";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";
    public static final String phone_no = "phone";
    public static final String unique_id = "unique_id";
    public static final String role = "role";
    public static ArrayList<MenuInfo> order_menu = new ArrayList<MenuInfo>();

    public General(Context context){
             this.context = context;
    }

    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void nextpage(Class nextClass) {
        Intent in = new Intent(context,nextClass);
        context.startActivity(in);
    }

}
