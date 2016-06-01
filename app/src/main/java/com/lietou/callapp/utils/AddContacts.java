package com.lietou.callapp.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-18 10:59
 * 邮箱：13716784721@163.com
 */
public class AddContacts {

    private Context context;

    public AddContacts(Context context) {
        this.context = context;
    }

    public void addContacts(String name , String number){
         /* 往 raw_contacts 中添加数据，并获取添加的id号*/
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = context.getContentResolver();
        ContentValues values = new ContentValues();
        long contactId = ContentUris.parseId(resolver.insert(uri, values));

        /* 往 data 中添加数据（要根据前面获取的id号） */
        // 添加姓名
        uri = Uri.parse("content://com.android.contacts/data");
        values.put("raw_contact_id", contactId);
        values.put("mimetype", "vnd.android.cursor.item/name");
        values.put("data2", name);
        resolver.insert(uri, values);

        // 添加电话
        values.clear();
        values.put("raw_contact_id", contactId);
        values.put("mimetype", "vnd.android.cursor.item/phone_v2");
        values.put("data2", "2");
        values.put("data1", number);
        resolver.insert(uri, values);

    }
}
