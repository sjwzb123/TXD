package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.im.IMHelper;
import com.cc.tongxundi.im.IMListener;
import com.cc.tongxundi.utils.SPManager;
import com.tencent.ijk.media.player.pragma.DebugLog;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;

public class ChatActivity extends BaseActivity {
    private RecyclerView mChatRv;
    private EditText mEtChat;
    private Button mBtnChat;
    private String TAG="ChatActivity";
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView() {
        mChatRv = findViewById(R.id.rv_chat);
        mEtChat = findViewById(R.id.et_chat);
        mBtnChat = findViewById(R.id.btn_push_chat);
        mBtnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendMsg();
            }
        });
    }

    private void sendMsg() {
        String msg = mEtChat.getText().toString();
        String userId = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
        IMHelper.getInstance().sendTXTMsg(msg, userId, imListener);
    }

    private IMListener imListener = new IMListener() {
        @Override
        public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {
            super.onSendMessageComplete(ecError, ecMessage);
            DebugLog.d(TAG,"onSendMessageComplete "+ecError.errorCode+" msg "+ecMessage.toString());

        }

        @Override
        public void onProgress(String s, int i, int i1) {
            super.onProgress(s, i, i1);
            DebugLog.d(TAG,"onProgress "+s+" i "+i);
        }
    };
}
