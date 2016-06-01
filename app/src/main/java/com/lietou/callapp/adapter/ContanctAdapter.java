package com.lietou.callapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lietou.callapp.R;
import com.lietou.callapp.utils.PingYinUtil;
import com.lietou.callapp.views.PinyinComparator;

import java.util.Arrays;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-15 15:13
 * 邮箱：13716784721@163.com
 */
public class ContanctAdapter extends BaseAdapter implements SectionIndexer {

    private String[] list;
    private Context context;

    public ContanctAdapter(String[] list, Context context) {
        this.list = list;
        this.context = context;
        Arrays.sort(list, new PinyinComparator());
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String nickName = list[position];
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.contact_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvCatalog = (TextView) convertView
                    .findViewById(R.id.contactitem_catalog);
            viewHolder.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.contactitem_avatar_iv);
            viewHolder.tvNick = (TextView) convertView
                    .findViewById(R.id.contactitem_nick);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String catalog = PingYinUtil.converterToFirstSpell(nickName)
                .substring(0, 1);
        if (position == 0) {
            viewHolder.tvCatalog.setVisibility(View.VISIBLE);
            viewHolder.tvCatalog.setText(catalog);
        } else {
            String lastCatalog = PingYinUtil.converterToFirstSpell(
                    list[position - 1]).substring(0, 1);
            if (catalog.equals(lastCatalog)) {
                viewHolder.tvCatalog.setVisibility(View.GONE);
            } else {
                viewHolder.tvCatalog.setVisibility(View.VISIBLE);
                viewHolder.tvCatalog.setText(catalog);
            }
        }

        viewHolder.ivAvatar.setImageResource(R.drawable.default_avatar);
        viewHolder.tvNick.setText(nickName);
        return convertView;
    }

    static class ViewHolder {
        TextView tvCatalog;// 目录
        ImageView ivAvatar;// 头像
        TextView tvNick;// 昵称
    }

    public int getPositionForSection(int section) {
        for (int i = 0; i < list.length; i++) {
            String l = PingYinUtil.converterToFirstSpell(list[i])
                    .substring(0, 1);
            char firstChar = l.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    public int getSectionForPosition(int position) {
        return 0;
    }

    public Object[] getSections() {
        return null;
    }
}
