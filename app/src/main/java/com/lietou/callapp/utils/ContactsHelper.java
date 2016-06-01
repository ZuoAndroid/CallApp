package com.lietou.callapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;


import com.lietou.callapp.bean.ContactsB;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述:查找联系人的方法
 * 作者：zuowenbin
 * 时间：16-3-28 09:48
 * 邮箱：13716784721@163.com
 */
public class ContactsHelper {

    public static List<ContactsB> list = new ArrayList<>();

    public static List<ContactsB> getContancts(Context context) {
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        ContactsB contactsBean;
        String phoneName;
        String phoneNumber;
        Long id;
        while (cursor.moveToNext()) {
            phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            id = cursor.getLong(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

            Log.d("TAG" , "id=" + id + ",+name=" + phoneName + ",number=" + phoneNumber);
            contactsBean = new ContactsB(id, phoneName, phoneNumber);
            list.add(contactsBean);
        }
        return list;
    }
}
