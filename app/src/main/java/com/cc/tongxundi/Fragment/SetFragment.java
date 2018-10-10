package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.activity.LoginActivity;
import com.cc.tongxundi.utils.SPManager;


public class SetFragment extends BaseFragment {
    private TextView mTvPhone;
    private TextView mTvAddr;
    private SPManager spManager;
    private RelativeLayout mRlUpdate;
    private RelativeLayout mRLClean;
    private Button mBtnOut;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTvAddr = (TextView) view.findViewById(R.id.tv_addr);
        mTvPhone = (TextView) view.findViewById(R.id.tv_user);
        mRLClean = (RelativeLayout) view.findViewById(R.id.rl_clean);
        mRlUpdate = (RelativeLayout) view.findViewById(R.id.rl_update);
        mRLClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "缓存已清除", Toast.LENGTH_SHORT).show();
            }
        });
        mRlUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "已经是最先版本", Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spManager.clear();
                getActivity().finish();
                LoginActivity.startActivity(getContext());
            }
        });
        initData();
    }

    private void initData() {
        spManager = new SPManager(getContext());
        String phone = (String) spManager.getSharedPreference(SPManager.KEY_PHONE, "1001");
        String addr = (String) spManager.getSharedPreference(SPManager.KEY_ADDR, "1001");
        mTvPhone.setText("用户" + phone);
        mTvAddr.setText(addr);

    }
}
