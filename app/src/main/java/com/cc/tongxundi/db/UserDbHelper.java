package com.cc.tongxundi.db;

import com.cc.tongxundi.bean.UserBean;
import com.cc.tongxundi.greendao.UserBeanDao;

import java.util.List;

public class UserDbHelper {
    private UserBeanDao mUserDao;
    private static UserDbHelper instance = new UserDbHelper();
    private UserDbHelper() {
        mUserDao = DBHelper.getInstance().getUserDao();
    }

    public static UserDbHelper getInstance() {
        return instance;
    }

    public void insertUser(UserBean userBean) {
        mUserDao.insert(userBean);
    }

    public UserBean getUserByUserId(String id) {
        List<UserBean> list = mUserDao.queryBuilder().where(UserBeanDao.Properties.Id.eq(id)).build().list();
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }


}
