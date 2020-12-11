package com.zt.bw;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spinner.litz.BlueToothDeviceAdapter;
import com.spinner.litz.BluetoothManager1;
import com.spinner.litz.GoodsAdapter;
import com.spinner.litz.GoodsInfo;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zt.bw.bean.GoodsInfoBean;
import com.zt.bw.bean.TicketBean;
import com.zt.bw.util.DialogUtil;
import com.zt.bw.util.SPUtil;
import com.zt.bw.widget.LoadingDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Request;
import ye.fang.tool.Byte_Tool;
import ye.fang.tool.TimeDemo;
import ye.fang.tool.Times;
import ye.fang.tool.ToastUtils;

public class MainActivity extends Activity implements View.OnClickListener {
    private boolean webviewMode = true;
    private String webviewUrl;
    //    private final String webviewUrl = "http://192.168.0.126:8080/";
    private WebView mWebView;
    private View connectLayout;
    private BluetoothAdapter bTAdatper;
    private ListView listView;
    private ArrayAdapter adapter;
    private TextView text_state;
    private TextView tvSearch;
    private boolean connecting;
    private boolean reconnect;
    private TextView text_msg, mh_tv;
    private final int BUFFER_SIZE = 1024;
    private static final UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ConnectThread connectThread;
    private Times timer;
    private Dialog dialog;
    //    private ListenerThread listenerThread;
    String[] time;
    ArrayList<byte[]> av = new ArrayList<byte[]>();
    private boolean isFinish;
    private Thread sendThread;
    private BluetoothDevice device;
  //  private Timer timer1;
    private TimerTask task;

    /* private class MyHandler extends Handler {
         public void handleMessage(Message msg) {
             super.handleMessage(msg);
             if (msg.what == 1) {
                 String message=  msg.obj.toString();
                 if(ui.Inf(message)){
                     //ui.action(message);
                     //final String ss= "DWL\tINF\t\r\nINF\t忱晖生鲜超市\t6号秤\t6\tADS-30E CC006 V1.25(连锁版)D\t1\t6\t00350031-34364713-32373838\t\r\nEND\tINF\t\r\n";
                     final String ss ="DWL\tINF\t\r\nINF\t"+dtmt.Search_Data(0)+"\t"+dtmt.Search_Data(1)+"\t"+dt.Search_Data(40)+"\tADS-30上得利(测试版)\t1\t6\t"+MC+"\t"+search_shop_num()+"\t\r\nEND\tINF\t\r\n";
                     System.out.println("inf格式：    "+ss);
                     new Thread(new Runnable() {
                         public void run() {

                             try {
                                 outputStream = socket.getOutputStream();
                                 //SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");    //设置日期格式
                                 outputStream.write(ss.getBytes("gb2312"));
                                 outputStream.flush();
                             }catch (IOException e) {
                                 e.printStackTrace();
                             }
                         }
                     }).start();
                 }

 //  if(message.contains("DWL\tREP")){
 //  System.out.println("DWL\tREP  --------------------- ");
 //  }


                 if(socket_wait_bluetooth){
                     if(DWL_PLU.Inf(message)){
                         dp.action(message);
 //av.add(goods((Integer.parseInt(dp.s[4]))+"",dp.s[7],Double_Number.Nettodouble(dp.s[8]),dp.s[18]));
                         av.add(goods(Integer.parseInt(dp.s[4]),Integer.parseInt(dp.s[5]),dp.s[6],dp.s[7],Double_Number.Nettodouble(dp.s[8]),dp.s[18]));
 //IntToButeArray.longToButeArray(123456789097l);
                         good_massege(av);
                         pluMsg=message;
 //dp.Cheng_Data();
 //good_massege(av);
                         av.clear();
 //g_index=0;
                     }

                     if(DWL_TIM.Inf(message)){
                         dti.action(message);
                     }



                     if(DWL_TMT.Inf(message)){
                         dtmt.action(message);
                         connectThread.sendbyte(Byte_Tool.paperText(dtmt.s[5],Integer.parseInt(dtmt.s[4])));//以下代码只能等称成功后才能下发另一条
                         System.out.println("dtmt.s[4]  "+dtmt.s[4]+"      dtmt.s[5]     "+dtmt.s[5]);
                         ydTmt=message;
                     }

                     if(DWL_TMS.Inf(message)){
                         dt.action(message);
                         connectThread.sendbyte(Byte_Tool.systemValue(dt.s[5],Integer.parseInt(dt.s[4])));//以下代码只能等称成功后才能下发另一条
                         ydTms=message;
                     }



                     if(DWL_SCP.Inf(message)){
                         ds.action(message);
                         sentmassege(message,"SCP");
                     }

                     if(VIP.Inf(message)){
                         vip.action(message);
                         vipVulue=vip.value;
                         System.out.println("收到服务端发送的会员信息"+"    vip.value[9]   "+ vip.value[9]+"  meber_card   "+meber_card+""+"");
                         connectThread.sendbyte(Byte_Tool.Merber(meber_card, vip.value[9], Double_Number.Nettodouble(vip.value[10]),(int)Double_Number.Nettodouble(vip.value[7])));

                         tv_m_name.setText(vip.value[9]);
                         tv_m_balance.setText(Double_Number.Nettodouble(vip.value[10])+"元");
                         tv_m_jifen.setText(Double_Number.Nettodouble(vip.value[7])+"积分");

                         View_Height_Set(merber_ll,(int) (height*0.14));

                         LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)goods_list.getLayoutParams();//获取当前控件的布局对象
                         params1.height=(int) (height*0.48);//设置当前控件布局的高度 /20*12
                         goods_list.setLayoutParams(params1);

 //        		System.out.println("vip.s[9]  "+vip.s[9]);
 //        		System.out.println("Double_Number.Nettodouble(vip.s[10])  "+Double_Number.Nettodouble(vip.s[10]));
 //        		System.out.println("(int)Double_Number.Nettodouble(vip.s[7]))  "+Double_Number.Nettodouble(vip.s[7]));
                     }


                     if(DWL_SAL.Inf(message)){

                     }

                     if(DWL_ORP.Inf(message)){
                         dorp.action(message);
                     }

                     if(DWL_REP.Inf(message)){
                         drep.action(message);
                     }
                 }
             }
         }
     }*/

