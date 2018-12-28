package com.cc.tongxundi;

import android.app.Application;

import com.cc.tongxundi.db.DBHelper;
import com.cc.tongxundi.im.IMManagerImpl;
import com.yuntongxun.plugin.common.SDKCoreHelper;
import com.yuntongxun.plugin.common.common.utils.LogUtil;
import com.yuntongxun.plugin.greendao3.helper.DaoHelper;
import com.yuntongxun.plugin.im.dao.helper.IMDao;
import com.yuntongxun.plugin.im.manager.IMPluginManager;
import com.yuntongxun.plugin.im.manager.bean.IMConfiguration;

public class TXDApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBHelper.getInstance().setDatabase(getApplicationContext());
        initIM();
    }

    private void initIM() {
        //初始化插件上下文
        SDKCoreHelper.setContext(this);
//初始化数据库
        DaoHelper.init(this, new IMDao());
        LogUtil.setDebugMode(true);

        /**
         * 推荐配置方式  同为链式调用，但创建统一的接口实现类，在实现类中配置接口的方法
         * 第一步 先创建 IMConfiguration的实例
         * 第二步 添加到IMPluginManager.getManager().init(IMConfiguration的实例);
         * 注意事项：下面的方法可以根据需求进行配置，详情见文档后面接口讲解
         */
        IMConfiguration imConfiguration = new IMConfiguration.IMConfigBuilder(this)
/** 方法1 ↓ */
                .setOnIMBindViewListener(IMManagerImpl.getInstance())
/** 方法2 ↓ */
                .setOnNotificationClickListener(IMManagerImpl.getInstance())
/** 方法3 ↓ */
                .setOnReturnIdsClickListener(IMManagerImpl.getInstance())
/** 方法4 ↓ */
               // .imPanel(panel1, panel2)
/** 方法5 ↓ */
                .notifyIcon(R.drawable.ic_launcher)
/** 方法6 ↓*/
                .setOnBindViewHolderListener(IMManagerImpl.getInstance())
/** 方法7 ↓*/
                .setOnMessagePreproccessListener(IMManagerImpl.getInstance())
                .build();
        IMPluginManager.getManager().init(imConfiguration);
    }
}
