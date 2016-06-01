package com.lietou.callapp.adapter;

import android.content.Context;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lietou.callapp.R;
import com.lietou.callapp.bean.CallLogB;

import java.util.List;

/**
 * 类描述:
 * 作者：zuowenbin
 * 时间：16-4-15 16:52
 * 邮箱：13716784721@163.com
 */
public class InfoLogAdapter extends RecyclerView.Adapter<InfoLogAdapter.ViewHolder>{

    private Context context;
    private List<CallLogB> list;

    private ViewHolder holder;

    public InfoLogAdapter(Context context, List<CallLogB> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.info_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_logNumber.setText(list.get(position).getNumber());
        holder.tv_logTime.setText(list.get(position).getDate());
        int type = list.get(position).getType();
        switch (type) {
            case CallLog.Calls.INCOMING_TYPE://来电
                holder.tv_logType.setText("来电");
                break;
            case CallLog.Calls.OUTGOING_TYPE://已拨
                holder.tv_logType.setText("已拨");
                break;
            case CallLog.Calls.MISSED_TYPE://未接
                holder.tv_logType.setText("未接");
                break;
            case 4:
                holder.tv_logType.setText("拒接");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_logNumber;
        private final TextView tv_logTime;
        private final TextView tv_logType;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_logNumber = ((TextView) itemView.findViewById(R.id.calllog_number));
            tv_logTime = ((TextView) itemView.findViewById(R.id.calllog_time));
            tv_logType = ((TextView) itemView.findViewById(R.id.calllog_type));

        }
    }
}
