package com.cc.tongxundi.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UserBean extends BaseBean{
    /**
     * "createTime": 1539747998000,
     *      * 		"id": 2,
     *      * 		"loginName": "13164232910",
     *      * 		"phone": "13164232910"
     */
    
    private long createTime;
    private int id;
    private String phone;

    @Generated(hash = 2060111914)
    public UserBean(long createTime, int id, String phone) {
        this.createTime = createTime;
        this.id = id;
        this.phone = phone;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "createTime=" + createTime +
                ", id=" + id +
                ", phone='" + phone + '\'' +
                '}';
    }


}
