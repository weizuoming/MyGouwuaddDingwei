package com.example.hasee.myapplication2.model;

import com.example.hasee.myapplication2.model.inter.GoodsModel;
import com.example.hasee.myapplication2.presenter.PresenterGoods;
import com.example.hasee.myapplication2.presenter.inter.Ipresentergoods;
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

public class ModelGoods implements GoodsModel{
    private  Ipresentergoods ipresentergoods;
    private Disposable d;

    public ModelGoods(PresenterGoods ipresentergoods) {
        this.ipresentergoods = ipresentergoods;
        
    }

    public void getDataFromNet(String url, Map<String, String> map) {
        RetrofitUtil.getService().doGet(url, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        ModelGoods.this.d = d;
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        ipresentergoods.onSuccess(responseBody);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ipresentergoods.onError(e);
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
