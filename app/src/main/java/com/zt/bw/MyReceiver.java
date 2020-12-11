package com.zt.bw;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            return;
        }
        Log.d("======开机", intent.getAction());
//        if (!isRun(context, "android.intent.action.BOOT_COMPLETED")) {
            intent.setClass(context, StartActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//        }
    }

    /**
     * 判断应用是否在运行
     *
     * @param context
     * @return
     */
    public boolean isRun(Context context, String packagename) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packagename) || info.baseActivity.getPackageName().equals(packagename)) {
                isAppRunning = true;
                Log.d("======", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }
        Log.d("==========", "com.ad 程序  ...isAppRunning......" + isAppRunning);
        return isAppRunning;
    }
}