 /*   class timerThread extends Thread {
        public boolean bStop=false;         // 通过给bStop设置true而停止定时操作
        @Override
        public void run() {
            while(! bStop)
            {
                try{
                    Thread.sleep(1000);//每隔1s执行一次
                    Message msg = new Message();
                    msg.what= 1;
                    handler.sendMessage(msg);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    // 移除所有的msg.what为0等消息，保证只有一个循环消息队列再跑
                    handler.removeMessages(0);
                    // app的功能逻辑处理
                    tvSearch.setText("搜索中");
                    adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
                    listView.setAdapter(adapter);
                    searchDevices();
                    // 再次发出msg，循环更新
                    handler.sendEmptyMessageDelayed(0, 10000);
                    break;

                case 1:
                    // 直接移除，定时器停止
                    handler.removeMessages(0);
                    break;

                default:
                    break;
            }
        };
    };

    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        setFullScreen();
        setContentView(R.layout.act_main);
        webviewUrl = SPUtil.getString(this, "url2");
        if (TextUtils.isEmpty(webviewUrl)) {
//            webviewUrl = "http://47.111.64.234:9096/dist/index.html";
//            webviewUrl = "http://117.80.116.243:8083/show/index.html";
            webviewUrl = "http://222.92.134.202:8083/show_jy/index.html";
        }
//        if (bhh == null) {
//            bhh = new bhHandle();//编码倒计时
//        }
        initReceiver();
//        sbyte = new Byte_Tool(this);
        TextView time = (TextView) findViewById(R.id.time);
        timer = new Times(time);
        bTAdatper = BluetoothAdapter.getDefaultAdapter();
        initView();
//        listenerThread = new ListenerThread();
//        listenerThread.start();
        initWebView();
        initPermission();

        //创建一个定时器对象
     /*   timer1 = new Timer();
        task = new TimerTask() {
            //创建定时器任务对象，必须实现run方法，在该方法中定义用户任务
            @Override
            public void run() {
                try {
                    tvSearch.setText("搜索中");
                    adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
                    listView.setAdapter(adapter);
                    searchDevices();
                    //希望定时器做什么，在此实现，
                    //可以使用一个Handler对象，将定时消息传递到界面线程
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };*/
        // 再次发出msg，循环更新
        handler.sendEmptyMessageDelayed(0, 10000);

    }

    private void addData(byte[] data) {
        synchronized (sendThread) {
            if (data == null && sendThread != null) {
                sendThread.notify();
                return;
            }
            Log.i("==============addData:", Byte_Tool.toHexString(data));
            av.add(data);
        }
    }

    private void removeData() {
        synchronized (sendThread) {
            av.remove(0);
        }
    }

    private void initPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE, Permission.ACCESS_FINE_LOCATION)
                .onGranted(permissions -> searchDevices())
                .onDenied(permissions -> {
                    // Storage permission are not allowed.
                    String msg = "";
                    if (permissions.contains(Permission.Group.STORAGE[0]) || permissions.contains(Permission.Group.STORAGE[1])) {
                        msg = "缺少储存卡读写权限,立即去授权?";
                        ToastUtils.showToast(this, msg);
                    }
                })
                .start();
    }

    @Override
    protected void onResume() {
        if (!BluetoothManager1.isBluetoothEnabled()) {
//            BluetoothManager1.turnOnBluetooth();
//            search_BlueTooth();
            openBlueTooth();
        }
        super.onResume();
    }

    protected void setFullScreen() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.INVISIBLE);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_openBT:
                openBlueTooth();
                break;
            case R.id.btn_setting:
                DialogUtil.createSettingDialog(this, webviewUrl, data -> {
                    webviewUrl = data;
                    SPUtil.putString(this, "url2", webviewUrl);
                });
                break;
            case R.id.btn_search:
            case R.id.btn_search2:
                if ("搜索".equals(tvSearch.getText().toString())) {
                    tvSearch.setText("搜索中");
                    adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
                    listView.setAdapter(adapter);
                    searchDevices();
                }
                break;
            case R.id.load_mh:
//                loadMH();
                break;
            case R.id.settime_mh:
                String s1 = TimeDemo.get_Time();
                time = s1.split("/");
                addData(Byte_Tool.setTime(time[0], time[1], time[2], time[3], time[4], time[5]));
                break;
            case R.id.back:
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bt_boolean = false;
                finish();
                break;
