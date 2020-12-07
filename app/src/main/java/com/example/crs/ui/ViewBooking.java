package com.example.crs.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.crs.R;
import com.example.crs.model.MenuResponseRepo;
import com.example.crs.model.VIewBookingRes;
import com.example.crs.network.Api;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBooking extends Fragment {

    private View view;
    private RecyclerView rly_reservation;
    Context context;
    private General general;
    private SharedPreferences sharedpreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.activity_reservation, container, false);
        rly_reservation = view.findViewById(R.id.rly_reservation);
        context = getActivity();
        general = new General(getActivity());
        sharedpreferences = context.getSharedPreferences(general.shared_name,
                Context.MODE_PRIVATE);
        getBookingDetails();
        return view;
    }

    public void getBookingDetails(){
            (Api.getClient().getBookingDetails(sharedpreferences.getString(General.unique_id, null),sharedpreferences.getString(General.role, null))).
                    enqueue(new Callback<VIewBookingRes>() {
                        @Override
                        public void onResponse(Call<VIewBookingRes> call, Response<VIewBookingRes> response) {

                            Log.e("TAG", "onFailure: "+response.body() );
                        }

                        @Override
                        public void onFailure(Call<VIewBookingRes> call, Throwable t) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Log.e("TAG", "onFailure: "+t.toString() );
                            Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

    }
}
