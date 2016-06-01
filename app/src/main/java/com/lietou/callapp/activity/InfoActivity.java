package com.lietou.callapp.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.lietou.callapp.R;
import com.lietou.callapp.adapter.InfoLogAdapter;
import com.lietou.callapp.bean.CallLogB;
import com.lietou.callapp.utils.PhoneListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private String disPlay_name;
    private CallLogB calllog;
    private List<CallLogB> list = new ArrayList<>();
    private RecyclerView recyView;

    private Context context;
    private InfoLogAdapter adapter;
    private TextView tv_Number;

    private String PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        context = getApplicationContext();

        Intent intent = getIntent();
        disPlay_name = intent.getStringExtra("NAME");
        PhoneNumber = intent.getStringExtra("NUMBER");




        Log.d("TAG", "传来的手机号码是－－－－－:" + PhoneNumber);
        initData();

        initView();

    }

    private void initData() {
        ContentResolver cr = getContentResolver();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        String number;
        String name;
        int type;
        long callTime;
        Date date = null;
        String time = "";


        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[]{
                        CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.TYPE, CallLog.Calls.DATE,}, null, null,
                CallLog.Calls.DEFAULT_SORT_ORDER);

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            number = cursor.getString(0);
            name = cursor.getString(1);
            type = cursor.getInt(2);

            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = new Date(Long.parseLong(cursor.getString(3)));
            time = sfd.format(date);

            Log.d("TAG" , "----->通话的类型" + type);
            if (disPlay_name.equals(name)) {
                calllog = new CallLogB(name, number, type, time);
                list.add(calllog);
            }

        }
    }

    private void initView() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(disPlay_name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyView = ((RecyclerView) findViewById(R.id.recyView));
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        recyView.setLayoutManager(manager);
        adapter = new InfoLogAdapter(context, list);
        recyView.setAdapter(adapter);


        tv_Number = ((TextView) findViewById(R.id.info_number));
        tv_Number.setText(PhoneNumber);
        PhoneListener.getCallName(disPlay_name);

        //打电话按钮的监听事件
        findViewById(R.id.call_Btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + PhoneNumber));
                startActivity(intent);
            }
        });

        //发送短信按钮的监听事件


    }
}
