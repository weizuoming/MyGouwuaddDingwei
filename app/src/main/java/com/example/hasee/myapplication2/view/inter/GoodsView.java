package com.example.hasee.myapplication2.view.inter;

import okhttp3.ResponseBody;

/**
 * Created by hasee on 2018/3/29.
 */

public interface GoodsView {
    void onSuccess(ResponseBody responseBody);
    void onError(Throwable throwable);
}
