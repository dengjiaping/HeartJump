package com.cucr.myapplication.fragment.star;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.utils.MyLogger;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by 911 on 2017/6/24.
 */
public class Fragment_star_shuju extends Fragment {

    private View view;

    @ViewInject(R.id.wv)
    private WebView wv;

    @ViewInject(R.id.ll_load)
    private LinearLayout ll_load;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //view的复用
        if (view == null) {
            view = inflater.inflate(R.layout.item_other_fans_shuju, container, false);
            ViewUtils.inject(this, view);
            initViews();
        }

        return view;
    }


    private void initViews() {
        WebSettings settings = wv.getSettings();


        //webView  加载视频，下面五行必须
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        settings.setJavaScriptEnabled(true);//支持js
        settings.setPluginState(WebSettings.PluginState.ON);// 支持插件
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片


        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小  无效
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//        wv.setWebChromeClient(new WebChromeClient() );
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                MyLogger.jLog().i("加载完成");
                ll_load.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MyLogger.jLog().i("开始加载");
            }
        });
        wv.loadUrl(HttpContans.HTTP_HOST + HttpContans.ADDRESS_STAR_DATA);
    }
}
