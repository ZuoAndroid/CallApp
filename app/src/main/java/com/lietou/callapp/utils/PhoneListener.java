package com.lietou.callapp.utils;

import android.app.Service;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.czt.mp3recorder.MP3Recorder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.tencent.android.tpush.XGPushConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-16 11:19
 * 邮箱：13716784721@163.com
 */
public class PhoneListener extends PhoneStateListener {


    private MP3Recorder mp3Recorder;
    private static String CallName;
    private static Context context_this;
    private static String TOKEN;
    private String FilePath;

    private String CallInId;

    public static final String CALLING = "0"; //开始接通
    public static final String HANGUP = "1";//挂断
    public static final String NOCONNECTED = "2";

    private String BaseCode;

    public static String getCallName(String callName) {
        CallName = callName;
        Log.d("TAG", "得到的名字是:" + callName);
        return CallName;
    }

    public static Context getContext(Context context) {
        context_this = context;
        return context_this;
    }

    public static String getTOKEN(String token) {
        TOKEN = token;
        return TOKEN;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE://空闲或者挂断
                if (mp3Recorder != null) {

                    stopRecord();
                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK://接通调用录音

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        CallStatusUtils.callStatus(CALLING, CallName, "1000");
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(2000);
                            recordCalling();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }.start();
                break;
            case TelephonyManager.CALL_STATE_RINGING://响铃

                Log.d("TAG", "来电的号码是:" + incomingNumber);

                Log.d("TAG", "我的ｔｏｋｅｎ" + TOKEN);
                RequestParams params = new RequestParams();
                params.addBodyParameter("oppositePhoneNumber", incomingNumber);
                params.addBodyParameter("deviceToken", TOKEN);
                HttpUtils utils = new HttpUtils();
                utils.send(HttpRequest.HttpMethod.POST,
                        UrlUtils.TestCallInUrl,
                        params,
                        new RequestCallBack<String>() {
                            @Override
                            public void onSuccess(ResponseInfo<String> responseInfo) {
                                Log.d("TAG", "--------------" + responseInfo.result);
                                CallInId = responseInfo.result;
                            }

                            @Override
                            public void onFailure(HttpException error, String msg) {
                                Log.d("TAG", "没访问到啊！！！");
                            }
                        });
                Log.d("TAG", "来电话了");
                break;
        }
    }

    //当挂断之后停止录音
    private void stopRecord() {
        if (mp3Recorder != null) {
            mp3Recorder.stop();

            MediaPlayer player = MediaPlayer.create(context_this, Uri.fromFile(new File(FilePath)));
            int time = player.getDuration();
            String longTime = String.valueOf(time);
            Log.d("TAG", "得到mp3文件的总时长是－－－－>" + longTime);
            CallStatusUtils.callStatus(HANGUP, CallName, longTime);


            try {
                BaseCode = FileCode.encodeBase64File(FilePath);
                Log.d("TAG", BaseCode);

                if (CallInId != null) {
                    UpLoadUtils.UploadFle(UrlUtils.TestUpLoadUrl, BaseCode, CallInId, "mp3");
                    Log.d("TAG" , "呼入执行的上传方法" + CallInId);
                } else {
                    Log.d("TAG" , "呼出执行的上传方法");
                    UpLoadUtils.UploadFle(UrlUtils.TestUpLoadUrl, BaseCode, CallName, "mp3");
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            mp3Recorder = null;

        }


    }

    private void recordCalling() {
        String FileName = getLocalTime();

        //获取本机时间作为保存文件的名字的一部分
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        Log.d("TAG", "得到本机的时间是---》" + str);

        File SavePath = Environment.getExternalStorageDirectory(); //获取到手机SD卡的根目录
        File saveFile = new File(SavePath + "/" + "AudioPhone"); //对应的本App的文件目录(录音文件保存的总目录)
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        Log.d("TAG", "录音文件保存的总目录" + saveFile);

        //初始化录音文件保存的路径
        String path = saveFile + "/" + str;

        Log.d("TAG", "录音文件保存的路径--->" + path);
        //判断文件保存的路径是否存在，如果不存在就创建
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }

        mp3Recorder = new MP3Recorder(new File(path, CallName + FileName + ".mp3"));
        FilePath = path + "/" + CallName + FileName + ".mp3";
        Log.d("TAG", "文件的总路径是－－－－：" + FilePath);
        try {
            mp3Recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取本地时间的方法
     *
     * @return
     */
    public String getLocalTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


}
