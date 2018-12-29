package com.cc.tongxundi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.BaseBean;
import com.cc.tongxundi.bean.CommentBean;
import com.cc.tongxundi.bean.VideoBean;
import com.cc.tongxundi.utils.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class CommAadapter extends BaseQuickAdapter<CommentBean.content, BaseViewHolder> {
    private Context context;
    public CommAadapter(Context context) {
        super(R.layout.item_comment);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentBean.content item) {
        ImageView iv = helper.getView(R.id.iv_user);
        helper.setText(R.id.tv_user_name, item.getNickname());
        helper.setText(R.id.tv_time, String.valueOf(TimeUtils.getChatTimeStr(item.getCreateTime())));
        helper.setText(R.id.tv_content, item.getContent());

    }

    public void loadMore(List<CommentBean.content> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void refeData(List<CommentBean.content> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();

    }
}
