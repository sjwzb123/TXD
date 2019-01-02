package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.activity.VideoDesActivity;
import com.cc.tongxundi.adapter.InfoAdapter;
import com.cc.tongxundi.adapter.VideoAdapter;
import com.cc.tongxundi.bean.InfoBean;
import com.cc.tongxundi.bean.VideoBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.GsonManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment {
    private RecyclerView mRvInfo;
    private VideoAdapter mAdapter;
    private SwipeRefreshLayout mSrl;
    private int pageNo;
    private boolean isRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_video, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srl);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rl_video);
        mRvInfo.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter = new VideoAdapter();
        mRvInfo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDesActivity.startActivity(getContext(), mAdapter.getItem(position));
            }
        });
        mSrl.setRefreshing(true);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRef = true;
                pageNo = 0;
                getNetData();
            }
        });
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRef = false;
                getNetData();
            }
        }, mRvInfo);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mAdapter.getData().size() == 0) {
            getNetData();
        }
    }

    private void getNetData() {
        HttpUtil.getInstance().vode(pageNo, new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response, final int totalPages, int pageStart) {
                pageNo++;
                final List<VideoBean> list = GsonManager.fromJsonToList(response, new TypeToken<List<VideoBean>>() {
                }.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isRef) {
                            mAdapter.setEnableLoadMore(true);
                            mAdapter.refData(list);
                        } else {
                            mAdapter.addMore(list);
                        }
                        mAdapter.loadMoreComplete();
                        mSrl.setRefreshing(false);
                        if (pageNo == totalPages||list.size()==0) {
                            mAdapter.setEnableLoadMore(false);
                        }
                    }
                });
            }
        });
    }


}
