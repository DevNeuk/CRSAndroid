package com.example.crs.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crs.R;
import com.example.crs.model.MenuItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewBookinAdapter extends RecyclerView.Adapter<ViewBookinAdapter.ViewHolder> {
    List<MenuItem> orderMenu;
    Context context;
    public ViewBookinAdapter(List<MenuItem> orderMenu, Context context) {
        this.orderMenu = orderMenu;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewBookinAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_viewbooking, parent, false);
        ViewBookinAdapter.ViewHolder myViewHolder = new ViewBookinAdapter.ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookinAdapter.ViewHolder holder, int position) {
        MenuItem menuItem = orderMenu.get(position);
         holder.txt_booking.setText("Booking Id : "+ menuItem.getBooking_id());
         holder.txt_price.setText("Price : £"+menuItem.getTotalPrice());
         holder.txt_count.setText("Total Items : "+menuItem.getTotalCount());
         holder.txt_timeslot.setText("Time : "+menuItem.getBookingTimeslot());
         holder.txt_status.setText("Booking Confirmation : "+menuItem.getBookingStatus());

    }

    @Override
    public int getItemCount() {
        return orderMenu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_booking,txt_count,txt_price,txt_timeslot,txt_status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_booking = itemView.findViewById(R.id.txt_booking);
            txt_status = itemView.findViewById(R.id.txt_status);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_timeslot = itemView.findViewById(R.id.txt_timeslot);
        }
    }
}
