package com.cc.tongxundi.bean;

import java.util.List;

public class PostBean extends BaseBean{
    /**
     * 15 帖子列表  /api/post/list GET
     *       参数 pageNo
     *       返回：
     *        {
     *          "msg": "ok",
     *          "data": {
     *             "content": [
     *               {
     *                   "content": "",
     *                   "id": "0",
     *                   "nickname": "hahahha",
     *                   "theme": "压屏",
     *                   "thumbnailUrls": [
     *                       "http://sdfwe.jpg",
     *                       "http://sdfwef.jpg"
     *                   ],
     *                   "title": "testq",
     *                   "userId": 1,
     *                   "createTime":182811212212
     *               }
     *             ],

     *        }
     */

    private String content;
    private String id;
    private String nickName;
    private String theme;
    private List<String> thumbnailUrls;
    private String title;
    private int userId;
    private long createTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<String> getThumbnailUrls() {
        return thumbnailUrls;
    }

    public void setThumbnailUrls(List<String> thumbnailUrls) {
        this.thumbnailUrls = thumbnailUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}