//case R.id.print:
//String weiyima=sbyte.gotWYM(true,0);
//connectThread.sendbyte(Byte_Tool.print_code(weiyima));
//break;

        }
    }

    //    public static Byte_Tool sbyte;
    //退出本APP
    private long mExitTime;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                reconnect = false;
                try {
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bt_boolean = false;
                onDestroy();
                System.gc();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


//    BluetoothDevice device;

    //    public void search_BlueTooth() {
//        bTAdatper = BluetoothAdapter.getDefaultAdapter();
//        if (bTAdatper.isEnabled()) {//蓝牙关闭不会报错
//            try {
//                adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
//                listView.setAdapter(adapter);
//                //initReceiver();
//                //registerBoradcastReceiver();
//                adapter.clear();
//                if (bTAdatper.isDiscovering()) {
//                    bTAdatper.cancelDiscovery();
//                }
//                // openBlueTooth();
//                //获取已经配对过的设备
//                Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
//                //将其添加到设备列表中
//                if (pairedDevices.size() > 0) {
//                    for (BluetoothDevice device : pairedDevices) {
//                        adapter.add(device);
//                    }
//                }
//                bTAdatper.startDiscovery();
//                device = (BluetoothDevice) adapter.getItem(0);
//                connectDevice(device);
//
//                //  listenerThread = new ListenerThread();
//                //   listenerThread.start();
//            } catch (Exception e) {
//
//                ToastUtils.showToast(this, "蓝牙配置错误");
//                adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
//                listView.setAdapter(adapter);
//                bTAdatper = BluetoothAdapter.getDefaultAdapter();
//                //initReceiver();
//                //registerBoradcastReceiver();
//                adapter.clear();
//                if (bTAdatper.isDiscovering()) {
//                    bTAdatper.cancelDiscovery();
//                }
//                // openBlueTooth();
//                //获取已经配对过的设备
//                Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
//                //将其添加到设备列表中
//                if (pairedDevices.size() > 0) {
//                    for (BluetoothDevice device : pairedDevices) {
//                        adapter.add(device);
//                    }
//                }
//
//                bTAdatper.startDiscovery();
////        if(device!=null){
//                device = (BluetoothDevice) adapter.getItem(0);
//                connectDevice(device);
//            }
//        } else
//            Toast.makeText(this, "蓝牙已经关闭，请到设置开启蓝牙并和秤配对", Toast.LENGTH_SHORT).show();
//    }
    public static String getAppVersion() {
        PackageInfo pi = null;
        try {
            PackageManager pm = MyApp.getInstance().getPackageManager();
            pi = pm.getPackageInfo(MyApp.getInstance().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initView() {
        findViewById(R.id.btn_openBT).setOnClickListener(this);
        findViewById(R.id.btn_setting).setOnClickListener(this);
        findViewById(R.id.btn_search).setOnClickListener(this);
        ((TextView) findViewById(R.id.version)).setText("版本号：" + getAppVersion());
        connectLayout = findViewById(R.id.connect_layout);
        tvSearch = findViewById(R.id.btn_search2);
        tvSearch.setOnClickListener(this);
        //findViewById(R.id.print).setOnClickListener(this);
//        findViewById(R.id.load).setOnClickListener(this);
//        findViewById(R.id.settime).setOnClickListener(this);
        findViewById(R.id.load_mh).setOnClickListener(this);
        findViewById(R.id.settime_mh).setOnClickListener(this);
//        findViewById(R.id.back).setOnClickListener(this);
        text_state = (TextView) findViewById(R.id.text_state);
        text_msg = (TextView) findViewById(R.id.text_msg);
        mh_tv = (TextView) findViewById(R.id.mh_tv);
        listView = (ListView) findViewById(R.id.blue_tooth_list);
        adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter != null && adapter instanceof GoodsAdapter) {
                    return;
                }
                if (bTAdatper.isDiscovering()) {
                    bTAdatper.cancelDiscovery();
                }
                device = (BluetoothDevice) adapter.getItem(position);
                //连接设备
                connectDevice();
            }
        });
    }


//    public int num1 = 6;


//    String name[] = {"莆田枇杷", "莆田龙眼", "莆田柚子", "莆田荔枝", "香蕉", "花生"};


    //    public void goodsUpload(int a, String s) {
//        av.add(Byte_Tool.goodsLK(a, 5.0, s));
//        connectThread.sendbyte(Byte_Tool.goodMassegeLK(av));
//        av.clear();
//    }
//
//    public void goodsUpload() {
////        av.add(Byte_Tool.goods(a, a, a + "", 255 + "", 5.0, s));
//        if (connectThread == null || !connectThread.isAlive() || connectThread.isInterrupted()) {
//            return;
//        }
    byte[] sendData = Byte_Tool.good_massege(av);

    //        Log.d("=========", "发送数据:" + Byte_Tool.toHexString(sendData));
//        connectThread.sendbyte(sendData);
//        av.clear();
//    }
    public void clearPLU() {
//        av.add(Byte_Tool.goods(a, a, a + "", 255 + "", 5.0, s));
        if (connectThread == null || !connectThread.isAlive() || connectThread.isInterrupted()) {
            return;
        }
        byte[] clearPLU = {(byte) 0xFF, (byte) 0x04, (byte) 0xA6, (byte) 0x5D};
        Log.d("=========", "清空PLU");
        connectThread.sendbyte(clearPLU, 20000);
    }

//    ThreadMH tmh;
//
//    public void loadMH() {//这个线程设计很合理，能达到工业要求
//        num1 = 6;
//        if (tmh == null) {//当线程还没结束时候，不能开启新的线程，只能再原来的线程中重新倒计时
//            tmh = new ThreadMH();
//            tmh.start();
//        }
//    }


