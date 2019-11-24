package com.jarchie.resizeitem.webview;

import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 17:34:28
 * 邮箱：jarchie520@gmail.com
 * 说明：自定义WebView，设置一些能用的参数
 */
public class BrowserWebView extends WebView {

    public BrowserWebView(Context context) {
        super(context);
        init();
    }

    private void init() {
        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);

        webSettings.setPluginState(WebSettings.PluginState.ON);

        webSettings.setBuiltInZoomControls(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        setInitialScale(1);
    }
}

