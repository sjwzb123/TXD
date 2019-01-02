package com.cc.tongxundi.adapter;

import android.content.Context;

import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.CommentBean;
import com.cc.tongxundi.bean.ThemeBean;
import com.cc.tongxundi.utils.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThemeAadapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> {
    private Context context;
    public ThemeAadapter(Context context) {
        super(R.layout.item_theme);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemeBean item) {
      helper.setText(R.id.tv_theme,item.getThemeName());

    }

    public void loadMore(List<ThemeBean> list) {
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void refeData(List<ThemeBean> list) {
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();

    }
    public void addOneItem(ThemeBean content) {
        mData.add(0,content);
        notifyDataSetChanged();

    }


}
