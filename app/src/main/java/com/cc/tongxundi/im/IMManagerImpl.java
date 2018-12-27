package com.cc.tongxundi.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.plugin.im.dao.bean.RXConversation;
import com.yuntongxun.plugin.im.manager.bean.RETURN_TYPE;
import com.yuntongxun.plugin.im.manager.port.OnBindViewHolderListener;
import com.yuntongxun.plugin.im.manager.port.OnIMBindViewListener;
import com.yuntongxun.plugin.im.manager.port.OnMessagePreproccessListener;
import com.yuntongxun.plugin.im.manager.port.OnNotificationClickListener;
import com.yuntongxun.plugin.im.manager.port.OnReturnIdsCallback;
import com.yuntongxun.plugin.im.manager.port.OnReturnIdsClickListener;
import com.yuntongxun.plugin.im.ui.chatting.model.BaseChattingRow;
import com.yuntongxun.plugin.im.ui.conversation.ConversationAdapter;

public class IMManagerImpl implements OnIMBindViewListener, OnNotificationClickListener, OnReturnIdsClickListener, OnBindViewHolderListener, OnMessagePreproccessListener {

    private static IMManagerImpl instance = new IMManagerImpl();

    public static IMManagerImpl getInstance() {
        return instance;
    }

    private IMManagerImpl() {
    }

    @Override
    public BaseChattingRow onBindView(ECMessage ecMessage, BaseChattingRow baseChattingRow) {
        return null;
    }

    @Override
    public View onBindView(Context context, View view, RXConversation rxConversation, ConversationAdapter.BaseConversationViewHolder baseConversationViewHolder) {
        return null;
    }

    @Override
    public String onBindNickName(Context context, String s) {
        return null;
    }

    @Override
    public void OnAvatarClickListener(Context context, String s) {

    }

    @Override
    public String onBindAvatarByUrl(Context context, String s) {
        return null;
    }

    @Override
    public boolean dispatchMessage(ECMessage ecMessage) {
        return false;
    }

    @Override
    public void onNotificationClick(Context context, String s, Intent intent) {

    }

    @Override
    public void onReturnIdsClick(Context context, RETURN_TYPE return_type, OnReturnIdsCallback onReturnIdsCallback, String... strings) {

    }
}
