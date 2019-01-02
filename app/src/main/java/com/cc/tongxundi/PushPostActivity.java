package com.cc.tongxundi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cc.tongxundi.adapter.ThemeAadapter;
import com.cc.tongxundi.bean.ThemeBean;
import com.cc.tongxundi.down.Http.CommonResultBean;
import com.cc.tongxundi.down.Http.CommonResultListBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpResultCallback;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.SPManager;
import com.cc.tongxundi.view.ItemImg;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tencent.ijk.media.player.pragma.DebugLog;
import com.yuntongxun.plugin.common.common.utils.ToastUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Request;

public class PushPostActivity extends BaseActivity {
    private String TAG = "PushPostActivity";
    private EditText mEtContent;
    private TextView mBtnPush;
    private ImageView mIvAdd;
    private LinearLayout mLlImg;
    private int REQUEST_CODE_CHOOSE = 0x01;
    private int MAX_SELECT = 4;
    private ArrayList<String> mSelected = new ArrayList<>();
    private String title;
    private RecyclerView mThemeRv;
    private ThemeAadapter mThemeAdapter;
    private TextView mTvTheme;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PushPostActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_push_post;
    }

    @Override
    public void initView() {
        mTvTheme = findViewById(R.id.tv_theme);
        mThemeAdapter = new ThemeAadapter(this);
        mThemeRv = findViewById(R.id.rl_theme_list);
        mThemeRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mThemeRv.setAdapter(mThemeAdapter);
        mEtContent = findViewById(R.id.et_content);
        mBtnPush = findViewById(R.id.btn_push_post);
        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(title)) {
                    ToastUtil.showMessage("主题不能为空");
                    return;
                }

                String content = mEtContent.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showMessage("内容不能为空");
                    return;
                }

                mLoadView.show();
                if (mSelected.size() > 0) {
                    uploadPic();
                } else {
                    pusPost(null);
                }
            }
        });
        mIvAdd = findViewById(R.id.iv_add);
        mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPic();
            }
        });
        mLlImg = findViewById(R.id.ll_img);

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getThemeList();
        mThemeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showMessage("你选择的主题是" + mThemeAdapter.getItem(position).getThemeName());
                title = mThemeAdapter.getItem(position).getThemeName();
                mTvTheme.setText(mThemeAdapter.getItem(position).getThemeName());
                mThemeRv.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.rl_theme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mThemeRv.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getThemeList() {
        HttpUtil.getInstance().getThemeList(new HttpResultCallback<CommonResultListBean<ThemeBean>>() {
            @Override
            public void onError(String msg) {

            }

            @Override
            public void onResponse(CommonResultListBean<ThemeBean> response) {
                if (response != null && response.getData().size() > 0) {
                    DebugLog.d(TAG, response.getData().toString());
                    mThemeAdapter.refeData(response.getData());
                }
            }


        });
    }

    private int getMaxSelect() {
        if (mSelected == null)
            return MAX_SELECT;
        return MAX_SELECT - mSelected.size();

    }

    private void selectPic() {

        PhotoPicker.builder()
                .setPhotoCount(MAX_SELECT - mSelected.size())
                .setShowCamera(true)
                .setShowGif(false)
                .setPreviewEnabled(true)
                .start(this, PhotoPicker.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                addImgItem(photos);
            }
        }
    }

    private void addImgItem(final List<String> list) {
        mSelected.addAll(list);
        for (int i = 0; i < list.size(); i++) {
            ItemImg itemImg = new ItemImg(this);
            int w = getResources().getDimensionPixelOffset(R.dimen.dimen_81);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, w);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_5);
            itemImg.setIndex(i);
            itemImg.setDelImgListener(new ItemImg.DelImgListener() {
                @Override
                public void onDelImg(int index) {
                    mSelected.remove(index);
                }
            });
            itemImg.showDelImg(true);
            itemImg.setLayoutParams(params);
            itemImg.setData(list.get(i));
            mLlImg.addView(itemImg, 0);
        }

    }


    private void pusPost(List<String> picList) {
        String content = mEtContent.getText().toString();
        String userID = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
        HttpUtil.getInstance().postCreate(title, content, userID, picList, title, new HttpResultCallback<CommonResultBean<String>>() {
            @Override
            public void onError(String msg) {
                ToastUtil.showMessage("发帖失败");
                mLoadView.dismiss();

            }

            @Override
            public void onResponse(CommonResultBean<String> response) {
                mLoadView.dismiss();
                if (response.isStatus()) {
                    ToastUtil.showMessage("发贴成功");
                } else {
                    ToastUtil.showMessage("发贴失败");
                }


            }
        });
        //uploadPic();
        // HttpUtil.getInstance().postCreate(title, content, userId, list, theme, callBack);

    }

    public void uploadPic() {

        HttpUtil.getInstance().uploadFile(mSelected, new HttpResultCallback<CommonResultListBean<String>>() {
            @Override
            public void onError(String msg) {
                ToastUtil.showMessage("发贴失败，请稍后再试");
                mLoadView.dismiss();

            }

            @Override
            public void onResponse(CommonResultListBean<String> response) {
                if (response.isStatus()) {
                    pusPost(response.getData());
                }
            }


        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mThemeRv.getVisibility() == View.VISIBLE) {
                mThemeRv.setVisibility(View.GONE);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
