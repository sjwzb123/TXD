package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.activity.ChatActivity;

public class IMFragment extends BaseFragment {

    private RecyclerView mRvIm;
    private SwipeRefreshLayout mSrl;
    private EditText mEtIm;
    private Button mBtnPush;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_im, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mRvIm = view.findViewById(R.id.rv_im);
        mBtnPush = view.findViewById(R.id.btn_push_im);
        mEtIm = view.findViewById(R.id.et_im);
        mSrl = view.findViewById(R.id.srf_im);
        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatActivity.startActivity(getContext());
            }
        });

    }

    private void pushMsg() {
        String msg = mEtIm.getText().toString();

    }

}