//    public class ThreadMH extends Thread {
//        public void run() {
//            super.run();
//            //while (!Thread.interrupted()){
//            while (num1 >= 1) {
//                try {
//
//                    goodsUploadMH(num1, name[--num1]);
//                    //System.out.println("查找PLU倒计时"+num1);//+"PRINT_RIGHTNOW:   "+PRINT_RIGHTNOW
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            //线程结束后重启线程的办法
//            if (num1 < 1) {
//                if (tmh != null) {
//                    tmh.interrupt();//线程中断
//                    tmh = null;
//                    num1 = 6;
//
//                    //System.out.println("下发完成");
//                    Message handMsg4 = new Message();
//                    handMsg4.obj = "";
//                    handMsg4.what = 1;
//                    mHandler.sendMessage(handMsg4);
//                }
//            }
//        }
//    }


//    public Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    ToastUtils.showToast(MainActivity.this, "下发完成");
//                    break;
//                case 2:
//                    String[] data = (String[]) msg.obj;
//                    int index = Integer.parseInt(data[0]);
//                    String name = data[0];
//
//                    if (index > 0 && index <= MainActivity.this.name.length) {
//                        name = MainActivity.this.name[index - 1];
//                    }
//                    mh_tv.setText("商品编号：" + name + "  重量：" + data[1] + "  单价：" + data[2] + "  总价：" + data[3]);
//                    break;
//
//            }
//        }
//    };

    private void initReceiver() {
        //注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        //unregisterReceiver(mReceiver);   //注销广播
/*

        //启动AlarmManager
        Intent intent =new Intent(ACTION_NAME);
        PendingIntent sender=PendingIntent.getBroadcast(this, 0, intent, 0);

        longfirstime= SystemClock.elapsedRealtime();

        AlarmManager am=(AlarmManager)getSystemService(ALARM_SERVICE);


        //5秒周期的发送广播
        am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstime,1000, sender);
*/

    }

    /**
     * 开启蓝牙
     */
    private void openBlueTooth() {

        if (bTAdatper == null) {
            new AlertDialog.Builder(this)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            finish();
                        }
                    }).setTitle("错误")
                    .setMessage("当前设备不支持蓝牙功能")
                    .create().show();
        }
        if (!bTAdatper.isEnabled()) {
           /* Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);*/
            bTAdatper.enable();
        }
        //开启被其它蓝牙设备发现的功能
        if (bTAdatper.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            //设置为一直开启
            i.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
            startActivity(i);
        }
    }

    /**
     * 搜索蓝牙设备
     */
    private void searchDevices() {
        if (bTAdatper.isDiscovering()) {
            bTAdatper.cancelDiscovery();
        }
//        getBoundedDevices();
        bTAdatper.startDiscovery();
    }

    /**
     * 获取已经配对过的设备
     */
    private void getBoundedDevices() {
        //获取已经配对过的设备
        Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
        //将其添加到设备列表中
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                adapter.add(device);
            }
        }
    }


    @Override
    protected void onDestroy() {
        isFinish = true;
        super.onDestroy();
        //取消搜索
        if (bTAdatper != null && bTAdatper.isDiscovering()) {
            bTAdatper.cancelDiscovery();
        }
        //注销BroadcastReceiver，防止资源泄露
        unregisterReceiver(mReceiver);


    //    timer1.cancel();                                   // 该命用于以销毁定时器，一般可以在onStop里面调用
        // 直接移除，定时器停止
        handler.removeMessages(0);

    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d("==========", "found,name:" + device.getName() + ",mac:" + device.getAddress());
                //避免重复添加已经绑定过的设备
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                String curName = device.getName();
                if (curName == null) {
                    return;
                }
                String btName = SPUtil.getString(MainActivity.this, "btName");
                adapter.add(device);
                adapter.notifyDataSetChanged();
                if (btName != null && btName.equals(curName) && device.getBondState() == BluetoothDevice.BOND_BONDED) {
                    MainActivity.this.device = device;
                    connectDevice();
                    bTAdatper.cancelDiscovery();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Toast.makeText(MainActivity.this, "开始搜索", Toast.LENGTH_SHORT).show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                tvSearch.setText("搜索");
                Toast.makeText(MainActivity.this, "搜索完毕", Toast.LENGTH_SHORT).show();
            }
        }
    };


    //int rf_length=-1;
//    String[] fr = new String[52];
    String s = "";
    //    String d183 = "";
//    String weight = "0.00";
//    String prize = "0.00";
//    String total = "0.00";
    int goods_num = 0;
    boolean goods_num_bl = false;
