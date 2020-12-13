package com.example.crs.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderConform extends AppCompatActivity implements View.OnClickListener {
    ArrayList<MenuInfo> menu_order;
    LinearLayout rly_frame;
    TextView txt_itemname,txt_quantity,txt_itemtotal,txt_discount,txt_servicecharge,txt_pay;
    Boolean isDiscount = false;
    private ChipCloud chipCloud;
    private String[] activityTitles;
    private String[] selectedchip;
    private boolean isStart,isEnd;
    Button btn_placeorder;
    private SharedPreferences sharedpreferences;
    General general;
    float afterdis = 0;
    String strSelectedDate=null;
    int nItemCount = 0;
    View view;
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
        txt_itemtotal = findViewById(R.id.txt_itemtotal);
        txt_discount = findViewById(R.id.txt_discount);
        txt_servicecharge = findViewById(R.id.txt_servicecharge);
        txt_pay = findViewById(R.id.txt_pay);
        chipCloud = (ChipCloud) findViewById(R.id.chip_cloud);
        btn_placeorder = findViewById(R.id.btn_placeorder);
        btn_placeorder.setOnClickListener(this);
        general = new General(this);
        view = findViewById(R.id.lb_progress);
        sharedpreferences = getSharedPreferences(general.shared_name,
                Context.MODE_PRIVATE);

        changeStatusBarColor();

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
        nItemCount = 0;

        for (int i = 0; i<menu_order.size();i++)
        {
            nTotalCount = (Float.parseFloat(menu_order.get(i).getItemPrice())*(Float.parseFloat(menu_order.get(i).getItemCount()+"")))+nTotalCount;
            nItemCount = menu_order.get(i).getItemCount()+nItemCount;
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
        txt_itemtotal.setText("£"+String.format("%2.02f", nTotalCount));
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
        if(strSelectedDate!=null&&strSelectedDate.length()!=0) {
            BookingReq req = new BookingReq();
            req.setUniqueId(sharedpreferences.getString(General.unique_id, null));
            req.setTotalCount(nItemCount + "");
            req.setBookingStatus("Confirmed");
            req.setTotalPrice(afterdis + "");
            req.setBooking_timeslot(strSelectedDate);
            req.setItems(menu_order);
            view.setVisibility(View.VISIBLE);

            (Api.getClient().sendBookingInfo(req)).
                    enqueue(new Callback<StatusResponse>() {
                        @Override
                        public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                            // if error occurs in network transaction then we can get the error in this method.
                            Log.e("TAG", "onSuccess: " + response.body());
                            view.setVisibility(View.GONE);
                            Toast.makeText(OrderConform.this, "Order SuccessFull", Toast.LENGTH_LONG).show();
//                        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
//                            for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
//                                Fragment mFragment = getSupportFragmentManager().getFragments().get(i);
//                                if (mFragment != null) {
//                                    getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
//                                }
//                            }
//                        }
//                        Intent in = new Intent(OrderConform.this,MainDrawerActivity.class);
//                        in.putExtra("page","0");
//                        startActivity(in);
                            General.isClosed = true;
                            OrderConform.this.finish();
                        }

                        @Override
                        public void onFailure(Call<StatusResponse> call, Throwable t) {
                            // if error occurs in network transaction then we can get the error in this method.
                            view.setVisibility(View.GONE);
                            Toast.makeText(OrderConform.this, t.toString(), Toast.LENGTH_LONG).show();
                            Log.e("TAG", "onFailure: " + t.toString());
                        }
                    });
        }else{

            Toast.makeText(OrderConform.this, "Please select time slot", Toast.LENGTH_LONG).show();
        }

    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
    }

}
