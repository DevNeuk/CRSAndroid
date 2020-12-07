package com.example.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crs.R;
import com.example.crs.model.MenuInfo;
import com.example.crs.model.MenuResponseRepo;
import com.example.crs.model.ResponseList;
import com.example.crs.network.Api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuActivity extends Fragment implements View.OnClickListener {

    RecyclerView rly_reservation;
    private MenuResponseRepo response_list;
    private List<MenuInfo> userListResponseData;
    General general;
    TextView txt_nodata;
    ContentLoadingProgressBar pb_loading;
    private SharedPreferences sharedpreferences;
    private View view;
    Context context;
    Button btn_confirm;
    HashMap<Integer,MenuInfo> item_select;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.activity_menu, container, false);
        rly_reservation = view.findViewById(R.id.rly_reservation);
        txt_nodata = view.findViewById(R.id.txt_nodata);
        pb_loading = view.findViewById(R.id.pb_loading);
        btn_confirm = view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        item_select = new HashMap<Integer,MenuInfo>();
        pb_loading.hide();
        context = getActivity();
        general = new General(context);
        getMenuList();
         return view;
    }



    private void getMenuList() {
        pb_loading.show();
        (Api.getClient().getMenuInfo()).
                enqueue(new Callback<MenuResponseRepo>() {
                    @Override
                    public void onResponse(Call<MenuResponseRepo> call, Response<MenuResponseRepo> response) {
                        response_list = response.body();
                        if(response_list.getStatus().equals("1")&&response_list.getData()!=null) {
                            stopProgressbar();
                            userListResponseData = response_list.getData();
                            setMenuList(userListResponseData);
                        }else if(response_list.getData()==null){
                            txt_nodata.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<MenuResponseRepo> call, Throwable t) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Log.e("TAG", "onFailure: "+t.toString() );
                        stopProgressbar();
                        Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void setMenuList(List<MenuInfo> userListResponseData) {
        MenuListAdapter adapter = new MenuListAdapter(userListResponseData,context,item_select);
        rly_reservation.setHasFixedSize(true);
        rly_reservation.setLayoutManager(new LinearLayoutManager(context));
        rly_reservation.setAdapter(adapter);
    }

    private void stopProgressbar() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pb_loading.hide();

            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {

        if(item_select.size()!=0) {
            Collection<MenuInfo> values = item_select.values();

            //Creating an ArrayList of values

            ArrayList<MenuInfo> listOfValues = new ArrayList<MenuInfo>(values);
            Intent in = new Intent(context, OrderConform.class);
            General.order_menu = listOfValues;
            startActivity(in);
        }else{
            Toast.makeText(context,"Please choose atleast on Item",Toast.LENGTH_LONG).show();
        }
    }



}
