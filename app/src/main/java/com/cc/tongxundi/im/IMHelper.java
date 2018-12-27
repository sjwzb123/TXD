package com.cc.tongxundi.im;

import android.content.Context;

import com.cc.tongxundi.utils.SPManager;
//import com.tencent.ijk.media.player.pragma.DebugLog;
//import com.yuntongxun.ecsdk.ECChatManager;
//import com.yuntongxun.ecsdk.ECDevice;
//import com.yuntongxun.ecsdk.ECError;
//import com.yuntongxun.ecsdk.ECInitParams;
//import com.yuntongxun.ecsdk.ECMessage;
//import com.yuntongxun.ecsdk.OnChatReceiveListener;
//import com.yuntongxun.ecsdk.SdkErrorCode;
//import com.yuntongxun.ecsdk.im.ECMessageNotify;
//import com.yuntongxun.ecsdk.im.ECTextMessageBody;
//import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;

import java.util.List;

public class IMHelper  {
    public static final String APPKEY = "8aaf07086537124901665ca1c577056c";
    public static final String APPTOKEN = "17c2c07f45a89072724b2ee9afe1f1f5";
    private static final String TAG = "IMHelper";
    private String userId = "13164232910";
    private static IMHelper instance = new IMHelper();
    private SPManager spManager;
    private IMHelper() {

    }
    public static IMHelper getInstance(){
        return instance;
    }

    public void initIMSDK(Context context,IMListener listener) {
//        if (!ECDevice.isInitialized()) {
//            ECDevice.initial(context, listener);
//        }
    }

    public void login(String id,IMListener listener) {
//        //创建登录参数对象
//        ECInitParams params = ECInitarams.createParams();
////        //设置用户 登录账号
////        params.setUserid(id);
////        //设置AppIdP
//        params.setAppKey(APPKEY);
//        //设置AppToken
//        params.setToken(APPTOKEN);
//        //设置登陆验证模式：自定义登录方式
//        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
//        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
//        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);
//        ECDevice.setOnDeviceConnectListener(listener);
//        ECDevice.setOnChatReceiveListener(listener);
//        if (params.validate())
//            ECDevice.login(params);

    }

    public void sendTXTMsg(String txt, String userId,IMListener listener) {
//        try {
//            //创建一个待发送的消息ECmessage消息体
//            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
//            //设置消息接收者,如果是发送群组消息，则接收者设置群组ID
//            msg.setTo(userId);
//            //创建一个文本消息体，并添加到消息对象中
//            ECTextMessageBody msgBody = new ECTextMessageBody(txt);
//            //将消息体存放到ECMessage中
//            msg.setBody(msgBody);
//            //调用SDK发送接口发送消息到服务器
//            ECChatManager manager = ECDevice.getECChatManager();
//            manager.sendMessage(msg, listener);
//        } catch (Exception e) {
//            // 处理发送异常
//            DebugLog.e(TAG, "send message fail , e=" + e.getMessage());
//        }

    }


}
