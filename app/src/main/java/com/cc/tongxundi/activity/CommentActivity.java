package com.cc.tongxundi.activity;

import android.content.Context;
import android.content.Intent;

import com.cc.tongxundi.BaseActivity;
import com.cc.tongxundi.Fragment.CommentFragment;
import com.cc.tongxundi.R;

public class CommentActivity extends BaseActivity {
    private CommentFragment mFragment;
    public static final String GROUP_ID = "GROUP_ID";
    public static final String GROUP_TYPE = "GROUP_TYPE";

    public static void startActivity(Context context, String groupType, String groupId) {
        Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(GROUP_TYPE, groupType);
        intent.putExtra(GROUP_ID, groupId);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_comment;
    }

    @Override
    public void initView() {
        String groupId = getIntent().getStringExtra(GROUP_ID);
        String groupType = getIntent().getStringExtra(GROUP_TYPE);
        mFragment = CommentFragment.newInstance(groupType, groupId);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_container, mFragment).commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  getSupportFragmentManager().beginTransaction().remove(mFragment).commit();
    }
}