//    public static ArrayList<ArrayList<String>> ds2 = new ArrayList<ArrayList<String>>();
//    ArrayList<String> ds1 = new ArrayList<String>();

    public static final String NOTMERBER = "0356b5";
    public static final String accumulate = "0356b4";
    boolean bt_boolean = true;
    BluetoothSocket socket;

    /**
     * 连接蓝牙设备
     */
    private void connectDevice() {
        Log.d("=============", "开始连接...");
        try {
            if (socket != null && socket.isConnected() || connecting) {
                return;
            }
         //   timer1.cancel();
            // 直接移除，定时器停止
            handler.removeMessages(0);

            connecting = true;
            reconnect = true;
            //创建Socket
            socket = device.createRfcommSocketToServiceRecord(BT_UUID);
            //启动连接线程
            connectThread = new ConnectThread(socket, device.getName(), bt_boolean);
            connectThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String base = "27a4";

    /**
     * 连接线程
     */
    private class ConnectThread extends Thread {
        private BluetoothSocket socket;
        private boolean activeConnect;
        private String name;
        InputStream inputStream;
        OutputStream outputStream;

        private ConnectThread(BluetoothSocket socket, String name, boolean connect) {
            this.socket = socket;
            this.name = name;
            this.activeConnect = connect;
        }

        public void run() {
            try {
                //如果是自动连接 则调用连接方法
                if (activeConnect) {
                    socket.connect();
                }
                text_state.post(new Runnable() {
                    public void run() {
                        connecting = false;
                        tvSearch.setText("已连接");
                        ToastUtils.showToast(MainActivity.this, "连接成功");
                        SPUtil.putString(MainActivity.this, "btName", name);
                        if (webviewMode) {
                            timer.stop();
                            connectLayout.setVisibility(View.GONE);
                            mWebView.setVisibility(View.VISIBLE);
                            mWebView.loadUrl(webviewUrl);
                            MyApp.getInstance().getExecutor().submit(new Runnable() {
                                @Override
                                public void run() {
                                    while (!isFinish) {
                                        sendThread = Thread.currentThread();
                                        sendbyte();
                                    }
                                }
                            });
                        }
                    }
                });

                Log.d("=============", "ConnectThread connected!");
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytes;
                while (!isFinish) {
                    //读取数据
//                    while(inputStream.available() <= 4){//数据不完整，继续等待数据
////                        Log.d("=============","available:"+inputStream.available());
//                    }
                    bytes = inputStream.read(buffer);
                    if (bytes > 0) {
                        final byte[] data = new byte[bytes];
                        System.arraycopy(buffer, 0, data, 0, bytes);
//                        text_msg.post(new Runnable() {
//
//                            public void run() {
//
//                                text_msg.setText("数据:" + Shiliu_Detail.byte2hex(data));
                        try {
                            s += Byte_Tool.toHexString1(data);
                            System.out.println("=========收到数据:   " + s);
                            if (s.contains("d183") & !goods_num_bl) {
                                Log.i(" =============追加商品    ", s);
                                goods_num++;
                                goods_num_bl = true;
                            }
                            final int weight_thengh = 0;
                            String moren = "";
                            //没接收新PLU信息的时候
                            //good_insert=false;
                            //获取重量单价合计商品等信息
//                            Log.d("=============", "收到数据:" + s);
                            if (s.length() == 78 & s.contains(base) & !s.contains("0262b5") & !s.contains("2408") & !s.equals("04b348") & !s.equals("0146b8")) {
//                                Log.d("=============", "商品信息显示:" + s);
//                                int aa = s.indexOf(base);
                                //System.out.println("重量：  "+s.substring(aa+12+weight_thengh,aa+18+weight_thengh));
//                                moren = s;
//                                if (Byte_Tool.jiaoyan(moren)) {
////	        message_demo(Integer.valueOf(moren.substring(aa+4+weight_thengh, aa+6+weight_thengh),16)+"",8);
////			int unit=Integer.valueOf(moren.substring(aa+6+weight_thengh, aa+8+weight_thengh),16);
////			System.out.println("重量单位"+unit);
////			message_demo(Integer.valueOf(moren.substring(aa+6+weight_thengh, aa+8+weight_thengh),16)+"",25);
//                                    weight = moren.substring(aa + 12 + weight_thengh, aa + 18 + weight_thengh);
//                                    weight = Integer.valueOf(weight, 16) + "";
//                                    //weight=Integer.parseInt( weight.substring(0, 2), 16)*65536+Integer.valueOf(weight.substring(2, 4), 16)*256+Integer.valueOf(weight.substring(4, 6), 16)+"";
//                                    weight = Double_Number.data_prize("3", Double.parseDouble(weight) / 1000 + "");
//                                    //System.out.println("weight  "+weight);
//                                    //物品重量传给handle
//                                    String[] data1 = new String[4];
//                                    int goodIndex = Integer.parseInt(moren.substring(aa + 4, aa + 6), 16);
//                                    data1[0] = goodIndex + "";
//                                    data1[1] = weight;
//                                    int perPrice = Integer.parseInt(moren.substring(aa + 18, aa + 24), 16);
//                                    data1[2] = Double_Number.data_prize("2", Double.parseDouble(perPrice + "") / 100 + "");
//                                    int totalPrice = Integer.parseInt(moren.substring(aa + 24, aa + 30), 16);
//                                    data1[3] = Double_Number.data_prize("2", Double.parseDouble(totalPrice + "") / 100 + "");
//                                    message_demo(data1, 2);
//
//                                }
                                s = "";
                                moren = "";
                            } else if ((s.length() > 78 & s.contains(base))) {
                                s = "";
                            } else if ((s.length() > 3 & s.contains("ff04a2"))) {//心跳
                                s = "";
//                                connectThread.sendbyte(Byte_Tool.heart());
                            } else if (s.length() == 1708 & s.contains(accumulate)) {//1620
                                //以下是累积代码   0136b4
                                if (Byte_Tool.jiaoyan(s)) { //校验异或
                                    Log.i(NOTMERBER + "=============", "累积成功");
                                    parseLeiji(s, false);
                                }
                                s = "";
                            } else if (s.length() == 1708 && s.contains(NOTMERBER)) {
                                //非会员卡交易,扫描长度不够1580左右。
                                Log.i(NOTMERBER + " : ", "非会员卡交易");//00现金支付,91微信支付92支付宝支付93会员卡支付94市民卡支付95银联支付
//                                bmt = new BM_thread();//发送朔源码倒计时0.5秒
//                                bmt.start();
                                if (Byte_Tool.jiaoyan(s)) {
                                    //校验异或
//                                    addData(Byte_Tool.Paper_Text01(s.substring(1682, 1706), 9));
                                    parseLeiji(s, true);
//                                    uploadServer(s);
                                }
                                s = "";
                            } else if (s.length() == 10 & s.contains("ff05c7")) {//设置文本参数
                                s = "";
                                removeData();
                                addData(null);
                            } else if (s.length() == 20 & (s.contains("0acb") | s.contains("0ac9"))) {//设置系统参数
                                s = "";
                                removeData();
                                addData(null);
                            } else if (s.length() == 8 & s.contains("ff04a75c")) {//清空PLU
                                s = "";
                                removeData();
                            } else if (s.length() == 8 & s.contains("ff04b348")) {//下发PLU
                                s = "";
                                removeData();
                                addData(null);
                            }


                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
//                            }
//                        });
                    }
                }
            } catch (IOException e) {
                connecting = false;
                e.printStackTrace();
                if (reconnect) {

                }
                text_state.post(new Runnable() {
                    @Override
                    public void run() {
                        text_state.setText("连接失败");
                    //    timer1.schedule(task,0,10000);                //启动定时器
                        // 再次发出msg，循环更新
                        //handler.sendEmptyMessageDelayed(0, 10000);
                    }
                });
            } finally {
                connecting = false;
                tvSearch.post(new Runnable() {
                    @Override
                    public void run() {
                        tvSearch.setText("连接");
                   //     timer1.schedule(task,0,10000);                //启动定时器
                        // 再次发出msg，循环更新
                        handler.sendEmptyMessageDelayed(0, 10000);
                        //自动连接
                        open_blue_thread();
                    }
                });
            }
        }

        public Enter_Thread Enter_ht = null;

        public void open_blue_thread() {
            if (Enter_hh == null) {
                Enter_hh = new Enter_Handle();
            }
            if (Enter_ht == null) {
                Enter_ht = new Enter_Thread();
                Enter_ht.start();
            }
        }

        public static final int Enter_WHAT = 1;
        public static final int Enter_WHAT1 = 2;
        private Enter_Handle Enter_hh = null;
        public int Enter_num = 10;
        public class Enter_Thread extends Thread {
            public void run() {
                super.run();
                while (!Thread.interrupted()) {
                    while (Enter_num >= 0) {
                        Message message = Enter_hh.obtainMessage(Enter_WHAT, Enter_num + "");
                        Enter_hh.sendMessage(message);
                        System.out.println("准备开蓝牙Enter_hhnum:   " + Enter_num);
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Enter_num--;
                    }
                }//线程结束后重启线程的办法
                if (Enter_num < 0) {
                    Message handMsg1 = new Message();
                    handMsg1.obj = "";
                    handMsg1.what = 4;
                    Enter_hh.sendMessage(handMsg1);
                    Enter_ht.interrupt();//线程中断
                    Enter_ht = null;
                    Enter_num = 10;
                }

            }
        }

        class Enter_Handle extends Handler {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case Enter_WHAT:
                        break;
                    case 4:
                        search_BlueTooth();
                        break;
                    case Enter_WHAT1:
                        break;
                }
            }
        }


        public void  search_BlueTooth() {
            bTAdatper = BluetoothAdapter.getDefaultAdapter();
            if (bTAdatper.isEnabled()) {//蓝牙关闭不会报错
                try {
                    adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
                    //initReceiver();
                    //registerBoradcastReceiver();
                    adapter.clear();
                    if (bTAdatper.isDiscovering()) {
                        bTAdatper.cancelDiscovery();
                    }
                    //openBlueTooth();
                    //获取已经配对过的设备
                    Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
                    //将其添加到设备列表中
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            adapter.add(device);
                        }
                    }

                    bTAdatper.startDiscovery();
                    device = (BluetoothDevice) adapter.getItem(0);
                    connectDevice();
                    //listenerThread = new ListenerThread();
                    // listenerThread.start();
                } catch (Exception e) {
                    ToastUtils.showToast(MainActivity.this, "第一蓝牙没配置好");
                    adapter = new BlueToothDeviceAdapter(getApplicationContext(), R.layout.bluetooth_device_list_item);
                    bTAdatper = BluetoothAdapter.getDefaultAdapter();
                    //initReceiver();
                    //registerBoradcastReceiver();
                    adapter.clear();
                    if (bTAdatper.isDiscovering()) {
                        bTAdatper.cancelDiscovery();
                    }
                    //openBlueTooth();
                    //获取已经配对过的设备
                    Set<BluetoothDevice> pairedDevices = bTAdatper.getBondedDevices();
                    //将其添加到设备列表中
                    if (pairedDevices.size() > 0) {
                        for (BluetoothDevice device : pairedDevices) {
                            adapter.add(device);
                        }
                    }
                    bTAdatper.startDiscovery();
                    if (device != null) {
                        device = (BluetoothDevice) adapter.getItem(0);
                        connectDevice();
                    }
                    socket_wait_bluetooth = true;


                }
            } else
                Toast.makeText(MainActivity.this, "蓝牙已经关闭，请到设置那边开启蓝牙，和秤配对", Toast.LENGTH_SHORT).show();
        }
        boolean socket_wait_bluetooth=false;//socket 等待蓝牙数据


        /**
                 * 发送数据
                 *
                 * @param msg
                 */
        public void sendMsg(final String msg) {
            byte[] bytes = msg.getBytes();
            if (outputStream != null) {
                try {
                    //发送数据
                    outputStream.write(bytes);
                    text_msg.post(new Runnable() {
                        @Override
                        public void run() {
//                            text_msg.setText("小叶发送消息" + msg);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    text_msg.post(new Runnable() {
                        @Override
                        public void run() {
//                            text_msg.setText("send_msg_error " + msg);
                        }
                    });
                }
            }
        }

        //        fff5b2010000030000033300000000000000000000000000000000000001f4c6ceccefc0f3d6a60000000000000000000000000200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000015
        public void sendbyte() {
            if (outputStream != null) {
                try {
                    synchronized (Thread.currentThread()){
                        boolean isClearPLU = false;
                        long sleepTime = 3*1000;
                        if(av.size() > 0){
                            //发送数据
                            byte[] data = av.get(0);
                            if(data.length == 4 && data[1] == (byte)0x04  && data[2] == (byte)0xA6){
                                isClearPLU = true;
                            }
                            outputStream.write(data);
                            Log.d("===========", "发送消息：" + Byte_Tool.toHexString(av.get(0)));
                        }
                        if(isClearPLU){
                            sleepTime = 20*1000;
                        }
                        Thread.currentThread().wait(sleepTime);
                    }
//                    sleep(800);


                    //text_msg.setText("小叶发送消息"+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    //text_msg.setText("send_msg_error "+msg);
                }
            }
        }
        public void sendbyte(byte[] bytes,long delay) {
            if (outputStream != null) {
                try {
                    //发送数据
                    outputStream.write(bytes);
                    sleep(delay);
//                    Log.d("===========", "发送消息：" + Byte_Tool.toHexString(bytes));
                    //text_msg.setText("小叶发送消息"+msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    //text_msg.setText("send_msg_error "+msg);
                }
            }
        }

    }

    private void getGoodsData(String merId, String stallId) {
        Log.d("===========", "数据初使化");
        OkHttpUtils
                .get()
                .url("http://222.92.134.202:8002/merchant/goods/findByMerchantIdAndStallId")
//                .content(data)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .addParams("merchantId", merId)
                .addParams("stallId", stallId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        dialog = LoadingDialog.showDialogForLoading(MainActivity.this, "数据初使化...", false, null);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        e.printStackTrace();
                        Log.d("=========", "error:" + e.getStackTrace().toString());
                    }
                    @Override
                    public void onResponse(final String response, int id) {
//                        Log.d("=========", "response:" + response);
                        MyApp.getInstance().getExecutor().submit(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.
                                            getInt("code") != 200) {
                                        return;
                                    }
                                    MyApp.getInstance().getDatabase().goodsDao().clear();
//                                    clearPLU();
                                    //清空PLU
                                    byte[] clearPLU = {(byte) 0xFF, (byte) 0x04, (byte) 0xA6, (byte) 0x5D};
                                    Log.d("=========", "清空PLU");
                                    addData(clearPLU);
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");

//                                    av.clear();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        String jsonStr = jsonArray.getString(i);
//                                        GoodsBean bean = MyApp.getInstance().getGson().fromJson(jsonStr, GoodsBean.class);
                                        GoodsInfoBean bean = MyApp.getInstance().getGson().fromJson(jsonStr, GoodsInfoBean.class);
//                                        GoodsBean goodsBean = new GoodsBean();
//                                        MyApp.getInstance().getDatabase().goodsDao().insert(bean);
                                        Log.d("=========", "准备数据:" + jsonStr);
//                                        av.add(Byte_Tool.goods(bean.plu, bean.goodsId + "", bean.goodsId + "", bean.keyword, bean.goodsPrice, bean.goodsName));
//                                        if (av.size() == 5) {
//                                            goodsUpload();
//                                        }
                                        addData(Byte_Tool.good_massege2(Byte_Tool.goods(bean.plu, bean.goodsId + "", bean.goodsId + "", bean.keyword, bean.goodsPrice, bean.goodsName)));
                                    }
//                                    addData(null);
//                                    if (av.size() > 0) {
//                                        goodsUpload();
//                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    DialogUtil.dismiss(dialog);
                                }
                            }
                        });
                        Log.d("=========", "success:" + response);
                    }
                });
    }

