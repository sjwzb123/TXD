package com.cc.tongxundi.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.cc.tongxundi.R;
import com.cc.tongxundi.adapter.CommAadapter;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;

public class CommLayout extends FrameLayout {
    private RecyclerView mRv;
    private CommAadapter mAdapter;
    public CommLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_comm_layout,this,false);
        mRv= (RecyclerView) findViewById(R.id.rl_comm);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRv.setAdapter(mAdapter);

    }

    public void getNetData(){
       // HttpUtil.getInstance().
    }


}
