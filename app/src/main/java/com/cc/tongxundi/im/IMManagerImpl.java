package com.cc.tongxundi.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.cc.tongxundi.bean.UserBean;
import com.cc.tongxundi.db.UserDbHelper;
import com.cc.tongxundi.down.HttpUtil;
import com.cc.tongxundi.greendao.UserBeanDao;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.plugin.im.dao.bean.RXConversation;
import com.yuntongxun.plugin.im.manager.bean.RETURN_TYPE;
import com.yuntongxun.plugin.im.manager.port.OnBindViewHolderListener;
import com.yuntongxun.plugin.im.manager.port.OnIMBindViewListener;
import com.yuntongxun.plugin.im.manager.port.OnMessagePreproccessListener;
import com.yuntongxun.plugin.im.manager.port.OnNotificationClickListener;
import com.yuntongxun.plugin.im.manager.port.OnReturnIdsCallback;
import com.yuntongxun.plugin.im.manager.port.OnReturnIdsClickListener;
import com.yuntongxun.plugin.im.ui.chatting.model.BaseChattingRow;
import com.yuntongxun.plugin.im.ui.conversation.ConversationAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IMManagerImpl implements OnIMBindViewListener, OnNotificationClickListener, OnReturnIdsClickListener, OnBindViewHolderListener, OnMessagePreproccessListener {

    private static IMManagerImpl instance = new IMManagerImpl();
    private List<UserBean> userList = new ArrayList<>();
    private List<String> HeadList = new ArrayList<>();

    public static IMManagerImpl getInstance() {
        return instance;
    }

    public void addUser(UserBean userBean) {
        if (!userList.contains(userBean))
            userList.add(userBean);
    }

    private IMManagerImpl() {

        HeadList.add("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg");
        HeadList.add("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1484031904&di=91b67952b067fc0403dbfc2825422f03&src=http://pic41.nipic.com/20140503/9908010_145213320111_2.jpg");
        HeadList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1484041987878&di=3a58317c2b0e5fca1e9bbecf760a6c34&imgtype=0&src=http%3A%2F%2Fpic45.nipic.com%2F20140805%2F7447430_145001150000_2.jpg");
        HeadList.add("http://new-img4.ol-img.com/moudlepic/199_module_images/201612/5865e0b6a6600_273.jpg");
        HeadList.add("http://new-img4.ol-img.com/moudlepic/199_module_images/201612/5861ddf858113_790.jpg");
        HeadList.add("http://new-img1.ol-img.com/moudlepic/199_module_images/201612/5861dcaf8305d_296.jpg");
        HeadList.add("http://new-img3.ol-img.com/moudlepic/199_module_images/201612/5861db8b4c8d3_630.jpg");
    }

    @Override
    public BaseChattingRow onBindView(ECMessage ecMessage, BaseChattingRow baseChattingRow) {
        return null;
    }

    @Override
    public View onBindView(Context context, View view, RXConversation rxConversation, ConversationAdapter.BaseConversationViewHolder baseConversationViewHolder) {
        return null;
    }

    @Override
    public String onBindNickName(Context context, String s) {
        for (UserBean userBean : userList) {
            if (String.valueOf(userBean.getId()).equals(s))
                return userBean.getNickname();
        }
        UserBean userBean = UserDbHelper.getInstance().getUserByUserId(s);
        if (userBean != null){
            addUser(userBean);
            return userBean.getNickname();
        }

        return null;
    }

    @Override
    public void OnAvatarClickListener(Context context, String s) {

    }

    @Override
    public String onBindAvatarByUrl(Context context, String s) {

        return HeadList.get(0);
    }

    @Override
    public boolean dispatchMessage(ECMessage ecMessage) {
        return false;
    }

    @Override
    public void onNotificationClick(Context context, String s, Intent intent) {

    }

    @Override
    public void onReturnIdsClick(Context context, RETURN_TYPE return_type, OnReturnIdsCallback onReturnIdsCallback, String... strings) {

    }
}
