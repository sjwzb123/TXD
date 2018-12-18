package com.cc.tongxundi.adapter;

import android.content.Context;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.cc.tongxundi.R;
import com.cc.tongxundi.bean.DownBean;
import com.cc.tongxundi.utils.DownloadUtil;
import com.cc.tongxundi.utils.SPManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class DownAdapter extends BaseQuickAdapter<DownBean, BaseViewHolder> {
    SPManager spManager;
    String filePath = "根目录" + DownloadUtil.downFile;

    public DownAdapter(Context context) {
        super(R.layout.down_item);
        spManager = new SPManager(context);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownBean item) {
        helper.setText(R.id.tv_file_name, item.getFilename());
        helper.addOnClickListener(R.id.btn_down);
        boolean b = (boolean) spManager.getSharedPreference("down" + item.getFilename(), false);
        if (b) {
            ProgressBar pb = helper.getView(R.id.pb_down);
            pb.setProgress(100);
            pb.setSecondaryProgress(100);
            helper.setText(R.id.tv_down_file, "文件保存在" + filePath + "/" + item.getFilename());
            helper.setText(R.id.btn_down, "重新下载");
        } else {
            ProgressBar pb = helper.getView(R.id.pb_down);
            pb.setProgress(0);
            pb.setSecondaryProgress(0);
            helper.setText(R.id.btn_down, "下载");
            helper.setText(R.id.tv_down_file, "");
        }

        ImageView iv = helper.getView(R.id.iv_down);
        //Glide.with(mContext).load(item.)

    }


    public void addMore(List<DownBean> list) {
        if (list != null) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }


    public void refData(List<DownBean> list) {
        mData.clear();
        if (list != null) {
            mData.addAll(list);
        }
        notifyDataSetChanged();
    }
}
