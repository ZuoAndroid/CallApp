package com.lietou.callapp.utils;

import com.tencent.android.tpush.XGPushConfig;

/**
 * 类描述:上传地址的配置
 * 作者：zuowenbin
 * 时间：16-4-28 14:34
 * 邮箱：13716784721@163.com
 */
public class UrlUtils {

    public static final String LocalUpLoadUrl = "http://10.1.1.117:8080/lietou-web-crm/call/uploadAudio";
    public static final String TestUpLoadUrl = "http://10.1.1.212:9080/call/uploadAudio";

    public static final String LocalStatusUrl = "http://10.1.1.117:8080/lietou-web-crm/call/updateCallStatus";
    public static final String TestStatusUrl = "http://10.1.1.212:9080/call/updateCallStatus";


    public static final String CallInUrl = "http://10.1.1.103:8080/lietou-web-crm/call/callIn";
    public static final String TestCallInUrl = "http://10.1.1.212:9080/call/callIn";
}
