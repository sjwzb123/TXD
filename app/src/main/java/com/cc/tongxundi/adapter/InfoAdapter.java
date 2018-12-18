package com.cc.tongxundi.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.InfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class InfoAdapter extends BaseQuickAdapter<InfoBean, BaseViewHolder> {
    public InfoAdapter() {
        super(R.layout.item_info);
    }

    @Override
    protected void convert(BaseViewHolder helper, InfoBean item) {
        helper.setText(R.id.tv_info, item.getTitle());
        ImageView iv = helper.getView(R.id.iv_info);
        Glide.with(mContext).load(item.getThumbnailUrl()).into(iv);
    }

    public void addMore(List<InfoBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


    public void refData(List<InfoBean> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }

}
