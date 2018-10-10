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
import com.cc.tongxundi.bean.VideoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment {
    private RecyclerView mRvInfo;
    private VideoAdapter mAdapter;
    private SwipeRefreshLayout mSrl;

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
                testData();
            }
        });
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                testData();
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
        testData();
    }

    private void testData() {
        String videoUrl = "http://cdn.weshow.me/sg/video/heI3/e3ce55a5489033ed7a572e7159223bd1.mp4";
        String url = "http://imgsrc.baidu.com/forum/w=580/sign=4899df59da1373f0f53f6f97940e4b8b/dc5095da81cb39db2f272074db160924aa183045.jpg";
        List<VideoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            VideoBean infoBean = new VideoBean();
            infoBean.setVideoDes("热烈祝贺通讯帝正式上线");
            infoBean.setVideoCover(url);
            infoBean.setVideoUrl(videoUrl);
            list.add(infoBean);
        }
        if (mAdapter != null) {
            mAdapter.addMore(list);
        }
        mAdapter.loadMoreComplete();
        mSrl.setRefreshing(false);

    }
}
