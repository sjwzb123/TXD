package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.Fragment.CommentFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.PostBean;
import com.cc.tongxundi.db.UserDbHelper;
import com.cc.tongxundi.im.IMManagerImpl;
import com.cc.tongxundi.utils.TimeUtils;
import com.cc.tongxundi.view.ItemImg;
import com.yuntongxun.plugin.im.manager.IMPluginManager;

import java.util.concurrent.TimeUnit;

public class PostDesActivity extends BaseActivity {
    private ImageView mIvUser;
    private TextView mTvNick;
    private TextView mTvContent;
    private TextView mTitme;
    private LinearLayout mLlimg;
    private TextView mTvIm;
    private static final String KEY_ITEM = "KEY_POST_ITEM";
    private PostBean.content mItem;

    public static void startActivity(Context context, PostBean.content content) {
        Intent intent = new Intent(context, PostDesActivity.class);
        intent.putExtra(KEY_ITEM, content);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_post_detail;
    }

    @Override
    public void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvContent = findViewById(R.id.tv_content);
        mIvUser = findViewById(R.id.iv_user);
        mTvNick = findViewById(R.id.tv_user_name);
        mTitme = findViewById(R.id.tv_time);
        mLlimg = findViewById(R.id.ll_iv);
        mTvIm = findViewById(R.id.tv_im);
        mTvIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = String.valueOf(mItem.getUserId());
                IMManagerImpl.getInstance().addUser(mItem.getUser());
                UserDbHelper.getInstance().insertUser(mItem.getUser());
                IMPluginManager.getManager().startChatting(PostDesActivity.this, id);
            }
        });
        findViewById(R.id.tv_more_comment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentActivity.startActivity(mContext,"3",mItem.getId());
            }
        });
        bindDataToView();

    }

    private void bindDataToView() {
        mItem = (PostBean.content) getIntent().getSerializableExtra(KEY_ITEM);

        mTvNick.setText(mItem.getNickname());
        mTitme.setText(TimeUtils.getChatTimeStr(mItem.getCreateTime()));
        mTvContent.setText(mItem.getContent());
        for (String path : mItem.getThumbnailUrls()) {
            ImageView itemImg = new ImageView(this);
            Glide.with(this).load(path).into(itemImg);
            mLlimg.addView(itemImg);
        }

    }

}
