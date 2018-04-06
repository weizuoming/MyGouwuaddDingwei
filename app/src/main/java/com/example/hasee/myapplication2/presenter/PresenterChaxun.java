package com.example.hasee.myapplication2.presenter;

import com.example.hasee.myapplication2.model.ModelChaxun;
import com.example.hasee.myapplication2.presenter.base.BasePresenter;
import com.example.hasee.myapplication2.presenter.inter.Ipresenter;
import com.example.hasee.myapplication2.view.activity.MainActivity;
import com.example.hasee.myapplication2.view.inter.Imain;

import java.util.Map;

import okhttp3.ResponseBody;

/**
 * Created by hasee on 2018/3/29.
 */

public class PresenterChaxun extends BasePresenter implements Ipresenter {

    private  ModelChaxun modelChaxun;
    private  Imain imain;

    public PresenterChaxun(MainActivity imain) {
        this.imain = imain;
        modelChaxun = new ModelChaxun(this);
    }

    public void getDataFromNet(String url, Map<String, String> map) {
        modelChaxun.getDataFromNet(url,map);
    }

    @Override
    public void unsubcribe() {
        modelChaxun.unsubcribe();
    }

    @Override
    public void onSuccess(ResponseBody responseBody) {
        imain.onSuccess(responseBody);
    }

    @Override
    public void onError(Throwable throwable) {
        imain.onError(throwable);
    }
}
