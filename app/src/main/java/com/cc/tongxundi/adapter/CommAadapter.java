package com.cc.tongxundi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.BaseBean;
import com.cc.tongxundi.bean.CommentBean;
import com.cc.tongxundi.bean.VideoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CommAadapter extends BaseQuickAdapter<CommentBean,BaseViewHolder> {


    public CommAadapter(Context context) {
        super(R.layout.item_comment);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean item) {

    }
    private void loadMore(List<CommentBean> list){
        mData.addAll(list);
        notifyDataSetChanged();
    }

    private void refeData(List<CommentBean> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();

    }
}
