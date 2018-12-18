package com.cc.tongxundi.Fragment;


import android.content.Intent;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.WebActivity;
import com.cc.tongxundi.adapter.InfoAdapter;
import com.cc.tongxundi.bean.InfoBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.GsonManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends BaseFragment {
    private RecyclerView mRvInfo;
    private InfoAdapter mAdapter;
    private SwipeRefreshLayout mSrl;
    private String TAG = "InfoFragment";
    private int pageNo;
    private boolean isRef;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frgment_info, null);
    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initData() {
        HttpUtil.getInstance().news(pageNo, new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSrl.setRefreshing(false);
                        mAdapter.loadMoreComplete();
                    }
                });


            }

            @Override
            public void onSucc(String response, final int totalPages, int pageStart) {
                pageNo++;
                final List<InfoBean> list = GsonManager.fromJsonToList(response, new TypeToken<List<InfoBean>>() {
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

    private void initView(View view) {
        mSrl = (SwipeRefreshLayout) view.findViewById(R.id.srf);
        mRvInfo = (RecyclerView) view.findViewById(R.id.rl_info);
        mRvInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new InfoAdapter();
        mRvInfo.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                InfoBean item = mAdapter.getItem(position);
                String url = "http://file.tongxundi.com/html/news-%s.html";
                WebActivity.startActiviyt(getContext(), item.getTitle(), String.format(url, String.valueOf(item.getId())), true);
            }
        });
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isRef = false;
                if (mAdapter.getData().size() > 10) {
                    initData();
                } else {
                    mAdapter.setEnableLoadMore(false);
                }
                //testData();
            }
        }, mRvInfo);
        mSrl.setRefreshing(true);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                isRef = true;
                initData();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mAdapter.getData().size() == 0) {
            // getNetData();
            initData();
        }

    }


}
