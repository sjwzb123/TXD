package com.cc.tongxundi.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.cc.tongxundi.utils.SPManager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class SetFragment extends BaseFragment {
    private TextView mTvPhone;
    private TextView mTvAddr;
    private SPManager spManager;
    private RelativeLayout mRlUpdate;
    private RelativeLayout mRLClean;
    private Button mBtnOut;
    private String APP_ID = "wxec02cb34c2afe420";

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
                Toast.makeText(getContext(), "已经是最先版本", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        mTvPhone.setText("用户" + phone);
        mTvAddr.setText(addr);

    }
    private IWXAPI api;
    private LinearLayout llShare;
    private View shareView;
    private View ivChat;
    private View ivF;
    private void initShare(View view){
        api = WXAPIFactory.createWXAPI(getContext(), APP_ID);
        llShare = (LinearLayout) view.findViewById(R.id.ll_share);

        shareView = view.findViewById(R.id.iv_share);
        ivChat = (ImageView) view.findViewById(R.id.iv_weixin);
        ivF = (ImageView)view .findViewById(R.id.iv_weixin_m);
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
        String shareUrl="https://a.app.qq.com/o/simple.jsp?pkgname=com.cc.tongxundi&fromcase=40003";
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl =shareUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "通讯帝";
        msg.description = "大家快来用";

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
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
}
