package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.utils.DownloadUtil;
import com.cc.tongxundi.utils.SPManager;


public class DownFragment extends BaseFragment implements View.OnClickListener {
    private ImageView mIvDown;
    private Button mBtnDown;
    private ProgressBar mPb;
    private TextView mTvDownFile;
    private SPManager spManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_down, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spManager = new SPManager(getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mTvDownFile = (TextView) view.findViewById(R.id.tv_down_file);
        mBtnDown = (Button) view.findViewById(R.id.btn_down);
        mIvDown = (ImageView) view.findViewById(R.id.iv_down);
        mBtnDown.setOnClickListener(this);
        mPb = (ProgressBar) view.findViewById(R.id.pb_down);
        boolean isDown = (boolean) spManager.getSharedPreference(SPManager.KEY_IS_DOWN, false);
        if (isDown) {
            mBtnDown.setText("已下载，点击重新下载");
            String filePath = Environment.getExternalStorageDirectory() + DownloadUtil.downFile;
            mTvDownFile.setText("文件保存地址：" + filePath);
        } else {
            mBtnDown.setText("下载");
            mTvDownFile.setText("文件未下载");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_down:
                downFile();
                break;
        }
    }

    private void downFile() {
        mTvDownFile.setText("正在连接中");
        mBtnDown.setVisibility(View.GONE);
        DownloadUtil.get().download(DownloadUtil.downUrl, DownloadUtil.downFile, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spManager.put(SPManager.KEY_IS_DOWN, true);
                        mBtnDown.setText("已下载，点击重新下载");
                        mBtnDown.setVisibility(View.VISIBLE);
                        String filePath = Environment.getExternalStorageDirectory() + DownloadUtil.downFile;
                        mTvDownFile.setText("下载成功文件保存地址：" + filePath);
                        Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onDownloading(final int progress) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDownFile.setText("正在下载中");
                        mBtnDown.setVisibility(View.GONE);
                        mPb.setMax(100);
                        mPb.setSecondaryProgress(progress);
                        mPb.setProgress(progress);
                    }
                });

            }

            @Override
            public void onDownloadFailed() {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDownFile.setText("下载失败请重新下载");
                        mBtnDown.setText("下载失败请重新下载");
                        mBtnDown.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "下载失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
