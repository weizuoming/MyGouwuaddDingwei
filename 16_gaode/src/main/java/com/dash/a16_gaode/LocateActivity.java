package com.dash.a16_gaode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

public class LocateActivity extends AppCompatActivity implements AMapLocationListener {

    private AMapLocationClient mLocationClient;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locate);

        textView = findViewById(R.id.text_view);



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

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        //获取定位的结果
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("经纬度:"+amapLocation.getLatitude()+"--"+amapLocation.getLongitude());
        stringBuilder.append("地址:"+amapLocation.getAddress()+"--");
        stringBuilder.append("地址:"+amapLocation.getCountry()+"--");
        stringBuilder.append("地址:"+amapLocation.getProvince()+"--");
        stringBuilder.append("地址:"+amapLocation.getCity()+"--");
        stringBuilder.append("地址:"+amapLocation.getDistrict()+"--");
        stringBuilder.append("地址:"+amapLocation.getStreet()+"--");
        stringBuilder.append("地址:"+amapLocation.getStreetNum()+"--");

        textView.setText(stringBuilder.toString());

    }
}
