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
    private int currentFragment = -1;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);
        fragmentList = new ArrayList<>();

        fragmentList.add(CustomFragment.newInstance("首页"));
        fragmentList.add(CustomFragment.newInstance("消息"));
        fragmentList.add(CustomFragment.newInstance("应用"));
        fragmentList.add(CustomFragment.newInstance("通讯录"));
        fragmentList.add(CustomFragment.newInstance("设置"));

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
        bottomBar.update(bottomBarBeanList);

        bottomBar.setOnBottomClickListener(new BottomBar.BottomBarClickListener() {
            @Override
            public void click(int i) {
                setSelectedPage(i);
            }
        });

        int msgCount = 11;
        for (BottomBarBean bottomBarBean: bottomBarBeanList){
            bottomBarBean.setMsgCount(msgCount);
            msgCount = msgCount + 11;
        }

        bottomBar.updateMsgCount(bottomBarBeanList);
    }

    //碎片选择
    public void setSelectedPage(int selected) {
        Log.d(TAG, "setSelectedPage: " + selected);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();

        if (selected >= 0 && selected < fragmentList.size()){
            Fragment fragment = fragmentList.get(selected);
            if (!fragment.isAdded())
                transaction.replace(R.id.selected_fragment, fragment);
            else {
                if (currentFragment != -1) {
                    transaction.hide(fragmentList.get(currentFragment));
                }
                transaction.show(fragment);
            }
        }
        transaction.commit();
    }

}