//    private void uploadServer(String s) {
//        List<GoodsInfo> list = GoodsInfo.parse(s);
//        String data = new Gson().toJson(list);
//        Log.d("===========上传服务器", "size:" + list.size() + ",累计：" + s + "\ndata:" + data);
//        OkHttpUtils
//                .postString()
//                .url("http://47.111.64.234:8002/electronicScale/getData")
//                .content(data)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .build()
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        e.printStackTrace();
//                        Log.d("=========", "error:" + e.getStackTrace().toString());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.d("=========", "上传成功:" + response);
//                    }
//                });
//    }

    private void parseLeiji(final String s, boolean isFinish) {
        MyApp.getInstance().getExecutor().submit(new Runnable() {
            @Override
            public void run() {
                List<GoodsInfo> list = GoodsInfo.parse(s);
//                for (GoodsInfo info : list) {
//                    GoodsBean goodsBean = MyApp.getInstance().getDatabase().goodsDao().findGoodsById(info.bianhao);
//                    if (goodsBean != null) {
//                        info.name = goodsBean.goodsName;
//                        info.unitName = goodsBean.unit;
//                    }
//                }
                Log.d("===========", "size:" + list.size() + (isFinish?",结算：":",累计：") + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectLayout.isShown()) {
                            adapter = new GoodsAdapter(MainActivity.this);
                            listView.setAdapter(adapter);
                            adapter.clear();
                            adapter.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else {
                            String jsonStr = new Gson().toJson(list);
                            mWebView.loadUrl("javascript:whenTradingList('" + jsonStr + "')");
                            if (isFinish) {
                                String orderId = s.substring(1682, 1706);
                                Log.d("=============", "单号：" + orderId);
                                JSONObject json = new JSONObject();
                                try {
                                    json.put("orderId", orderId);
                                    json.put("goods", new JSONArray(jsonStr));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                jsonStr = json.toString();
                                mWebView.loadUrl("javascript:addElectronicScaleData('" + jsonStr + "')");
                            }
                            Log.d("=============", "json：" + jsonStr);
                        }
                    }
                });
            }
        });


    }


