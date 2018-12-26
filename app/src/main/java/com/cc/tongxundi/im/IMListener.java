package com.cc.tongxundi.im;

import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

public class IMListener implements ECDevice.InitListener,ECDevice.OnECDeviceConnectListener,OnChatReceiveListener,ECChatManager.OnSendMessageListener {
    @Override
    public void onInitialized() {

    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisconnect(ECError ecError) {

    }

    @Override
    public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {

    }

    @Override
    public void OnReceivedMessage(ECMessage ecMessage) {

    }

    @Override
    public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

    }

    @Override
    public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage ecGroupNoticeMessage) {

    }

    @Override
    public void onOfflineMessageCount(int i) {

    }

    @Override
    public int onGetOfflineMessage() {
        return 0;
    }

    @Override
    public void onReceiveOfflineMessage(List<ECMessage> list) {

    }

    @Override
    public void onReceiveOfflineMessageCompletion() {

    }

    @Override
    public void onServicePersonVersion(int i) {

    }

    @Override
    public void onReceiveDeskMessage(ECMessage ecMessage) {

    }

    @Override
    public void onSoftVersion(String s, int i) {

    }

    @Override
    public void onSendMessageComplete(ECError ecError, ECMessage ecMessage) {

    }

    @Override
    public void onProgress(String s, int i, int i1) {

    }
}
