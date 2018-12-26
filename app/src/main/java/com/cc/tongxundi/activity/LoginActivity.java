package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.SdkErrorCode;

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
    }

    private void login() {
        final String phone = mEtPhone.getText().toString();
        String code = mEtCode.getText().toString();
        final String addr = mEtAddr.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(addr)) {
            Toast.makeText(this, "账号，验证码，地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getInstance().login(phone, code, addr, new HttpResultCallback<CommonResultBean<UserBean>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonResultBean<UserBean> response) {
                UserBean userBean = response.getData();
                UserDbHelper.getInstance().insertUser(userBean);
                spManager.put(SPManager.KEY_UID, String.valueOf(userBean.getId()));
                spManager.put(SPManager.KEY_IS_LOGIN, true);
                DebugLog.d(TAG, userBean.toString());
                loginIM();
            }
        });

    }

    private IMListener imListener = new IMListener() {
        @Override
        public void onInitialized() {
            super.onInitialized();
            String userId = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
            IMHelper.getInstance().login(userId, imListener);
        }

        @Override
        public void onError(Exception e) {
            super.onError(e);
        }

        @Override
        public void onConnectState(ECDevice.ECConnectState state, ECError error) {
            super.onConnectState(state, error);

            if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
                if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                    DebugLog.d(TAG, "==帐号异地登陆");
                } else {
                    DebugLog.d(TAG, "==其他登录失败,错误码：" + error.errorCode);
                }
                return;
            } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                DebugLog.i(TAG, "==登陆成功");
            }
        }

    };

    private void loginIM() {
        IMHelper.getInstance().initIMSDK(getApplicationContext(), imListener);

    }

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
}
