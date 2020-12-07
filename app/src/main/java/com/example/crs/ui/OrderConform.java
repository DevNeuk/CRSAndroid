package com.example.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.example.crs.R;
import com.example.crs.model.BookingReq;
import com.example.crs.model.MenuInfo;
import com.example.crs.model.ResponseList;
import com.example.crs.model.StatusResponse;
import com.example.crs.network.Api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderConform extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MenuInfo> menu_order;
    LinearLayout rly_frame;
    TextView txt_itemname,txt_quantity,txt_itemcount,txt_discount,txt_servicecharge,txt_pay;
    Boolean isDiscount = false;
    private ChipCloud chipCloud;
    private String[] activityTitles;
    private String[] selectedchip;
    private boolean isStart,isEnd;
    Button btn_placeorder;
    private SharedPreferences sharedpreferences;
    General general;
    float afterdis = 0;
    String strSelectedDate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_confirm_activity);
//        Intent intent = getIntent();
        //listOfValues = (ArrayList<MenuInfo>)intent.getStringExtra("obj");
        menu_order = General.order_menu;
        activityTitles = getResources().getStringArray(R.array.slot_time);
        General.order_menu = null;
        rly_frame = findViewById(R.id.lay_frame);
        txt_itemcount = findViewById(R.id.txt_itemcount);
        txt_discount = findViewById(R.id.txt_discount);
        txt_servicecharge = findViewById(R.id.txt_servicecharge);
        txt_pay = findViewById(R.id.txt_pay);
        chipCloud = (ChipCloud) findViewById(R.id.chip_cloud);
        btn_placeorder = findViewById(R.id.btn_placeorder);
        btn_placeorder.setOnClickListener(this);
        general = new General(this);
        sharedpreferences = getSharedPreferences(general.shared_name,
                Context.MODE_PRIVATE);

        chipCloud.setChipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                strSelectedDate = activityTitles[index];
                selectedchip = (strSelectedDate).split("-");
                try {
                    String pattern = "hh:mm";
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    Date M_StartTime = sdf.parse("10:00");
                    Date M_EndTime = sdf.parse("11:00");
                    Date A_StartTime = sdf.parse("03:00");
                    Date B_EndTime = sdf.parse("04:00");
                    Date date = Calendar.getInstance().getTime();
//            Date current = sdf.parse(sdf.format(date));
                    Date current_start = sdf.parse(selectedchip[0]);
                    isStart = isTimeAfter(M_StartTime,M_EndTime,A_StartTime,B_EndTime,current_start);
                    Date current_end = sdf.parse(selectedchip[1]);
                    isEnd = isTimeAfter(M_StartTime,M_EndTime,A_StartTime,B_EndTime,current_end);
                    if(isStart&&isEnd){
                        isDiscount = true;
                    }else {
                        isDiscount = false;
                    }
                    addTotalPrice();

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void chipDeselected(int index) {

            }
        });


        for (int i = 0 ;i < menu_order.size() ; i++){
            View child = getLayoutInflater().inflate(R.layout.inflate_ordermenu, null);
            //setMargins(child,5,5,5,5);
            txt_itemname = child.findViewById(R.id.txt_itemname);
            txt_quantity = child.findViewById(R.id.txt_quantity);
            txt_itemname.setText(menu_order.get(i).getItemName());
            txt_quantity.setText(menu_order.get(i).getItemCount()+" x  £"+  menu_order.get(i).getItemPrice());
            rly_frame.addView(child);
        }


        addTotalPrice();
    }

    private void addTotalPrice() {
        float nTotalCount = 0;

        for (int i = 0; i<menu_order.size();i++)
        {
            nTotalCount = (Float.parseFloat(menu_order.get(i).getItemPrice())*(Float.parseFloat(menu_order.get(i).getItemCount()+"")))+nTotalCount;

        }
        txt_discount.setText("0");
        if(isDiscount){
            float roundoff = nTotalCount * ((float) 10 / 100);
            String str_Round = String.format("%2.02f", roundoff);
            txt_discount.setText("£"+str_Round);
            afterdis = nTotalCount - roundoff;
        }else{
            afterdis = nTotalCount;
        }
        txt_itemcount.setText("£"+String.format("%2.02f", nTotalCount));
        txt_servicecharge.setText("£0");
        txt_pay.setText("£"+String.format("%2.02f", afterdis));
    }

    boolean isTimeAfter(Date m_StartTime, Date m_EndTime, Date a_StartTime, Date b_EndTime, Date current) {
        if ((current.before(m_EndTime)&&current.after(m_StartTime)||(current.equals(m_StartTime)||current.equals(m_EndTime)))||
                (current.before(b_EndTime)&&current.after(a_StartTime)||(current.equals(a_StartTime)||current.equals(b_EndTime)))) { //Same way you can check with after() method also.
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onClick(View v) {

        BookingReq req = new BookingReq();
        req.setUniqueId(sharedpreferences.getString(General.unique_id, null));
        req.setTotalCount(menu_order.size()+"");
        req.setBookingStatus("Confirm");
        req.setTotalPrice(afterdis+"");
        req.setBooking_timeslot(strSelectedDate);
        req.setItems(menu_order);

        (Api.getClient().sendBookingInfo(req)).
                enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Log.e("TAG", "onSuccess: "+response.body());
                        Toast.makeText(OrderConform.this,"SuccessFully Booking",Toast.LENGTH_LONG).show();
                        OrderConform.this.finish();
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Log.e("TAG", "onFailure: "+t.toString() );
                    }
                });

    }
}
