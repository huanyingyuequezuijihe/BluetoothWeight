package com.zt.bw;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.os.Environment;

import com.google.gson.Gson;
import com.zt.bw.database.MyDatabase;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by lifujun on 2018/8/28.
 */

public class MyApp extends Application {
    public final String DATABASE_NAME = "database.db";
    private static MyApp instance = null;
    private MyDatabase database;
    private ExecutorService mExecutorService = null;
    private Gson gson = new Gson();
//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL(
//                    "CREATE TABLE history_search_home (id INTEGER NOT NULL, name TEXT NOT NULL unique, PRIMARY KEY(id))");
//        }
//    };
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mExecutorService = Executors.newFixedThreadPool(2);
    }
    public ExecutorService getExecutor(){
        return mExecutorService;
    }
    public Gson getGson(){
        return gson;
    }
    public String getDataPath(){
        return Environment.getExternalStorageDirectory().getPath()+ File.separator+getString(R.string.app_name)+ File.separator+"data"+ File.separator;
    }
    public String getConfigPath(){
        File file = new File(Environment.getExternalStorageDirectory().getPath()+ File.separator+getString(R.string.app_name)+ File.separator+"config"+ File.separator);
        if(!file.exists()){
            file.mkdirs();
        }
        return file.getPath();
    }
    public static MyApp getInstance() {
        return instance;
    }
    public synchronized MyDatabase getDatabase() {
        if (database == null) {
            synchronized (this) {
                String dbPath = "";
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    dbPath = getDataPath();
                    File file  = new File(dbPath);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    dbPath += DATABASE_NAME;
                }else{
                    dbPath = DATABASE_NAME;
                }
                database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, dbPath)
//                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return database;
    }
}
