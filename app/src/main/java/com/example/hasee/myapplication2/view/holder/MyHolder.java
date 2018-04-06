package com.example.hasee.myapplication2.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.hasee.myapplication2.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hasee on 2018/3/29.
 */

public class MyHolder extends RecyclerView.ViewHolder{

    public SimpleDraweeView simple_view;
    public TextView item_title;
    public TextView item_price;

    public MyHolder(View itemView) {
        super(itemView);

        simple_view = itemView.findViewById(R.id.simple_view);

        item_title = itemView.findViewById(R.id.item_title);

        item_price = itemView.findViewById(R.id.item_price);
    }
}
