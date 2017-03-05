package com.ljy.bottombar.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ljy.bottombar.R;
import com.ljy.bottombar.bean.BottomBarBean;
import com.ljy.bottombar.fragment.CustomFragment;
import com.ljy.bottombar.view.BottomBar;

import java.util.ArrayList;
import java.util.List;


public class BottomBarActivity extends AppCompatActivity {

    private static final String TAG = BottomBarActivity.class.getName();
    private BottomBar bottomBar;
    private List<BottomBarBean> bottomBarBeanList;
    private Fragment currentFragment;
    private CustomFragment webviewFragment;
    private CustomFragment messageFragment;
    private CustomFragment contactFragment;
    private CustomFragment appFragment;
    private CustomFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initBottom();
    }

    //加载底栏
    private void initBottom() {
        bottomBar = (BottomBar)findViewById(R.id.bottom_bar);
        bottomBarBeanList = new ArrayList<>();
        BottomBarBean bean = new BottomBarBean(R.drawable.foot_home, R.drawable.foot_home_hover, 0, "首页", true);
        bottomBarBeanList.add(bean);
        bean = new BottomBarBean(R.drawable.foot_message, R.drawable.foot_message_hover, 0, "消息", false);
        bottomBarBeanList.add(bean);
        bean = new BottomBarBean(R.drawable.foot_app, R.drawable.foot_app_hover, 0, "应用", false);
        bottomBarBeanList.add(bean);
        bean = new BottomBarBean(R.drawable.foot_contact, R.drawable.foot_contact_hover, 0, "通讯录", false);
        bottomBarBeanList.add(bean);
        bean = new BottomBarBean(R.drawable.foot_myself, R.drawable.foot_myself_hover, 0, "我", false);
        bottomBarBeanList.add(bean);

        if (currentFragment == null) {
            currentFragment = webviewFragment;
        }

        bottomBar.update(bottomBarBeanList);

        bottomBar.setOnBottomClickListener(new BottomBar.BottomBarClickListener() {
            @Override
            public void click(int i) {
                setSelectedPage(i);
            }
        });
    }

    //碎片选择
    public void setSelectedPage(int selected) {
        Log.d(TAG, "setSelectedPage: " + selected);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        switch (selected) {
            case 0:
                if (webviewFragment == null) {
                    webviewFragment = CustomFragment.newInstance("首页");
                    transaction.add(R.id.selected_fragment, webviewFragment);
                }
                transaction.show(webviewFragment);
                currentFragment = webviewFragment;
                break;
            case 1:
                if (messageFragment == null) {
                    messageFragment = CustomFragment.newInstance("信息");
                    transaction.add(R.id.selected_fragment, this.messageFragment, "conversation");
                }
                transaction.show(messageFragment);
                currentFragment = messageFragment;
                break;
            case 2:
                if (appFragment == null) {
                    appFragment = CustomFragment.newInstance("应用");
                    transaction.add(R.id.selected_fragment, appFragment);
                }
                transaction.show(appFragment);
                currentFragment = appFragment;

                break;
            case 3:
                if (contactFragment == null) {
                    contactFragment = CustomFragment.newInstance("通讯录");
                    transaction.add(R.id.selected_fragment, contactFragment);
                }
                transaction.show(contactFragment);
                currentFragment = contactFragment;
                break;
            case 4:
                if (settingFragment == null) {
                    settingFragment = CustomFragment.newInstance("设置");
                    transaction.add(R.id.selected_fragment, settingFragment);
                }
                transaction.show(settingFragment);
                currentFragment = settingFragment;
                break;
        }
        transaction.commit();
    }

}
