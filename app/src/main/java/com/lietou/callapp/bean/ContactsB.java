package com.lietou.callapp.bean;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-3-28 09:44
 * 邮箱：13716784721@163.com
 */
public class ContactsB {

    private long _id; //表的ID，主要用于其它表通过contacts 表中的ID可以查到相应的数据。
    private String display_name;  //联系人名称
    private String phoneNumber; //联系人电话

    public ContactsB(long _id, String display_name, String phoneNumber) {
        this._id = _id;
        this.display_name = display_name;
        this.phoneNumber = phoneNumber;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
