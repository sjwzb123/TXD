package com.cc.tongxundi.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.PostBean;
import com.cc.tongxundi.utils.TimeUtils;
import com.cc.tongxundi.view.ItemImg;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAadapter extends BaseQuickAdapter<PostBean.content,BaseViewHolder> {
    private Context mContext;

    public PostAadapter(Context context) {
        super(R.layout.item_post);
        this.mContext=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, PostBean.content item) {
       // String url="http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3bee9ce4cc1b9d16fdfa60f7.jpg";
        CircleImageView iv=helper.getView(R.id.iv_user);
       // Glide.with(mContext).load(url).into(iv);
        helper.setText(R.id.tv_user_name,item.getNickname());
        helper.setText(R.id.tv_time,String.valueOf(TimeUtils.getChatTimeStr(item.getCreateTime())));
        helper.setText(R.id.tv_content,item.getContent());
        helper.addOnClickListener(R.id.tv_im);
        LinearLayout llImg=helper.getView(R.id.ll_iv);
        llImg.removeAllViews();
        for (String path:item.getThumbnailUrls()){
            ItemImg itemImg=new ItemImg(mContext);
            itemImg.setData(path);
            llImg.addView(itemImg);
        }


        //Glide.with(mContext).load(item.get)

    }
    public void loadMore(List<PostBean.content> list){
        mData.addAll(list);
        notifyDataSetChanged();
    }

    public void refeData(List<PostBean.content> list){
        mData.clear();
        mData.addAll(list);
        notifyDataSetChanged();

    }
}
