package com.cc.tongxundi.adapter;

import android.content.Context;

import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.CommentBean;
import com.cc.tongxundi.bean.PostBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class PostAadapter extends BaseQuickAdapter<PostBean,BaseViewHolder> {


    public PostAadapter(Context context) {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean item) {

    }
    private void loadMore(List<PostBean> list){
        mData.addAll(list);
        notifyDataSetChanged();
    }

    private void refeData(List<PostBean> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();

    }
}
