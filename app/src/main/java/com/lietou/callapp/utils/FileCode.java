package com.lietou.callapp.utils;

import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**199199850
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-16 14:28
 * 邮箱：13716784721@163.com
 */
public class FileCode {

    public static String encodeBase64File(String filePath) throws IOException {


        Log.d("TAG" , "---->111" + filePath);
        File file = new File(filePath);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputStream.read(buffer);
        inputStream.close();
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

}
