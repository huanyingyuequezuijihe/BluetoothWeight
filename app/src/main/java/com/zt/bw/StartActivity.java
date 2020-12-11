package com.zt.bw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.zt.bw.util.SPUtil;

public class StartActivity extends Activity {
    public static final boolean isBrowserMode = false;
    EditText etUrl;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isBrowserMode)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.act_start);
        if(isBrowserMode){
            String url = SPUtil.getString(this,"url");
            if(TextUtils.isEmpty(url)){
//                url = "http://47.111.64.234:9096/show/index.html";
//                url = "http://117.80.116.243:8083/show_dh/index.html";
//                url = "http://117.80.116.243:8083/touch/index.html";
                url = "http://222.92.134.202:8083/show_jy/index.html";
            }
            Intent intent = new Intent(this, WebviewActivity.class);
            intent.putExtra("url",url);
//            intent.putExtra("url","http://47.111.64.234:9096/touch/index.html");
            startActivity(intent);
        }else{
            Intent intent2 = new Intent(this,MainActivity.class);
            startActivity(intent2);
        }
        finish();
    }
    public void doClick(View view){
        switch (view.getId()){
            case R.id.tv_left_start:
                String url = etUrl.getText().toString().trim();
                SPUtil.putString(this,"url",url);
                Intent intent = new Intent(this, WebviewActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
                finish();
            break;
            case R.id.tv_right_start:
                Intent intent2 = new Intent(this,MainActivity.class);
                startActivity(intent2);
                finish();
            break;
        }
    }
}
