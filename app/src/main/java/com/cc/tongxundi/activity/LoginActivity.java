package com.cc.tongxundi.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.MainActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.UserBean;
import com.cc.tongxundi.db.UserDbHelper;
import com.cc.tongxundi.down.Http.CommonResultBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpResultCallback;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.im.IMHelper;
import com.cc.tongxundi.im.IMListener;
import com.cc.tongxundi.utils.SPManager;
import com.tencent.ijk.media.player.pragma.DebugLog;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.plugin.common.AppMgr;
import com.yuntongxun.plugin.common.ClientUser;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.ECPreferences;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;
import com.yuntongxun.plugin.greendao3.helper.DaoHelper;
import com.yuntongxun.plugin.im.dao.helper.IMDao;


import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;

public class LoginActivity extends BaseActivity {
    private EditText mEtPhone;
    private EditText mEtCode;
    private Button mBtnLogin;
    private EditText mEtAddr;
    private Button mBtnCode;
    private SPManager spManager;
    private String TAG = "LoginActivity";
    private UserBean mUserBean;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        spManager = new SPManager(this);
        mEtAddr = (EditText) findViewById(R.id.et_addr);
        mEtCode = (EditText) findViewById(R.id.et_code);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnCode = (Button) findViewById(R.id.btn_get_code);
        mBtnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCode();
            }
        });
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();

            }
        });
        initIm();
    }

    private void login() {
        //127051
        mLoadView.show();
        final String phone = mEtPhone.getText().toString();
        String code = mEtCode.getText().toString();
        final String addr = mEtAddr.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(addr)) {
            Toast.makeText(this, "账号，验证码，地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getInstance().login(phone, code, addr, new HttpResultCallback<CommonResultBean<UserBean>>() {
            @Override
            public void onError(String msg) {
                mLoadView.dismiss();

            }

            @Override
            public void onResponse(CommonResultBean<UserBean> response) {
                if (response.isStatus()) {
                    //127051
                    mUserBean = response.getData();
                    // UserDbHelper.getInstance().insertUser(userBean);
                    // DebugLog.d(TAG, mUserBean.toString());
                    String nickname = TextUtils.isEmpty(mUserBean.getNickname()) ? "用户000" + mUserBean.getId() : mUserBean.getNickname();
                    loginIM(String.valueOf(mUserBean.getId()), nickname);
                } else {
                    mLoadView.dismiss();
                    ToastUtil.showMessage(response.getMsg());
                }

            }
        });


    }


    private void initIm() {

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SDKCoreHelper.ACTION_SDK_CONNECT);
        registerReceiver(mSDKNotifyReceiver, intentFilter);
        if (AppMgr.getClientUser() != null) {
            LogUtil.d(TAG, "SDK auto connect...");
            SDKCoreHelper.init(getApplicationContext());
        }
    }


    private void loginIM(String userid, String nickName) {
        //  IMHelper.getInstance().initIMSDK(getApplicationContext(), imListener);
        ClientUser.UserBuilder builder = new ClientUser.UserBuilder(userid, nickName);
        // 以下setXXX参数都是可选
        builder.setAppKey(IMHelper.APPKEY);// AppId(私有云使用)
        builder.setAppToken(IMHelper.APPTOKEN);// AppToken(私有云使用)
        // builder.setPwd(et_pwd.getText().toString());// Password不为空情况即通讯账号密码登入
        // 下面三个参数是调用REST接口使用(如:语音会议一键静音功能)
        // builder.setAccountSid("accountSid");// 主账号Id(REST使用)
        // builder.setAuthToken("autoToken");// 账户授权令牌(REST使用)


//            // 公有云使用一个参数login登入
//             builder.setRestHost("http://app.cloopen.com:8881");// REST 协议+ip+端口(REST使用)
//             builder.setLvsHost("http://app.cloopen.com:8888");
        SDKCoreHelper.login(builder.build());

    }

    private BroadcastReceiver mSDKNotifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mLoadView.dismiss();
            if (SDKCoreHelper.ACTION_SDK_CONNECT.equals(intent.getAction())) {
                if (SDKCoreHelper.isLoginSuccess(intent)) {
                    DebugLog.d(TAG, "login  succ------");
                    // 初始化IM数据库
                    DaoHelper.init(LoginActivity.this, new IMDao());
                    MainActivity.startActivity(mContext);
                    LoginActivity.this.finish();
                    try {
                        spManager.put(SPManager.KEY_UID, String.valueOf(mUserBean.getId()));
                        spManager.put(SPManager.KEY_IS_LOGIN, true);
//                        spManager.put(SPManager.KEY_ADDR,mUserBean.getAddress());
                        //     spManager.put(SPManager.KEY_PHONE,mUserBean.getPhone());

                    } catch (Exception e) {


                    }


                } else {
                    int error = intent.getIntExtra("error", 0);
                    if (error == SdkErrorCode.CONNECTING) return;
                    LogUtil.e(TAG, "登入失败[" + error + "]");
                    ToastUtil.showMessage("登入失败[" + error + "]");
                }
            }
        }
    };

    private void getCode() {

        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getInstance().getCode(phone, new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, "验证码获取失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onSucc(final String response, int totalPages, int pageStart) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String msg = jsonObject.optString("msg");
                            boolean status = jsonObject.optBoolean("status");
                            if (status) {
                                Toast.makeText(LoginActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "发送验证码失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSDKNotifyReceiver);
    }
}
