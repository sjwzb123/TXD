package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.PushPostActivity;
import com.cc.tongxundi.R;
import com.cc.tongxundi.adapter.PostAadapter;
import com.cc.tongxundi.bean.PostBean;
import com.cc.tongxundi.down.Http.CommonResultBean;
import com.cc.tongxundi.down.Http.CommonResultListBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpResultCallback;
import com.cc.tongxundi.down.HttpUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.ijk.media.player.pragma.DebugLog;

import java.util.List;

import okhttp3.Request;

public class PostListFragment extends BaseFragment {
    private RecyclerView mRv;
    private SwipeRefreshLayout mSrl;
    private PostAadapter mPostAdapter;
    private int pageno;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_post, container, false);
    }

    private void initView(View view) {
        mSrl = view.findViewById(R.id.srf_post);
        mSrl.setEnabled(true);
        mRv = view.findViewById(R.id.rv_post);
        mPostAdapter = new PostAadapter(getContext());
        mRv.setAdapter(mPostAdapter);
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPostAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                 getNetData(false);
            }
        }, mRv);

        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData(true);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getNetData(false);
        }
    }

    private void getNetData(final boolean isFresh) {
        HttpUtil.getInstance().getPostList(pageno, new HttpResultCallback<CommonResultBean<PostBean>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonResultBean<PostBean> response) {
                pageno++;
                if (isFresh){
                    mSrl.setEnabled(false);
                    mPostAdapter.refeData(response.getData().getContent());
                }else {
                    mPostAdapter.loadMore(response.getData().getContent());
                }


            }


        });
    }
}
