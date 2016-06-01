package com.lietou.callapp.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-18 11:36
 * 邮箱：13716784721@163.com
 */
public class MsgUtils {


    public static final String URL = "http://10.1.1.103:8081/lietou-web-crm/call/createDeviceInfo";

    public static void MessagePhone(String loginName ,String phoneNumber , String token , final Context context){
        RequestParams params = new RequestParams();
        params.addBodyParameter("loginName" , loginName);
        params.addBodyParameter("phoneNumber" , phoneNumber);
        params.addBodyParameter("token" , token);

        Log.d("TAG" , phoneNumber + "------" + token);
        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST,
                URL,
                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Toast.makeText(context, "信息上传成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Toast.makeText(context, "信息上传失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
