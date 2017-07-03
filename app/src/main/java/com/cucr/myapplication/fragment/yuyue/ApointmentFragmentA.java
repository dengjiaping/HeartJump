package com.cucr.myapplication.fragment.yuyue;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.HomeSearchActivity;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.adapter.PagerAdapter.YuYuePagerAdapter;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.fragment.star.FragmentStarRecommend;
import com.cucr.myapplication.utils.CommonUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 911 on 2017/4/10.
 */

public class ApointmentFragmentA extends BaseFragment {

    //导航栏
    @ViewInject(R.id.tablayout)
    TabLayout tablayout;

    //ViewPager
    @ViewInject(R.id.vp_recommed_star)
    ViewPager vp_recommed_star;

    //头部
    @ViewInject(R.id.head)
    RelativeLayout head;

    private List<Fragment> mFragments;


    @Override
    protected void initView(View childView) {
        ViewUtils.inject(this, childView);

        initHead();

        initTableLayout();

        initVP();

    }

    //沉浸栏
    private void initHead() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(mContext, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initVP() {
        mFragments = new ArrayList<>();
        mFragments.add(new FragmentStarRecommend());
//        mFragments.add(new FragmentStarClassify());
        mFragments.add(new FragmentStarRecommend());
        vp_recommed_star.setAdapter(new YuYuePagerAdapter(getFragmentManager(), mFragments));
    }

    @Override
    protected boolean needHeader() {
        return false;
    }

    private void initTableLayout() {
        tablayout.addTab(tablayout.newTab().setText("推荐"));
        tablayout.addTab(tablayout.newTab().setText("全部"));
        tablayout.setupWithViewPager(vp_recommed_star);//将导航栏和viewpager进行关联

    }


    //返回子类布局
    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_yuyue;
    }

    //跳转搜索界面
    @OnClick(R.id.iv_search)
    public void goSearch(View view) {
        startActivity(new Intent(mContext, HomeSearchActivity.class));
    }

    //跳转消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        startActivity(new Intent(mContext, MessageActivity.class));
    }


}
