package com.cc.tongxundi.bean;

public class CommentBean extends BaseBean {
   /**
    * "content": "这个内容不错5",
    *                 "createTime": 1544962285000,
    *                 "groupId": 1,
    *                 "groupType": 1,
    *                 "id": 4,
    *                 "nickname": "hahahha",
    *                 "replyId": 0,
    *                 "replyUserId": 0,
    *                 "replyUserNickname":"",
    *                 "userId": 1
    *
    *
    * */

   private String content;
   private long createTime;
   private int groupId;
   private int groupType;
   private  int id ;
   private String nickname;
   private int replyId;
   private int replyUserId;
   private String replyUserNickname;
   private int userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(int replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyUserNickname() {
        return replyUserNickname;
    }

    public void setReplyUserNickname(String replyUserNickname) {
        this.replyUserNickname = replyUserNickname;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
