package com.jx.demo;

import android.app.Application;

import com.jx.loadresultlayout.widget.LoadResultLayoutConfig;


/**
 * @author zhaoxl
 * @date 2018/6/8
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //应用初始化
        LoadResultLayoutConfig.getInstance().setDefaultLoadingLayout(R.layout.defult_loading_layout);
        LoadResultLayoutConfig.getInstance().setDefaultEmptyLayout(R.layout.default_empty_layout);
        LoadResultLayoutConfig.getInstance().setDefaultErrorLayout(R.layout.default_error_layout, R.id.textView);
    }
}
