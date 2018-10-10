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
import com.cc.tongxundi.utils.SPManager;

public class LoginActivity extends BaseActivity {
    private EditText mEtPhone;
    private EditText mEtCode;
    private Button mBtnLogin;
    private EditText mEtAddr;
    private Button mBtnCode;
    private SPManager spManager;
    public static void startActivity(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
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
        String phone = mEtPhone.getText().toString();
        String code = mEtCode.getText().toString();
        String addr = mEtAddr.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(code) || TextUtils.isEmpty(addr)) {
            Toast.makeText(this, "账号，验证码，地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        spManager.put(SPManager.KEY_IS_LOGIN, true);
        MainActivity.startActivity(this);
        spManager.put(SPManager.KEY_ADDR, addr);
        spManager.put(SPManager.KEY_PHONE, phone);
        finish();
    }

    private void getCode() {
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(LoginActivity.this, "验证码获取成功", Toast.LENGTH_SHORT).show();
    }
}
