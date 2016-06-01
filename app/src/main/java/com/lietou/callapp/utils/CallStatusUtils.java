package com.lietou.callapp.utils;

import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-19 14:35
 * 邮箱：13716784721@163.com
 */
public class CallStatusUtils {

    public static final String URL = "http://10.1.1.212:9080/call/updateCallStatus";

    /**callDatailld
     *
     * @param callStatus 0开始接通　１挂断
     * @param callDetailId　
     * @param callTotalTime
     */
    public static void callStatus(final String callStatus, String callDetailId, String callTotalTime) {

        RequestParams params = new RequestParams();
        params.addBodyParameter("callStatus", callStatus);
        params.addBodyParameter("callDetailId", callDetailId);
        params.addBodyParameter("callTotalTime", callTotalTime);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST,
                UrlUtils.TestStatusUrl,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {

                        Log.d("TAG" , "发送状态------>" + callStatus);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                    }
                });

    }
}
