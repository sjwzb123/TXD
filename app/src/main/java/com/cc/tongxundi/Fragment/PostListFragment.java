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
import com.cc.tongxundi.R;
import com.cc.tongxundi.adapter.PostAadapter;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

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
        mRv = view.findViewById(R.id.rv_post);
        mPostAdapter = new PostAadapter(getContext());
        mRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mPostAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
               // getNetData();
            }
        }, mRv);
        view.findViewById(R.id.btn_push_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            getNetData();
        }
    }

    private void getNetData() {
        HttpUtil.getInstance().getPostList(pageno, new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response, int totalPages, int pageStart) {
                pageno++;

            }
        });
    }
}
