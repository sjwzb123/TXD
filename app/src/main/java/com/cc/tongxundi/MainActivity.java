package com.cc.tongxundi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.tongxundi.Fragment.DownFragment;
import com.cc.tongxundi.Fragment.IMFragment;
import com.cc.tongxundi.Fragment.InfoFragment;
import com.cc.tongxundi.Fragment.PostListFragment;
import com.cc.tongxundi.Fragment.SetFragment;
import com.cc.tongxundi.Fragment.VideoFragment;
import com.cc.tongxundi.im.IMHelper;
import com.cc.tongxundi.im.IMListener;
import com.cc.tongxundi.utils.SPManager;
import com.cc.tongxundi.view.NoScrollViewPager;
import com.tencent.ijk.media.player.pragma.DebugLog;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.plugin.im.ui.chatting.fragment.ConversationListFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by sjw on 2017/9/23.
 */

public class MainActivity extends BaseActivity {
    private MyAdapter mAdapter;
    private NoScrollViewPager mViewPager;
    private TabLayout mTableLayout;
    private String[] titles = {"私信", "下载", "资讯", "视频", "我的", "帖子"};
    private List<Fragment> mFragmentList = new ArrayList<>();
    private RelativeLayout mRlTitle;
    private TextView mTvTitle;
    private int REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private String TAG = "MainActivity";

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMHelper.getInstance().initIMSDK(this, new IMListener() {
            @Override
            public void onInitialized() {
                super.onInitialized();
                String userId = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
                IMHelper.getInstance().login(userId, this);
            }

            @Override
            public void onConnectState(ECDevice.ECConnectState state, ECError error) {
                super.onConnectState(state, error);
                if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        DebugLog.d(TAG, "==帐号异地登陆");
                    } else {
                        DebugLog.d(TAG, "==其他登录失败,错误码：" + error.errorCode+" err "+error.toString());
                    }
                    return;
                } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    DebugLog.i(TAG, "==登陆成功");
                }
            }
        });

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initData();
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager = (NoScrollViewPager) findViewById(R.id.main_viewpager);
        mViewPager.setNoScroll(true);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mAdapter);
        mTableLayout = (TabLayout) findViewById(R.id.main_tab);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
        mTableLayout.getTabAt(0).setIcon(R.drawable.ic_launcher);
        mTableLayout.getTabAt(1).setIcon(R.drawable.down_bg);
        mTableLayout.getTabAt(2).setIcon(R.drawable.info_bg);
        mTableLayout.getTabAt(3).setIcon(R.drawable.video_bg);
        mTableLayout.getTabAt(4).setIcon(R.drawable.user_bg);
        mTableLayout.getTabAt(5).setIcon(R.drawable.ic_launcher);
        mRlTitle = (RelativeLayout) findViewById(R.id.rl_title);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTvTitle.setText(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                mRlTitle.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initData() {
        DownFragment downFragment = new DownFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ConversationListFragment.EXTRA_SHOW_TITLE, false);
        Fragment mFragment = Fragment.instantiate(this,
                ConversationListFragment.class.getName(), bundle);
        mFragmentList.add(mFragment);
        mFragmentList.add(downFragment);
        mFragmentList.add(new InfoFragment());
        mFragmentList.add(new VideoFragment());
        mFragmentList.add(new SetFragment());
        mFragmentList.add(new PostListFragment());
//        ActionFragment actionFragment = new ActionFragment();
//        mFragmentList.add(actionFragment);
//        InforFragment inforFragment = new InforFragment();
//        mFragmentList.add(inforFragment);


    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }


    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", "checkPermission: 已经授权！");
        }
    }

}
