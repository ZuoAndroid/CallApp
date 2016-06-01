package com.lietou.callapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lietou.callapp.activity.InfoActivity;
import com.lietou.callapp.adapter.ContanctAdapter;
import com.lietou.callapp.bean.ContactsB;
import com.lietou.callapp.utils.AddContacts;
import com.lietou.callapp.utils.ContactsHelper;
import com.lietou.callapp.utils.MsgUtils;
import com.lietou.callapp.utils.PhoneListener;
import com.lietou.callapp.utils.PhoneUtils;
import com.lietou.callapp.views.SideBar;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvContact;
    private SideBar indexBar;
    private WindowManager mWindowManager;
    private TextView mDialogText;
    private List<ContactsB> list = new ArrayList<>();
    private static String[] nicks;
    private Context context;
    private String TOKEN;
    private String PhoneNumber;
    private String NativePhoneNumber;
    private EditText editText , userName;
    private TextView token_Tv;

    public static final String ACTION = "recordingFlag";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);


        //注册信鸽服务
        XGPushManager.registerPush(context);
        TOKEN = XGPushConfig.getToken(context);


        PhoneListener.getTOKEN(TOKEN);
        Log.d("TAG" , "得到的设备的Ｔｏｋｅｎ是：" + TOKEN);
        //得到本机的号码
        PhoneUtils phoneUtils = new PhoneUtils(context);
        NativePhoneNumber = phoneUtils.getNativePhoneNumber();
        Log.d("TAG" , "得到的本机号是:" + NativePhoneNumber);
        initView();
    }

    //初始化控件的方法
    private void initView() {

/*
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        toolbar.setTitle("");
            setSupportActionBar(toolbar);
*/

        //启动后台监听服务
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.setPackage(getPackageName());
        context.startService(intent);

        lvContact = (ListView) this.findViewById(R.id.lvContact);
        list = ContactsHelper.getContancts(context);

        nicks = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            nicks[i] = list.get(i).getDisplay_name();
        }

        lvContact.setAdapter(new ContanctAdapter(nicks , context));
        indexBar = (SideBar) findViewById(R.id.sideBar);
        indexBar.setListView(lvContact);
        mDialogText = (TextView) LayoutInflater.from(context).inflate(
                R.layout.list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);
        final WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogText, lp);
        indexBar.setTextView(mDialogText);


        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context , InfoActivity.class);

                intent.putExtra("NAME" , nicks[position]);

                for (int i = 0; i < nicks.length; i++) {
                    if (nicks[position].equals(list.get(i).getDisplay_name())){
                        PhoneNumber = list.get(i).getPhoneNumber();
                    }
                }


                intent.putExtra("NUMBER" , PhoneNumber);
                Log.d("TAG" , " 传递的手机号码是----------->" + PhoneNumber);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info , menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.info_menu:
                LayoutInflater layoutInflater = getLayoutInflater();
                final View view = layoutInflater.inflate(R.layout.dialog_style , ((ViewGroup) findViewById(R.id.layout)));
                new AlertDialog.Builder(this)
                        .setView(view)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editText = ((EditText) view.findViewById(R.id.number_tv));
                                userName = ((EditText) view.findViewById(R.id.loginName));
                                String number = editText.getText().toString();
                                String name = userName.getText().toString();
                                Log.d("TAG" , name + number);
                                token_Tv = ((TextView) view.findViewById(R.id.token_tv));
                                token_Tv.setText(TOKEN);
                                MsgUtils.MessagePhone(name ,number ,TOKEN , context);
                            }
                        })
                        .setNegativeButton("取消" , null)
                        .show();

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        XGPushClickedResult result = XGPushManager.onActivityStarted(this);

        if (result != null){
            String name = result.getTitle();
            String number = result.getContent();

            for (int i = 0; i < list.size(); i++) {
                String name1 = list.get(i).getDisplay_name();
                if (name.equals(name1)){
                    break;
                }else {
                    AddContacts add = new AddContacts(context);
                    add.addContacts(name , number);
                    break;
                }
            }


            PhoneListener.getCallName(name);
            Intent callIntent = new Intent();
            callIntent.setAction(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }
    }
}
