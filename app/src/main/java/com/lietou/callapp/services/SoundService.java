package com.lietou.callapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.lietou.callapp.utils.PhoneListener;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-16 11:08
 * 邮箱：13716784721@163.com
 */
public class SoundService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TelephonyManager manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        manager.listen(new PhoneListener() , PhoneStateListener.LISTEN_CALL_STATE);
        Log.d("TAG" , "监听服务启动了！！！");
    }



}
