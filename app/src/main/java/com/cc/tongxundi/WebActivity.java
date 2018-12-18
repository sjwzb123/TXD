package com.cc.tongxundi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by sjw on 2017/9/27.
 */

public class WebActivity extends BaseActivity {
    private WebView webView;
    private View shareView;
    private String mUrl;
    private String APP_ID = "wxec02cb34c2afe420";
    private IWXAPI api;
    // private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private String newsTitle;


    private LinearLayout llShare;
    private ImageView ivChat;
    private ImageView ivF;

    public static void startActiviyt(Context context, String title, String url, boolean isShare) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("isShare", isShare);
        context.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_web;
    }

    public void initView() {
        api = WXAPIFactory.createWXAPI(this, APP_ID);
        llShare = (LinearLayout) findViewById(R.id.ll_share);

        ivChat = (ImageView) findViewById(R.id.iv_weixin);
        ivF = (ImageView) findViewById(R.id.iv_weixin_m);
        ivChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneSession);
                llShare.setVisibility(View.GONE);
            }
        });
        ivF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareToWxinChat(SendMessageToWX.Req.WXSceneTimeline);
                llShare.setVisibility(View.GONE);
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareView = findViewById(R.id.btn_share);
        findViewById(R.id.iv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
        shareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // share();
                share();
            }
        });
        mUrl = getIntent().getStringExtra("url");
        newsTitle = getIntent().getStringExtra("title");
        boolean b = getIntent().getBooleanExtra("isShare", false);
        if (b) {
            shareView.setVisibility(View.VISIBLE);
        } else {
            // shareView.setVisibility(View.GONE);
        }
        webView = (WebView) findViewById(R.id.wb);
        webView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llShare.setVisibility(View.GONE);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("gb2312");
        webView.loadUrl(mUrl);

//        mUrl = "https://app.tjcaw.gov.cn/news/279/";
        // mUrl="http://2449.vod.myqcloud.com/2449_43b6f696980311e59ed467f22794e792.f20.mp4";
        // mUrl="http://139.199.38.84/video/20171120/01.mp4";
        // mUrl="http://baidu.iqiyi.com/kan/aFj1b?fr=v.baidu.com/&page=videoMultiNeed";
        // mUrl = "http://cdn.snapshow.live/dev/sg/test/video.html";
//        mUrl = "https://139.199.38.84/api/webNews/video";
        //webView.loadUrl(mUrl);

        // 声明WebSettings子类
        // WebSettings webSettings = webView.getSettings();

        // 如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // webSettings.setJavaScriptEnabled(true);
        //
        // // 设置自适应屏幕，两者合用
        // webSettings.setUseWideViewPort(true); // 将图片调整到适合webview的大小
        // webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //
        // // 缩放操作
        // webSettings.setSupportZoom(true); // 支持缩放，默认为true。是下面那个的前提。
        // webSettings.setBuiltInZoomControls(true); // 设置内置的缩放控件。若为false，则该WebView不可缩放
        // webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
        // webView.getSettings().setMediaPlaybackRequiresUserGesture(false);

        // 其他细节操作
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 关闭webview中缓存
        // webSettings.setAllowFileAccess(true); // 设置可以访问文件
        // webSettings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        // webSettings.setLoadsImagesAutomatically(true); // 支持自动加载图片
        // webSettings.setDefaultTextEncodingName("utf-8");// 设置编码格式
//        WebChromeClient wvcc = new WebChromeClient();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setUseWideViewPort(true); // 关键点
//        webSettings.setAllowFileAccess(true); // 允许访问文件
//        webSettings.setSupportZoom(true); // 支持缩放
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        webView.setWebChromeClient(wvcc);
//        WebViewClient wvc = new WebViewClient() {
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
//                return true;
//            }
//        };
//        webView.setWebViewClient(wvc);

    }

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        // settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(false);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setWebViewClient(new WebViewClient());
    }

    private void share() {
        llShare.setVisibility(View.VISIBLE);

    }


    private void shareToWxinChat(int targetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = mUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        newsTitle = TextUtils.isEmpty(newsTitle) ? "通讯帝" : newsTitle;
        msg.title = newsTitle;
        msg.description = newsTitle;

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        // Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        // bmp.recycle();
        msg.thumbData = WXUitls.bmpToByteArray(bmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();

        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = targetScene;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
