package com.example.hasee.myapplication2.view.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.hasee.myapplication2.R;
import com.example.hasee.myapplication2.bean.ListBean;
import com.example.hasee.myapplication2.view.holder.MyHolder;

import java.util.List;

/**
 * Created by hasee on 2018/3/29.
 */

public class MyAdapter extends RecyclerView.Adapter<MyHolder>{
    Context context;
    List<ListBean.DataBean> data;
    private OnItemClickListener mOnItemClickListener;

    public MyAdapter(Context context, List<ListBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_layout, null);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.item_title.setText(data.get(position).getTitle());
        holder.item_price.setText("价格："+data.get(position).getPrice()+"");
        String[] split = data.get(position).getImages().split("\\|");
        Uri uri = Uri.parse(split[0]);
        holder.simple_view.setImageURI(uri);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }

}
