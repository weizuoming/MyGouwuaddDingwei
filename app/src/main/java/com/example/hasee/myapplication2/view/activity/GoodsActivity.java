package com.example.hasee.myapplication2.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.hasee.myapplication2.R;
import com.example.hasee.myapplication2.bean.GoodsBean;
import com.example.hasee.myapplication2.presenter.PresenterGoods;
import com.example.hasee.myapplication2.util.Constant;
import com.example.hasee.myapplication2.view.inter.GoodsView;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class GoodsActivity extends AppCompatActivity implements GoodsView, AMapLocationListener {

    @BindView(R.id.banner_goods)
    Banner bannerGoods;
    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.add_card)
    TextView addCard;
    @BindView(R.id.item_map)
    TextView itemMap;
    private PresenterGoods presenterGoods;
    private Map<String, String> map;
    private GoodsBean goodsBean;
    private List<String> list = new ArrayList<>();

    private AMapLocationClient mLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        ButterKnife.bind(this);

//        presenterGoods.attachView(GoodsActivity.this);


        presenterGoods = new PresenterGoods(this);
        Intent intent = getIntent();
        int pid = intent.getIntExtra("pid", 0);
        Log.e("++++----", String.valueOf(pid));
        map = new HashMap<>();
        map.put("pid", String.valueOf(pid));
        presenterGoods.getDataFromNet(Constant.GOODS_URL, map);


    }


    @Override
    public void onSuccess(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            goodsBean = new Gson().fromJson(json, GoodsBean.class);

            String[] split = goodsBean.getData().getImages().split("\\|");
            for (String str : split) {
                list.add(str);
            }
            //设置图片加载器
            bannerGoods.setImageLoader(new GlideImageLoader());
            //设置图片集合
            bannerGoods.setImages(list);
            //设置banner动画效果
            //bannerGoods.setBannerAnimation(Transformer.DepthPage);
            //设置标题集合（当banner样式有显示title时）
            //设置轮播时间
            bannerGoods.setDelayTime(1500);
            //设置指示器位置（当banner模式中有指示器时）
            //设置指示器位置（当banner模式中有指示器时）
            bannerGoods.setIndicatorGravity(BannerConfig.NUM_INDICATOR);
            //banner设置方法全部调用完毕时最后调用
            bannerGoods.start();

            itemTitle.setText(goodsBean.getData().getTitle());
            itemPrice.setText("价格：" + goodsBean.getData().getPrice() + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        Log.e("----", throwable.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenterGoods.unsubcribe();//解除订阅

        if (presenterGoods.isViewAttached()) {//如果产生关联
            //解除关联
            presenterGoods.detachView();
        }
    }


    @OnClick({R.id.item_map, R.id.add_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_map:
                Intent intent = new Intent(GoodsActivity.this,MapActivity.class);
                startActivity(intent);


                //1.初始化定位
                mLocationClient = new AMapLocationClient(this);
                //2.设置定位回调监听
                mLocationClient.setLocationListener(this);


                //2....2给定位客户端对象设置定位参数
                AMapLocationClientOption mLocationOption = new AMapLocationClientOption();

                //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

                //获取一次定位结果：
                mLocationOption.setOnceLocation(true);
                //获取最近3s内精度最高的一次定位结果：
                mLocationOption.setOnceLocationLatest(true);

                mLocationClient.setLocationOption(mLocationOption);
                //3.启动定位
                mLocationClient.startLocation();

                break;
            case R.id.add_card:
                Toast.makeText(GoodsActivity.this, "价格为:" + goodsBean.getData().getPrice(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        //获取定位的结果
        StringBuilder stringBuilder = new StringBuilder();

//        stringBuilder.append("经纬度:"+aMapLocation.getLatitude()+"--"+aMapLocation.getLongitude());
//         stringBuilder.append("地址:"+aMapLocation.getAddress()+"--");
//         stringBuilder.append("地址:"+aMapLocation.getCountry()+"--");
//         stringBuilder.append("地址:"+aMapLocation.getProvince()+"--");
        stringBuilder.append("配送至:"+aMapLocation.getCity()+"--");
        stringBuilder.append(""+aMapLocation.getDistrict()+"--");
        stringBuilder.append(""+aMapLocation.getStreet()+"--");
        stringBuilder.append(""+aMapLocation.getStreetNum()+"");

        itemMap.setText(stringBuilder.toString());
    }
}
