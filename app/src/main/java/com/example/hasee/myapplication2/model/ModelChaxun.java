package com.example.hasee.myapplication2.model;

import com.example.hasee.myapplication2.model.inter.BaseModel;
import com.example.hasee.myapplication2.presenter.inter.Ipresenter;
import com.example.hasee.myapplication2.presenter.PresenterChaxun;
import com.example.hasee.myapplication2.util.RetrofitUtil;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by hasee on 2018/3/29.
 */

public class ModelChaxun implements BaseModel {

    private  Ipresenter presenterChaInter;
    private Disposable d;

    public ModelChaxun(PresenterChaxun presenterChaInter) {
        this.presenterChaInter = presenterChaInter;

    }

    public void getDataFromNet(String url, Map<String, String> map) {
        RetrofitUtil.getService().doGet(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ModelChaxun.this.d = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        presenterChaInter.onSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenterChaInter.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unsubcribe() {
        d.dispose();
    }
}
