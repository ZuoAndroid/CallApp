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
 * 时间：16-4-18 09:24
 * 邮箱：13716784721@163.com
 */
public class UpLoadUtils {


    public static void UploadFle(String url, String baseFile, String callDetailId, String fileType) {
        RequestParams params = new RequestParams();

        params.addBodyParameter("callDetailId", callDetailId);
        params.addBodyParameter("fileContent", baseFile);
        params.addBodyParameter("fileType", fileType);

        Log.d("TAG" , callDetailId + fileType);

        HttpUtils utils = new HttpUtils();
        utils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        Log.d("TAG", "文件上传成功");

                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.d("TAG" , "接口访问失败");
                    }
                });
    }

}
