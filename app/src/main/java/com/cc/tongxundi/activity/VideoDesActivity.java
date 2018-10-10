package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.VideoBean;
import com.cc.tongxundi.video.MediaPlayView;

public class VideoDesActivity extends BaseActivity {
    private MediaPlayView mPlayView;
    public static final String VIDEO_KEY = "video";
    private VideoBean mVideoBean;
    private TextView mTvDes;
    private ImageView mIvBack;

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

    private void initData() {
        mVideoBean = (VideoBean) getIntent().getSerializableExtra(VIDEO_KEY);
        Glide.with(mContext).load(mVideoBean.getVideoCover()).into(mPlayView.getVideoCover());
        mTvDes.setText(mVideoBean.getVideoDes());
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
}
