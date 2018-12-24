package com.cc.tongxundi;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cc.tongxundi.down.Http.HttpNetCallBack;
import com.cc.tongxundi.down.HttpUtil;

import java.util.ArrayList;
import java.util.List;

public class PushPostActivity extends BaseActivity {
    private String TAG = "PushPostActivity";
    private EditText mEtContent;
    private EditText mEtTitle;
    private Button mBtnPush;

    @Override
    public int getContentView() {
        return R.layout.activity_push_post;
    }

    @Override
    public void initView() {
        mEtContent = findViewById(R.id.et_content);
        mEtTitle = findViewById(R.id.et_title);
        mBtnPush = findViewById(R.id.btn_push_post);
        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pusPost();
            }
        });

    }

    private HttpNetCallBack callBack = new HttpNetCallBack() {
        @Override
        public void onFailure(String errCode, String errMsg) {

        }

        @Override
        public void onSucc(String response, int totalPages, int pageStart) {

        }
    };

    private void pusPost() {
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();
        String theme = "dd";
        String userId = "dd";
        List<String> list = new ArrayList<>();
        String url = "http://b.hiphotos.baidu.com/image/pic/item/e4dde71190ef76c65e514b839016fdfaae516793.jpg";
        list.add(url);
        list.add(url);
        list.add(url);
        HttpUtil.getInstance().postCreate(title, content, userId, list, theme, callBack);

    }
}
