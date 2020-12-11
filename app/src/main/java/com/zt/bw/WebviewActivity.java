package com.zt.bw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.spinner.litz.GoodsInfo;
import com.zt.bw.util.DialogUtil;
import com.zt.bw.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

public class WebviewActivity extends Activity {
    private WebView mWebView;
    private String url;
    BroadcastReceiver netReceiver =new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type2 = networkInfo.getType();
//                    String typeName = networkInfo.getTypeName();
//                    tv.setText(type2+"--"+typeName);
                    if(networkInfo.isConnected()){
                        mWebView.loadUrl(url);
                    }
//                    switch (type2) {
//                        case 0://移动 网络    2G 3G 4G 都是一样的 实测 mix2s 联通卡
//
//                            break;
//                        case 1: //wifi网络
//                            break;
//
//                        case 9:  //网线连接
//                            break;
//                    }
                } else {// 无网络

                }
            }
        }

    };
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!StartActivity.isBrowserMode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.act_webview);
        url = getIntent().getStringExtra("url");
        findViewById(R.id.iv_setting).setOnClickListener(v -> {
            DialogUtil.createSettingDialog(this,url,data -> {
                url = data;
                SPUtil.putString(this,"url",url);
                mWebView.loadUrl(url);
            });
        });
        initWebView();
        initReceiver();
    }
    /**
     * 注册网络监听的广播
     */
    private void initReceiver() {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(netReceiver, timeFilter);
    }
    @SuppressLint("JavascriptInterface")
    private void initWebView() {
        mWebView = findViewById(R.id.webview);
        //声明WebSettings子类
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setDatabaseEnabled(true);//开启 database storage API 功能
        webSettings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient( new WebViewClient());
//        mWebView.registerHandler("getToken", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                String token= CommonSettingProvider.getStudentId(JSWebViewActivity.this);
//                LogUtil.d("=======================getToken,data:"+data+",token:"+token);
//                if(TextUtils.isEmpty(token)){//登录
//                    finish();
//                    return;
//                }
//                final Map<String,String> map=new HashMap<>();
//                map.put("api_token",token);
////                map.put("activityId","");
//                String tokenString = new Gson().toJson(map);
//                function.onCallBack(tokenString);
//            }
//        });
//        mWebView.registerHandler("setClose", new BridgeHandler() {
//            @Override
//            public void handler(String data, CallBackFunction function) {
//                finish();
//            }
//        });
        if(!TextUtils.isEmpty(url)){
            mWebView.loadUrl(url);
        }
//        mWebView.loadUrl("http://47.111.64.234:9096/dist/index.html");

    }
    public void doClick(View v){
        List<GoodsInfo> list = new ArrayList<>();
        GoodsInfo goods1 = new GoodsInfo();
        goods1.bianhao = 15226;
        goods1.weight = 1;
        goods1.price = "5.00";
        goods1.total = "10.00";
        list.add(goods1);
        GoodsInfo goods2 = new GoodsInfo();
        goods2.bianhao = 15227;
        goods2.weight = 2;
        goods2.price = "6.00";
        goods2.total = "12.00";
        list.add(goods2);
        GoodsInfo goods3 = new GoodsInfo();
        goods3.bianhao = 15228;
        goods3.weight = 3;
        goods3.price = "7.00";
        goods3.total = "21.00";
        list.add(goods3);
        GoodsInfo goods4 = new GoodsInfo();
        goods4.bianhao = 15229;
        goods4.weight = 4;
        goods4.price = "8.00";
        goods4.total = "32.00";
        list.add(goods4);
        String jsonStr = new Gson().toJson(list);;
        Log.d("==================",jsonStr);
        mWebView.loadUrl("javascript:whenTradingList('"+jsonStr+"')");
    }
    public class MyJavascriptInterface {
        private Context context;

        public MyJavascriptInterface(Context context) {
            this.context = context;
        }

        /**
         * 前端代码嵌入js：
         * imageClick 名应和js函数方法名一致
         *
         * @param src 图片的链接
         */
        @JavascriptInterface
        public void imageClick(String src) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netReceiver != null) {
            unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }
}
