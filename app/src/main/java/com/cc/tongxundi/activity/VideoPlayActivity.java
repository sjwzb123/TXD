package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.video.MediaPlayView;

public class VideoPlayActivity extends BaseActivity {
    private String url;
    private MediaPlayView mPlayView;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, VideoPlayActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_play;
    }

    @Override
    public void initView() {
        mPlayView = (MediaPlayView) findViewById(R.id.video);
        url = getIntent().getStringExtra("url");
        mPlayView.getIvScreen().setVisibility(View.GONE);
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
        if (url != null) {
            mPlayView.startPlay(getApplicationContext(), url);
        }
    }

    public void stopPlay() {
        mPlayView.stopPlay(getApplicationContext());
    }
}
