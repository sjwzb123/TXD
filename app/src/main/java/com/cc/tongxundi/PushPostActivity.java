package com.cc.tongxundi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cc.tongxundi.down.Http.CommonResultBean;
import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpResultCallback;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.utils.SPManager;
import com.cc.tongxundi.view.ItemImg;
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
    private RadioGroup mRG;
    private ArrayList<String> mSelected = new ArrayList<>();
    private String title;

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
        mEtContent = findViewById(R.id.et_content);
        mBtnPush = findViewById(R.id.btn_push_post);
        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pusPost();
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
        mRG = findViewById(R.id.rg);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = findViewById(i);
                title = rb.getText().toString();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
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

    private void addImgItem(List<String> list) {
        mSelected.addAll(list);
        for (String path : list) {
            ItemImg itemImg = new ItemImg(this);
            int w = getResources().getDimensionPixelOffset(R.dimen.dimen_62);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, w);
            params.leftMargin = getResources().getDimensionPixelOffset(R.dimen.dp_5);
            itemImg.setLayoutParams(params);
            itemImg.setData(path);
            mLlImg.addView(itemImg, 0);
        }

    }


    private void pusPost() {
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showMessage("主题不能为空");
            return;
        }

        String content = mEtContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.showMessage("内容不能为空");
            return;
        }
        String userID = (String) spManager.getSharedPreference(SPManager.KEY_UID, "");
        List<String> list = new ArrayList<>();
        list.add("http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3bee9ce4cc1b9d16fdfa60f7.jpg");
        list.add("http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3bee9ce4cc1b9d16fdfa60f7.jpg");
        list.add("http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/8435e5dde71190ef3bee9ce4cc1b9d16fdfa60f7.jpg");
        HttpUtil.getInstance().postCreate(title, content, userID, list, title, new HttpResultCallback<CommonResultBean<String>>() {
            @Override
            public void onError(Request request, Exception e) {
                ToastUtil.showMessage("发帖失败");

            }

            @Override
            public void onResponse(CommonResultBean<String> response) {
                ToastUtil.showMessage("发贴成功");

            }
        });
        //uploadPic();
        // HttpUtil.getInstance().postCreate(title, content, userId, list, theme, callBack);

    }

    public void uploadPic() {
        HttpUtil.getInstance().uploadFile(mSelected, new HttpResultCallback<CommonResultBean<String>>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(CommonResultBean<String> response) {

            }

        });
    }
}
