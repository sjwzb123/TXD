package com.cc.tongxundi.down.Http;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public interface HttpNetCallBack<T> {
    void onFailure(String errCode, String errMsg);

    void onSucc(String response, int totalPages, int pageStart);
}
