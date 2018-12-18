package com.cc.tongxundi.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.VideoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {
    public VideoAdapter() {
        super(R.layout.item_video);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoBean item) {
        helper.setText(R.id.tv_des, item.getDescription());
        ImageView iv = helper.getView(R.id.iv_cover);
        Glide.with(mContext).load(item.getThumbnailUrl()).into(iv);
    }

    public void addMore(List<VideoBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


    public void refData(List<VideoBean> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }
}
