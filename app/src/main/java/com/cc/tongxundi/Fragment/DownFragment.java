package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.cc.tongxundi.adapter.DownAdapter;
import com.cc.tongxundi.bean.DownBean;
import com.cc.tongxundi.bean.InfoBean;
import com.cc.tongxundi.down.DownloadListner;
import com.cc.tongxundi.down.DownloadManager;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.DownloadUtil;
import com.cc.tongxundi.utils.GsonManager;
import com.cc.tongxundi.utils.SPManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class DownFragment extends BaseFragment {
    private SPManager spManager;
    private RecyclerView mRv;
    private SwipeRefreshLayout mSrl;
    private DownAdapter mAdapter;
    private int pageNo;
    private boolean isRef;
    private boolean isDownIng;

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
        getNetData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    private void initView(View view) {
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mSrl.setRefreshing(true);
        mRv = (RecyclerView) view.findViewById(R.id.rv);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                pageNo = 0;
                getNetData();
            }
        });
        mAdapter = new DownAdapter(getContext());
        mRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mRv.setAdapter(mAdapter);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRef = false;
                getNetData();
            }
        }, mRv);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.btn_down:
                        if (isDownIng) {
                            Toast.makeText(getContext(), "有文件正在下载", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        isDownIng = true;
                        //downFile();
                        View itemView = mRv.getChildAt(position);
                        int downId = mAdapter.getItem(position).getId();
                        ProgressBar pb = (ProgressBar) itemView.findViewById(R.id.pb_down);
                        TextView tv = (TextView) itemView.findViewById(R.id.tv_down_file);
                        Button btnDown = (Button) itemView.findViewById(R.id.btn_down);
                        String fileName = mAdapter.getItem(position).getFilename();
                        String downUrl = mAdapter.getItem(position).getPath();
                        down(downUrl, DownloadUtil.downFile, fileName, tv, btnDown, pb);
                        break;
                }
            }
        });

    }

    private void getNetData() {
        HttpUtil.getInstance().dwon(pageNo, new HttpNetCallBack() {
            @Override
            public void onFailure(final String errCode, final String errMsg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "错误信息" + errMsg, Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onSucc(String response, final int totalPages, int pageStart) {
                final List<DownBean> list = GsonManager.fromJsonToList(response, new TypeToken<List<DownBean>>() {
                }.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRef) {
                            mAdapter.refData(list);
                        } else {
                            mAdapter.addMore(list);
                        }
                        mSrl.setRefreshing(false);
                        mAdapter.loadMoreComplete();
                        if (pageNo == totalPages || list.size() == 0) {
                            mAdapter.setEnableLoadMore(false);
                        }
                    }
                });
            }
        });
    }


    private void down(String downUrl, String saveDir, final String fileName, final TextView tvDownFile, final Button btnDown, final ProgressBar pb) {

        DownloadUtil.get().download(downUrl, saveDir, fileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                isDownIng = false;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        spManager.put("down" + fileName, true);
                        tvDownFile.setText("已下载，点击重新下载");
                        btnDown.setVisibility(View.VISIBLE);
                        String filePath = "根目录" + DownloadUtil.downFile + "/" + fileName;
                        tvDownFile.setText("下载成功文件保存地址：" + filePath);
                        Toast.makeText(getContext(), "下载成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onDownloading(final int progress) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvDownFile.setText("正在下载中");
                        btnDown.setVisibility(View.GONE);
                        pb.setMax(100);
                        pb.setSecondaryProgress(progress);
                        pb.setProgress(progress);
                    }
                });

            }

            @Override
            public void onDownloadFailed() {
                spManager.put("down" + fileName, false);
                isDownIng = false;
            }
        });
//        DownloadManager.getInstance().add(downUrl, new DownloadListner() {
//            @Override
//            public void onProgress(float v) {
//
//            }
//
//            @Override
//            public void onPause() {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//        });
//        DownloadManager.getInstance().download(downUrl);
    }
}
