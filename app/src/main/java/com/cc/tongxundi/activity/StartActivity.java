package com.cc.tongxundi.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.MainActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.utils.SPManager;
import com.yuntongxun.plugin.common.AppMgr;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.LogUtil;

public class StartActivity extends BaseActivity {
    private ImageView mIvIcon;
    private SPManager spManager;
    private String TAG = "StartActivity";

    @Override
    public int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void initView() {
        spManager = new SPManager(this);

        mIvIcon = (ImageView) findViewById(R.id.iv_icon);
        mIvIcon.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isLogin = (boolean) spManager.getSharedPreference(SPManager.KEY_IS_LOGIN, false);
                if (isLogin) {
                    MainActivity.startActivity(StartActivity.this);
                } else {
                    LoginActivity.startActivity(StartActivity.this);
                }
                finish();
            }
        }, 2000);
    }


}
