package com.cc.tongxundi.down;

import android.os.Build;
import android.text.LoginFilter;
import android.util.Log;

import com.cc.tongxundi.down.Http.HttpNetCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Http网络工具,基于OkHttp
 * Created by Cheny on 2017/05/03.
 */

public class HttpUtil {
    private String TAG = "HttpUtil";
    private static OkHttpClient mOkHttpClient;
    private static HttpUtil mInstance;
    private final static long CONNECT_TIMEOUT = 60;//超时时间，秒
    private final static long READ_TIMEOUT = 60;//读取时间，秒
    private final static long WRITE_TIMEOUT = 60;//写入时间，秒
    private static final String BASE_URL = "http://api.tongxundi.com/";
    private static final String NEWS_URL = BASE_URL + "api/news/list?pageNo=";
    private static final String VODE_URL = BASE_URL + "api/video/list?pageNo=";
    private static final String DOWN_URL = BASE_URL + "api/file/list?pageNo=";
    private static final String DOWN_FILE = BASE_URL + "api/file/download?id=";
    private static final String GET_CODE = BASE_URL + "api/user/send-code";
    private static final String LOGIN = BASE_URL + "api/user/login-by-code";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * @param url        下载链接
     * @param startIndex 下载起始位置
     * @param endIndex   结束为止
     * @param callback   回调
     * @throws IOException
     */
    public void downloadFileByRange(String url, long startIndex, long endIndex, Callback callback) throws IOException {
        // 创建一个Request
        // 设置分段下载的头信息。 Range:做分段数据请求,断点续传指示下载的区间。格式: Range bytes=0-1024或者bytes:0-1024
        Request request = new Request.Builder().header("RANGE", "bytes=" + startIndex + "-" + endIndex)
                .url(url)
                .build();
        doAsync(request, callback);
    }

    public void getContentLength(String url, Callback callback) throws IOException {
        // 创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        doAsync(request, callback);
    }

    /**
     * 同步GET请求
     */
    public void doGetSync(String url) throws IOException {
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        doSync(request);
    }

    /**
     * 异步请求
     */
    private void doAsync(Request request, Callback callback) throws IOException {
        //创建请求会话
        Call call = mOkHttpClient.newCall(request);
        //同步执行会话请求
        call.enqueue(callback);
    }

    /**
     * 同步请求
     */
    private Response doSync(Request request) throws IOException {
        //创建请求会话
        Call call = mOkHttpClient.newCall(request);
        //同步执行会话请求
        return call.execute();
    }


    /**
     * @return HttpUtil实例对象
     */
    public static HttpUtil getInstance() {
        if (null == mInstance) {
            synchronized (HttpUtil.class) {
                if (null == mInstance) {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * 构造方法,配置OkHttpClient
     */
    private HttpUtil() {
        //创建okHttpClient对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        mOkHttpClient = builder.build();
    }

    /**
     * okHttp post同步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     */
    private void requestPostBySynWithForm(String actionUrl, HashMap<String, String> paramsMap) {
        try {
            //创建一个FormBody.Builder
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : paramsMap.keySet()) {
                //追加表单信息
                builder.add(key, paramsMap.get(key));
            }
            //生成表单实体对象
            RequestBody formBody = builder.build();
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(formBody).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            if (response.isSuccessful()) {
                Log.e(TAG, "response ----->" + response.body().string());
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void login(String phone, String code, String address, HttpNetCallBack callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("phone", phone);
            jsonObject.putOpt("code", code);
            jsonObject.putOpt("address", address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), LOGIN, callBack);


    }

    public void getCode(String phone, HttpNetCallBack callBack) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.putOpt("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request(jsonObject.toString(), GET_CODE, callBack);
    }

    private static void request(String json, String url, final HttpNetCallBack callBack) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (callBack != null) {
                    callBack.onFailure("1", e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // DebugLog.d(TAG, "onResponse : body " + response.body().string());
                if (callBack != null) {
                    callBack.onSucc(response.body().string(), 0, 0);
                }

                // try {
                // JSONObject obj = new JSONObject(response.body().string());
                // DebugLog.d(TAG, "response " + obj.toString());
                // } catch (JSONException e) {
                // e.printStackTrace();
                // }
            }
        });
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }

    private void getDataAsynFromNet(String url, final HttpNetCallBack netCall) {
        //1 构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url(url).build();
        //2 将Request封装为Call
        Call call = mOkHttpClient.newCall(request);
        //3 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                netCall.onFailure(String.valueOf(1), e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String re = response.body().string();
                try {
                    JSONObject jsRes = new JSONObject(re);
                    int totalPages = jsRes.optInt("totalPages");
                    int pageStart = jsRes.optInt("pageStart");
                    JSONObject data = jsRes.optJSONObject("data");
                    netCall.onSucc(data.optString("content"), totalPages, pageStart);
                } catch (JSONException e) {
                    e.printStackTrace();
                    netCall.onFailure(String.valueOf(1), e.getMessage());
                }
                Log.d(TAG, "response: " + re);


            }
        });
    }

    public void news(int pageNo, HttpNetCallBack callBack) {
        String news_url = NEWS_URL + pageNo;
        getDataAsynFromNet(news_url, callBack);
    }

    public void vode(int pageNo, HttpNetCallBack callBack) {
        String news_url = VODE_URL + pageNo;
        getDataAsynFromNet(news_url, callBack);
    }

    public void dwon(int pageNo, HttpNetCallBack callBack) {
        String news_url = DOWN_URL + pageNo;
        getDataAsynFromNet(news_url, callBack);
    }

    public String getDownFileUrl(int id) {
        return DOWN_FILE + id;
    }
}
