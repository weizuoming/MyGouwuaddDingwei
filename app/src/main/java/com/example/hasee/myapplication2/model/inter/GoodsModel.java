package com.example.hasee.myapplication2.model.inter;

import java.util.Map;

/**
 * Created by hasee on 2018/3/29.
 */

public interface GoodsModel {
    void getDataFromNet(String url, Map<String, String> map);
    void unsubcribe();
}
