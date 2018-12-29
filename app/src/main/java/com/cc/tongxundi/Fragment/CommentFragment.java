package com.cc.tongxundi.Fragment;

import android.content.Context;
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
import com.cc.tongxundi.bean.CommentBean;
import com.cc.tongxundi.down.Http.CommonResultBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpResultCallback;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.SPManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;

import okhttp3.Request;

public class CommentFragment extends BaseFragment {
    private RecyclerView mRvComment;
    private SwipeRefreshLayout mSwr;
    private CommAadapter mAdapter;
    private int pageno;
    private String TAG = "CommentFragment";
    private EditText mEtComment;
    private Button mBtnPush;
    private int groupType;
    private int groupid;
    private static final String KEY_GROUP_TYPE = "groupType";
    private static final String KEY_GROUP_ID = "groupid";

    public static CommentFragment newInstance(int groupType,int  groupid){
        Bundle bundle=new Bundle();
        bundle.putInt(KEY_GROUP_ID,groupid);
        bundle.putInt(KEY_GROUP_TYPE,groupType);
        CommentFragment commentFragment=new CommentFragment();
        commentFragment.setArguments(bundle);
        return commentFragment;

    }
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        groupType = getArguments().getInt(KEY_GROUP_TYPE);
        groupid = getArguments().getInt(KEY_GROUP_ID);

    }


    private void initView(View view) {
        mRvComment = view.findViewById(R.id.rv_comment);
        mSwr = view.findViewById(R.id.srf_comment);
        mRvComment.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new CommAadapter(getContext());
        mRvComment.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(mRvComment);
        mAdapter.setEmptyView(R.layout.item_null);
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

        HttpUtil.getInstance().getCommentList(pageno, groupType, groupid, new HttpResultCallback<CommonResultBean<CommentBean>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonResultBean<CommentBean> response) {
                mAdapter.loadMore(response.getData().getContent());

            }

        });
    }

    private void pushComment() {
        String userId = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
        String comment = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(comment)) {
            Toast.makeText(getContext(), " 评论不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        HttpUtil.getInstance().pushComment(userId, comment, groupType, groupid, userId, "2", new HttpResultCallback<Object>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtil.showMessage("发表评论失败，请稍后再试");

            }

            @Override
            public void onResponse(Object response) {
                hideKeyboard();
                ToastUtil.showMessage("发表评论成功");

            }
        });


    }
}
