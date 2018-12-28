package com.cc.tongxundi.view;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.R;

public class ItemImg extends RelativeLayout {
    private ImageView mIvDel;
    private ImageView mIv;

    public ItemImg(Context context) {
        super(context);
        initView(context);
    }

    public ItemImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_img, this);

        mIvDel = findViewById(R.id.iv_de);
        mIv = findViewById(R.id.iv);
        mIvDel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout v = (LinearLayout) getParent();
                v.removeView(ItemImg.this);
            }
        });

    }

    public void setData(String url) {
        Glide.with(getContext()).load(url).into(mIv);
    }
}
