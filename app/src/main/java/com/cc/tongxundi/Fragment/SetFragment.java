package com.cc.tongxundi.Fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.WXUitls;
import com.cc.tongxundi.activity.LoginActivity;
import com.cc.tongxundi.utils.AppUtils;
import com.cc.tongxundi.utils.SPManager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuntongxun.plugin.common.SDKCoreHelper;

import java.util.List;


public class SetFragment extends BaseFragment {
    private TextView mTvPhone;
    private TextView mTvAddr;
    private SPManager spManager;
    private RelativeLayout mRlUpdate;
    private RelativeLayout mRLClean;
    private Button mBtnOut;
    private String APP_ID = "wxec02cb34c2afe420";
    private String shareUrl = "https://a.app.qq.com/o/simple.jsp?pkgname=com.cc.tongxundi&fromcase=40003";

    private TextView mTvVersion;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initShare(view);
    }

    private void initView(View view) {
        mTvVersion = view.findViewById(R.id.tv_version);
        mTvVersion.setText("版本升级：" + AppUtils.getVersionName(getContext()));
        mTvAddr = (TextView) view.findViewById(R.id.tv_addr);
        mTvPhone = (TextView) view.findViewById(R.id.tv_user);
        mRLClean = (RelativeLayout) view.findViewById(R.id.rl_clean);
        mRlUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        mRLClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
            }
        });
        mRlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(), "已经是最先版本", Toast.LENGTH_SHORT).show();
                startYyb();
            }
        });
        view.findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKCoreHelper.logout();
                spManager.clear();
                getActivity().finish();
                LoginActivity.startActivity(getContext());
            }
        });
        view.findViewById(R.id.rl_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llShare.setVisibility(View.VISIBLE);

            }
        });
        initData();
    }

    private void initData() {
        spManager = new SPManager(getContext());
        String phone = (String) spManager.getSharedPreference(SPManager.KEY_PHONE, "1001");
        String addr = (String) spManager.getSharedPreference(SPManager.KEY_ADDR, "1001");
        mTvPhone.setText("用户：" + phone);
        mTvAddr.setText(addr);

    }

    private IWXAPI api;
    private LinearLayout llShare;
    private View shareView;
    private View ivChat;
    private View ivF;

    private void initShare(View view) {
        api = WXAPIFactory.createWXAPI(getContext(), APP_ID);
        llShare = (LinearLayout) view.findViewById(R.id.ll_share);

        shareView = view.findViewById(R.id.iv_share);
        ivChat = (ImageView) view.findViewById(R.id.iv_weixin);
        ivF = (ImageView) view.findViewById(R.id.iv_weixin_m);
        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneSession);
                llShare.setVisibility(View.GONE);
            }
        });
        ivF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneTimeline);
                llShare.setVisibility(View.GONE);
            }
        });
        view.findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
    }

    private void shareToWxinChat(int targetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "通讯帝";
        msg.description = "这里有最专业的维修技术";

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.icon_launcher);
        // Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        // bmp.recycle();
        msg.thumbData = WXUitls.bmpToByteArray(bmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = targetScene;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void startYyb() {

        //安装了应用宝
        if (isMobile_spExist()) {
            //跳到应用宝下载安装
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse("market://details?id=" + getContext().getPackageName());
            try {
                intent.setClassName("com.tencent.android.qqdownloader", "com.tencent.pangu.link.LinkProxyActivity");
                intent.setData(content_url);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //没安装
            openBrowser(getContext(), shareUrl);

        }

    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public static void openBrowser(Context context, String url) {
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    //检测是否安装了应用宝
    public boolean isMobile_spExist() {
        PackageManager manager = getActivity().getPackageManager();
        List<PackageInfo> pkgList = manager.getInstalledPackages(0);
        for (int i = 0; i < pkgList.size(); i++) {
            PackageInfo pI = pkgList.get(i);
            if (pI.packageName.equalsIgnoreCase("com.tencent.android.qqdownloader"))
                return true;
        }
        return false;
    }
}