//    public static BM_thread bmt = null;
//    public static int num2 = 2;
//    public static final int WHAT = 1;
//    private static bhHandle bhh = null;

//    public static class BM_thread extends Thread {
//        @Override
//        public void run() {
//            super.run();
//            while (!Thread.interrupted()) {
//                while (num2 >= 0) {
//                    Message message = bhh.obtainMessage(WHAT, num2 + "");
//                    bhh.sendMessage(message);
//                    // System.out.println("支付num倒计时: "+num);
//                    try {
//                        sleep(250);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    num2--;
//                }
//                //线程结束后重启线程的办法
//                if (num2 < 0) {
//                    Message handMsg1 = new Message();
//                    handMsg1.obj = "";
//                    handMsg1.what = 4;
//                    bhh.sendMessage(handMsg1);
//                    bmt.interrupt();//线程中断
//                    bmt = null;
//                    num2 = 2;
//                }
//            }
//        }
//    }

//    /***
//     * 编码倒计时
//     ***/
//    class bhHandle extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case WHAT:
////tv_time.setText(msg.obj.toString());
//                    break;
//                case 4:
//                    System.out.println("handle倒计时0.5秒weiyima   " + weiyima);
////                    weiyima = sbyte.gotWYM(true, 0);
////                    connectThread.sendbyte(Byte_Tool.print_code(weiyima));
//                    break;
//            }
//        }
//    }

