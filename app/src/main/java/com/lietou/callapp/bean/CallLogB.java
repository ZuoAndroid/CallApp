package com.lietou.callapp.bean;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-3-28 13:55
 * 邮箱：13716784721@163.com
 */
public class CallLogB {

    private String name;
    private String  number;
    private int type;
    private String date;

    public CallLogB(String name, String number, int type, String date) {
        this.name = name;
        this.number = number;
        this.type = type;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
