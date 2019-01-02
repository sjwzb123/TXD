package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.Fragment.CommentFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.WXUitls;
import com.cc.tongxundi.bean.VideoBean;
import com.cc.tongxundi.video.MediaPlayView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class VideoDesActivity extends BaseActivity {
    private MediaPlayView mPlayView;
    public static final String VIDEO_KEY = "video";
    private VideoBean mVideoBean;
    private TextView mTvDes;
    private ImageView mIvBack;
    private ImageView mIvScreen;
    private LinearLayout llShare;
    private ImageView ivChat;
    private ImageView ivF;
    private String APP_ID = "wxec02cb34c2afe420";
    private IWXAPI api;
    private String newsTitle;
    private View shareView;
    private CommentFragment mCommentFragment;
    private String groupType = "1";

    public static void startActivity(Context context, VideoBean videoBean) {
        Intent intent = new Intent(context, VideoDesActivity.class);
        intent.putExtra(VIDEO_KEY, videoBean);
        context.startActivity(intent);

    }

    @Override
    public int getContentView() {
        return R.layout.activity_video;
    }

    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        llShare = (LinearLayout) findViewById(R.id.ll_share);
        shareView = findViewById(R.id.iv_share);
        ivChat = (ImageView) findViewById(R.id.iv_weixin);
        ivF = (ImageView) findViewById(R.id.iv_weixin_m);
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
        findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // share();
                share();
            }
        });
        mPlayView = (MediaPlayView) findViewById(R.id.video);
        mTvDes = (TextView) findViewById(R.id.tv_des);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initData();

    }

    private void share() {
        llShare.setVisibility(View.VISIBLE);

    }

    private void initData() {
        mVideoBean = (VideoBean) getIntent().getSerializableExtra(VIDEO_KEY);
        Glide.with(mContext).load(mVideoBean.getThumbnailUrl()).into(mPlayView.getVideoCover());
        mTvDes.setText(mVideoBean.getDescription());
        mCommentFragment = CommentFragment.newInstance(groupType, String.valueOf(mVideoBean.getId()));
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, mCommentFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPlay();
    }

    private void startPlay() {
        if (mVideoBean != null) {
            mPlayView.startPlay(getApplicationContext(), mVideoBean.getVideoUrl());
        }
    }

    public void stopPlay() {
        mPlayView.stopPlay(getApplicationContext());
    }

    private void shareToWxinChat(int targetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mVideoBean.getContentUrl();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        newsTitle = TextUtils.isEmpty(newsTitle) ? "通讯帝" : newsTitle;
        msg.title = newsTitle;
        msg.description = newsTitle;

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
