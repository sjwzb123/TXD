package com.cc.tongxundi.down.Http;

public class CommonResultBean<T> {

    /**
     * {
     * 	"msg": "登陆成功",
     * 	"data": {
     * 		"createTime": 1539747998000,
     * 		"id": 2,
     * 		"loginName": "13164232910",
     * 		"phone": "13164232910"
     *        },
     * 	"status": true
     * }
     */
    private String code;
    private T data;
    private String msg;
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
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
