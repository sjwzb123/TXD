package com.cc.tongxundi.video;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.cc.tongxundi.R;
import com.cc.tongxundi.activity.VideoDesActivity;
import com.cc.tongxundi.activity.VideoPlayActivity;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.logging.Logger;

public class MediaPlayView extends FrameLayout implements MediaPlayManager.PlayListener {
    private ProgressBar mPbLoading;
    private TXCloudVideoView mVideoView;
    private ImageView mIvCover;
    private String TAG = "MediaPlayView";
    private ImageView mIvPause;
    private ProgressBar mPbPlay;
    private ImageView mIvScreen;
    private String url;
    private OnScreenOnClickListener mLitener;


    public MediaPlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.play_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }

    public void setLitener(OnScreenOnClickListener litener) {
        this.mLitener = litener;
    }

    private void initView() {
        mIvScreen = (ImageView) findViewById(R.id.iv_screen);
        mPbPlay = (ProgressBar) findViewById(R.id.pb_play);
        mPbLoading = (ProgressBar) findViewById(R.id.pb);
        mVideoView = (TXCloudVideoView) findViewById(R.id.tx_video);
        mIvCover = (ImageView) findViewById(R.id.iv_cover);
        mIvPause = (ImageView) findViewById(R.id.iv_pause);
        mIvPause.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MediaPlayManager.getInstance(getContext()).isPlaying()) {
                    mIvPause.setImageResource(R.drawable.icon_pause);
                    MediaPlayManager.getInstance(getContext()).pause();
                } else {
                    MediaPlayManager.getInstance(getContext()).resume();
                    mIvPause.setImageResource(R.drawable.icon_restart);
                }
            }
        });
        mIvScreen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration mConfiguration = getContext().getResources().getConfiguration(); //获取设置的配置信息
                int ori = mConfiguration.orientation; //获取屏幕方向
                if (mLitener != null)
                    mLitener.onClick(ori != mConfiguration.ORIENTATION_LANDSCAPE);
                //((Activity) getContext()).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                // VideoPlayActivity.startActivity(getContext(), url);


            }
        });
        findViewById(R.id.view_mark).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIvPause.performClick();
            }
        });

    }

    public ImageView getIvScreen() {
        return mIvScreen;
    }

    public void startPlay(Context context, String url) {
        this.url = url;
        mIvPause.setImageResource(R.drawable.icon_restart);
        mPbLoading.setVisibility(VISIBLE);
        MediaPlayManager.getInstance(context).setPlayListener(this);
        MediaPlayManager.getInstance(context).setSurfaceView(mVideoView);
        MediaPlayManager.getInstance(context).startPlay(url);
    }

    public void stopPlay(Context context) {
        mPbLoading.setVisibility(GONE);
        MediaPlayManager.getInstance(context).stopPlay();
        mIvCover.setVisibility(VISIBLE);
    }

    public ImageView getVideoCover() {
        return mIvCover;
    }

    @Override
    public void onPlayComplete() {

    }

    @Override
    public void onPlayBegin() {
        mPbLoading.setVisibility(GONE);
        mIvCover.setVisibility(GONE);
    }

    @Override
    public void onPlayProgress(int dur, int pro) {
        mPbPlay.setMax(dur);
        mPbPlay.setProgress(pro);
        mPbPlay.setSecondaryProgress(pro);
    }

    public interface OnScreenOnClickListener {
        void onClick(boolean isToLand);
    }

}
