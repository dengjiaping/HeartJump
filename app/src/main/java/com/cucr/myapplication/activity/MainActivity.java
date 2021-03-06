package com.cucr.myapplication.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cucr.myapplication.R;
import com.cucr.myapplication.fragment.DaBang.DaBangFragment;
import com.cucr.myapplication.fragment.fuLiHuoDong.FragmentHuoDongAndFuLi;
import com.cucr.myapplication.fragment.home.FragmentHotAndFocusNews;
import com.cucr.myapplication.fragment.mine.MineFragment;
import com.cucr.myapplication.fragment.other.FragmentFans;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ZipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private List<Fragment> mFragments = new ArrayList<>();
    private RadioGroup mRg_mian_fragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //share sdk 初始化
        ShareSDK.initSDK(this);

        //获取从 我的-明星-右上角加关注 界面跳转过来的数据
        findView();
        initView();


        initFragment(0);
        initRadioGroup();

        //TODO: 2017/4/28 Splash界面完成
        //实例化文件对象 判断文件是否存在
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase");
        file.mkdir();


//        if (!file.exists()){
        //解压文件
        initZip();
//        }
    }


    private void initZip() {

        try {
            InputStream is = getAssets().open("citys.zip");
            FileOutputStream os = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dataBase", "city.db"));
            ZipUtil.unzip(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void findView() {
        RadioButton rb_1 = (RadioButton) findViewById(R.id.rb_1);
        RadioButton rb_2 = (RadioButton) findViewById(R.id.rb_2);
        RadioButton rb_3 = (RadioButton) findViewById(R.id.rb_3);
        RadioButton rb_4 = (RadioButton) findViewById(R.id.rb_4);
        RadioButton rb_mid = (RadioButton) findViewById(R.id.rb_mid);
//        rb_4.setVisibility(View.GONE);

        //底部导航栏距离
        initDrawable(rb_1, 0, 0, 22, 25);
        initDrawable(rb_2, 0, 0, 21, 23);
        initDrawable(rb_mid, 0, 0, 23, 23);
        initDrawable(rb_3, 0, 0, 22, 25);
        initDrawable(rb_4, 0, 0, 22, 22);

    }

    //初始化rb中的图片大小
    public void initDrawable(RadioButton v, int left, int top, int right, int bottom) {
        Drawable drawable = v.getCompoundDrawables()[1];
        drawable.setBounds(CommonUtils.dip2px(this, left), CommonUtils.dip2px(this, top), CommonUtils.dip2px(this, right), CommonUtils.dip2px(this, bottom));
        v.setCompoundDrawables(null, drawable, null, null);
    }

    private void initRadioGroup() {
        mRg_mian_fragments.setOnCheckedChangeListener(this);

    }

    //根据常过来的索引切换fragment
    private void initFragment(int i) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ll_container, mFragments.get(i)).commit();
    }

    private void initView() {
//        mFragments.add(new HomeFragment());              //首页
        mFragments.add(new FragmentHotAndFocusNews());     //首页
        mFragments.add(new FragmentHuoDongAndFuLi());

        mFragments.add(new DaBangFragment());            //打榜
        mFragments.add(new MineFragment());              //我的

        //TODO
//        if (企业用户) {
//        mFragments.add(new ApointmentFragmentA());
//        }else(其他用户){
        mFragments.add(new FragmentFans());              //其他
//        }


        mRg_mian_fragments = (RadioGroup) findViewById(R.id.rg_mian_fragments);

    }

    //切换RadioGroup的监听
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //首页
            case R.id.rb_1:
                initFragment(0);
                break;

            //福利
            case R.id.rb_2:
                initFragment(1);
                break;

            //打榜
            case R.id.rb_3:
                initFragment(2);
                break;

            //我的
            case R.id.rb_4:
                initFragment(3);
                break;


            //中间的other
            case R.id.rb_mid:
                initFragment(4);
                break;

        }
    }

}