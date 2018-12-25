package com.cc.tongxundi.im;

import android.content.Context;

import com.tencent.ijk.media.player.pragma.DebugLog;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

public class IMHelper {
    private static final String APPKEY = "8aaf07086537124901665ca1c577056c";
    private static final String APPTOKEN = "17c2c07f45a89072724b2ee9afe1f1f5";
    private static final String TAG = "IMHelper";
    private String userId="13164232910";
    public void initIMSDK(Context context) {
        if (!ECDevice.isInitialized()) {
            ECDevice.initial(context, new ECDevice.InitListener() {
                @Override
                public void onInitialized() {

                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
    }

    public void login() {
        //创建登录参数对象
        ECInitParams params = ECInitParams.createParams();
        //设置用户 登录账号
        params.setUserid(userId);
        //设置AppId
        params.setAppKey(APPKEY);
        //设置AppToken
        params.setToken(APPTOKEN);
        //设置登陆验证模式：自定义登录方式
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
        ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            @Override
            public void onConnect() {

            }

            @Override
            public void onDisconnect(ECError ecError) {

            }

            @Override
            public void onConnectState(ECDevice.ECConnectState ecConnectState, ECError ecError) {
                if (ecConnectState == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (ecError.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        DebugLog.d(TAG, "==帐号异地登陆");
                    } else {
                        DebugLog.i(TAG, "==其他登录失败,错误码：" + ecError.errorCode);
                    }
                    return;
                } else if (ecConnectState == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    DebugLog.d(TAG, "==登陆成功");
                }

            }
        });
        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
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
        });
        if (params.validate())
            ECDevice.login(params);

    }

    public void sendTXTMsg(String txt,String userId) {
        try {
            //创建一个待发送的消息ECmessage消息体
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
            //设置消息接收者,如果是发送群组消息，则接收者设置群组ID
            msg.setTo(userId);
            //创建一个文本消息体，并添加到消息对象中
            ECTextMessageBody msgBody = new ECTextMessageBody(txt.toString());
            //将消息体存放到ECMessage中
            msg.setBody(msgBody);
            //调用SDK发送接口发送消息到服务器
            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                }
            });
        } catch (Exception e) {
            // 处理发送异常
            DebugLog.e(TAG, "send message fail , e=" + e.getMessage());
        }

    }


}
