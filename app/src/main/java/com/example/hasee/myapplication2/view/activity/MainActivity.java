package com.example.hasee.myapplication2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.example.hasee.myapplication2.R;
import com.example.hasee.myapplication2.bean.ListBean;
import com.example.hasee.myapplication2.presenter.PresenterChaxun;
import com.example.hasee.myapplication2.util.Constant;
import com.example.hasee.myapplication2.view.adapter.MyAdapter;
import com.example.hasee.myapplication2.view.adapter.OnItemClickListener;
import com.example.hasee.myapplication2.view.inter.Imain;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements Imain {

    @BindView(R.id.xrecycer_view)
    XRecyclerView xrecycerView;
    private PresenterChaxun presenterChaxun;
    private int page = 1;
    private List<ListBean.DataBean> data = new ArrayList<>();
    private Map<String, String> map;
    private MyAdapter myAdapter;
    private ListBean listBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        presenterChaxun = new PresenterChaxun(this);
        map = new HashMap<>();
        map.put("keywords","笔记本");
        map.put("page", String.valueOf(page));

        presenterChaxun.attachView(MainActivity.this);
        presenterChaxun.getDataFromNet(Constant.CHAXUN_URL, map);

        xrecycerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

        xrecycerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                       // data.addAll(0,listBean.getData());
                        presenterChaxun.getDataFromNet(Constant.CHAXUN_URL, map);
                        xrecycerView.refreshComplete();
                    }
                },2000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        data.addAll(listBean.getData());
                        presenterChaxun.getDataFromNet(Constant.CHAXUN_URL, map);
                        xrecycerView.refreshComplete();
                    }
                },2000);
            }
        });

    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            listBean = new Gson().fromJson(json, ListBean.class);


           data = listBean.getData();
            Log.e("+++++", listBean.getMsg());
            setAdapter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter() {

        if (myAdapter==null){
            myAdapter=new MyAdapter(MainActivity.this,data);
            xrecycerView.setAdapter(myAdapter);
        }else {
            myAdapter.notifyDataSetChanged();
        }
        //myAdapter = new MyAdapter(MainActivity.this,data);
        myAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                int pid = listBean.getData().get(position).getPid();

                Intent intent = new Intent(MainActivity.this,GoodsActivity.class);
                intent.putExtra("pid",pid);
                startActivity(intent);

                Toast.makeText(MainActivity.this, "点击了"+(position+1)+"个", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onError(Throwable throwable) {
        Log.e("----",throwable.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterChaxun.unsubcribe();//解除订阅

        if (presenterChaxun.isViewAttached()) {//如果产生关联
            //解除关联
            presenterChaxun.detachView();
        }
    }
}
