package com.cc.tongxundi.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.PostBean;
import com.cc.tongxundi.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class PostDesActivity extends BaseActivity {
    private ImageView mIvUser;
    private TextView mTvNick;
    private TextView mTitme;
    private LinearLayout mLlimg;
    private TextView mTvIm;
    @Override
    public int getContentView() {
        return R.layout.activity_post_detail;
    }

    @Override
    public void initView() {
        mIvUser=findViewById(R.id.iv_user);
        mTvNick=findViewById(R.id.tv_user_name);
        mTitme=findViewById(R.id.tv_time);
        mLlimg=findViewById(R.id.ll_img);
        mTvIm=findViewById(R.id.tv_im);
        mTvIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void bindDataToView(PostBean.content item){
        mTvNick.setText(item.getNickname());
        mTitme.setText(TimeUtils.getChatTimeStr(item.getCreateTime()));

    }

}
