package com.example.crs.ui;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crs.R;
import com.example.crs.model.MenuInfo;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MenuListAdapter extends RecyclerView.Adapter<MenuListAdapter.ViewHolder> {
    List<MenuInfo> menulist;
    private Context context;
    HashMap<Integer, MenuInfo> item_select;

    public MenuListAdapter(List<MenuInfo> userListResponseData, Context context, HashMap<Integer, MenuInfo> item_select) {
        menulist = userListResponseData;
        this.context = context;
        this.item_select = item_select;
    }

    @NonNull
    @Override
    public MenuListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_inflateview, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuListAdapter.ViewHolder holder, int position) {

        final MenuInfo menuInfo = menulist.get(position);
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier("img_" + menuInfo.getId(), "drawable",
                context.getPackageName());
        holder.img_menu.setImageDrawable(resources.getDrawable(resourceId, null));
        //holder.img_menu.setBackgroundResource(R.drawable.img_1);
        holder.txt_itemname.setText(menuInfo.getItemName());
        holder.txt_itemdescription.setText(menuInfo.getItemDescription());
        holder.txt_itemprice.setText("£"+menuInfo.getItemPrice());
        holder.txt_itemcalories.setText(menuInfo.getItemCalories());

        if(menuInfo.getItemCount()>0){
            holder.txt_count.setText(String.valueOf(menuInfo.getItemCount()));
        }else{
            holder.txt_count.setText("0");
        }

        holder.img_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inc(true, holder.txt_count, holder.img_dec, menuInfo);
            }
        });

        holder.img_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inc(false, holder.txt_count, holder.img_dec, menuInfo);
            }
        });
    }

    private void inc(boolean isIncrease, TextView txt_count, ImageView dec_img, MenuInfo menuInfo) {
        int y = Integer.parseInt(txt_count.getText().toString());
        if (isIncrease) {
            y++;
            txt_count.setText(String.valueOf(y));
            menuInfo.setItemCount(y);
            item_select.put(menuInfo.getId(),menuInfo);
            if (!dec_img.isEnabled()) {
                dec_img.setEnabled(true);
            }
        } else {
            y--;
            if (y == 0) {
                dec_img.setEnabled(false);
                item_select.remove(menuInfo.getId());
            }
            txt_count.setText(String.valueOf(y));
            menuInfo.setItemCount(y);

        }
    }

    @Override
    public int getItemCount() {
        return menulist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_menu, img_dec, img_inc;
        TextView txt_itemname, txt_itemdescription, txt_itemprice, txt_itemcalories, txt_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_menu = itemView.findViewById(R.id.img_menu);
            img_inc = itemView.findViewById(R.id.img_inc);
            img_dec = itemView.findViewById(R.id.img_dec);
            txt_itemname = itemView.findViewById(R.id.txt_itemname);
            txt_itemdescription = itemView.findViewById(R.id.txt_itemdescription);
            txt_itemprice = itemView.findViewById(R.id.txt_itemprice);
            txt_itemcalories = itemView.findViewById(R.id.txt_itemcalories);
            txt_count = itemView.findViewById(R.id.txt_count);
        }
    }

}