//    String weiyima = "";

//    public void message_demo(String[] s, int a) {
//        Message handMsg5 = new Message();
//        handMsg5.obj = s;
//        handMsg5.what = a;
//        mHandler.sendMessage(handMsg5);
//    }


//    /**
//     * 监听线程
//     */
//    private class ListenerThread extends Thread {
//
//        private BluetoothServerSocket serverSocket;
//        private BluetoothSocket socket;
//
//        @Override
//        public void run() {
//            try {
//                serverSocket = bTAdatper.listenUsingRfcommWithServiceRecord(NAME, BT_UUID);
//                while (true) {
//                    //线程阻塞，等待别的设备连接
//                    socket = serverSocket.accept();
//                    text_state.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            text_state.setText("连接中");
//                        }
//                    });
//                    Log.d("=============","ListenerThread connected!");
//                    connectThread = new ConnectThread(socket, false);
//                    connectThread.start();
//
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    //退出本APP
//    private long  mExitTime;
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//    if (keyCode == KeyEvent.KEYCODE_BACK) {
//     if ((System.currentTimeMillis() - mExitTime) > 2000) {
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
//             mExitTime = System.currentTimeMillis();
//
//     } else {
//    //BluetoothManager.turnOffBluetooth();
//    //connectThread.destroy();
//    onDestroy(); System.gc();finish();
//
//     }
//
//    return true; 
//       }
//       return super.onKeyDown(keyCode, event);
//    }
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
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(new AndroidJs(), "android");
    }

    public class AndroidJs {
        @JavascriptInterface
        public void getGoodsByMerchantId(String merId, String stallId, String ticket) {
            Log.d("===========", "js,merId:" + merId + ",stallId：" + stallId+",ticket:"+ticket);
            ticketCheck:if (!TextUtils.isEmpty(ticket)) {
                TicketBean ticketBean = new Gson().fromJson(ticket, TicketBean.class);
                if (connectThread == null || !connectThread.isAlive() || connectThread.isInterrupted()) {
                    break ticketCheck;
                }
                av.clear();
                addData(Byte_Tool.sysParam(140,3));
                addData(Byte_Tool.sysParam(428,1));
                addData(Byte_Tool.Paper_Text01(ticketBean.marKetName, 4));
                addData(Byte_Tool.Paper_Text01(ticketBean.merchantName, 5));
                addData(Byte_Tool.Paper_Text01(ticketBean.stallNo, 6));
//                connectThread.sendbyte(Byte_Tool.Paper_Text01("文本4", 7));
                addData(Byte_Tool.Paper_Text01(ticketBean.Prompt1, 8));
                addData(Byte_Tool.Paper_Text01("", 9));
                addData(Byte_Tool.Paper_Text01(ticketBean.Prompt2, 10));
                addData(Byte_Tool.Paper_Text01(ticketBean.Prompt3, 11));
                addData(Byte_Tool.Paper_Text01("欢迎光临", 19));//欢迎光临
                addData(null);
            }
            getGoodsData(merId, stallId);
        }
    }
}

