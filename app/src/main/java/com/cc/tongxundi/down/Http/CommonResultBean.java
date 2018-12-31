package com.cc.tongxundi.down.Http;

public class CommonResultBean<T> {

    /**
     * {
     * "msg": "登陆成功",
     * "data": {
     * "createTime": 1539747998000,
     * "id": 2,
     * "loginName": "13164232910",
     * "phone": "13164232910"
     * },
     * "status": true
     * }
     */
    private String code;
    private T data;
    private String msg;
    private boolean status;
    private int totalPages;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
