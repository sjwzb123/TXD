package com.cc.tongxundi.down;

public interface DownloadListner {
    public void onProgress(float v);

    void onPause();

    void onFinished();

    void onCancel();
}
