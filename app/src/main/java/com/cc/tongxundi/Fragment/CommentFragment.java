package com.cc.tongxundi.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cc.tongxundi.BaseFragment;
import com.cc.tongxundi.R;
import com.cc.tongxundi.adapter.CommAadapter;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

public class CommentFragment extends BaseFragment {
    private RecyclerView mRvComment;
    private SwipeRefreshLayout mSwr;
    private CommAadapter mAdapter;
    private int pageno;
    private String TAG = "CommentFragment";
    private EditText mEtComment;
    private Button mBtnPush;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment, container, false);
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
        mRvComment = view.findViewById(R.id.rv_comment);
        mSwr = view.findViewById(R.id.srf_comment);
        mRvComment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommAadapter(getContext());
        mRvComment.setAdapter(mAdapter);
        getNetData();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getNetData();

            }
        }, mRvComment);
        mEtComment = view.findViewById(R.id.et_comm);
        mBtnPush = view.findViewById(R.id.btn_push);
        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushComment();
            }
        });
    }

    private void getNetData() {

        HttpUtil.getInstance().getCommentList(pageno, 1, 1, new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {
                Log.d(TAG, "onFailure  code " + errCode + "  msg  " + errMsg);

            }

            @Override
            public void onSucc(String response, int totalPages, int pageStart) {
                pageno++;
                Log.d(TAG, "onSucc " + response);

            }
        });
    }

    private void pushComment() {
        String comment = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(getContext(), " 评论不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getInstance().pushComment(comment, 1, 1, "3", "2", new HttpNetCallBack() {
            @Override
            public void onFailure(String errCode, String errMsg) {

            }

            @Override
            public void onSucc(String response, int totalPages, int pageStart) {

            }
        });

    }
}
