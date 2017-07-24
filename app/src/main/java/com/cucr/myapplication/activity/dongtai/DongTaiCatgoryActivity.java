package com.cucr.myapplication.activity.dongtai;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.adapter.LvAdapter.FtCatgoryAadapter;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.cucr.myapplication.widget.textview.MyClickableSpan;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DongTaiCatgoryActivity extends Activity implements View.OnClickListener {

    //沉浸栏头部
    @ViewInject(R.id.head)
    RelativeLayout head;

    //popWindow背景
    @ViewInject(R.id.fl_pop_bg)
    FrameLayout fl_pop_bg;

    //ListView
    @ViewInject(R.id.lv_dongtai_catgory)
    ListView lv_dongtai_catgory;


    private PopupWindow popWindow;
    private LayoutInflater layoutInflater;
    private TextView tv_delete, tv_share;
    private LinearLayout cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dong_tai_catgory);
        ViewUtils.inject(this);

        initHead();

        initLV();
    }

    private void initLV() {
        View lvHead = layoutInflater.inflate(R.layout.head_lv_dongtai_catgory, null);
        TextView tv_from = (TextView) lvHead.findViewById(R.id.tv_from);

        //模拟获取数据
        String who = "胡歌";

        SpannableString sp = new SpannableString("来自" + who + "粉团");

        //设置高亮样式二
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#F68D89")), 2, 2 + who.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        //设置点击 传入明星
        MyClickableSpan mySpan = new MyClickableSpan(who);
        sp.setSpan(mySpan,2,2+who.length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //SpannableString对象设置给TextView
        tv_from.setText(sp);
        //设置TextView可点击
        tv_from.setMovementMethod(LinkMovementMethod.getInstance());


        lv_dongtai_catgory.addHeaderView(lvHead,null,true);
        lv_dongtai_catgory.setHeaderDividersEnabled(false);
        lv_dongtai_catgory.setAdapter(new FtCatgoryAadapter(this));

    }

    //沉浸栏
    private void initHead() {

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) head.getLayoutParams();
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) head.getLayoutParams();
            layoutParams.height = CommonUtils.dip2px(this, 73.0f);
            head.setLayoutParams(layoutParams);
            head.requestLayout();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    //弹出popupWindow
    @OnClick(R.id.iv_dongtai_catgory_more)
    private void showPopupWindow(View parent) {

        CommonUtils.initPopBg(true, fl_pop_bg);

        if (popWindow == null) {
            View view = layoutInflater.inflate(R.layout.pop_dongtai_catgory, null);
            popWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            initPop(view);
        }
        popWindow.setAnimationStyle(R.style.AnimationFade);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        popWindow.setBackgroundDrawable(new BitmapDrawable());

        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                CommonUtils.initPopBg(false, fl_pop_bg);
            }
        });
    }

    public void initPop(View view) {
        tv_delete = (TextView) view.findViewById(R.id.tv_delete);//删除
        tv_share = (TextView) view.findViewById(R.id.tv_share);//分享
        cancel = (LinearLayout) view.findViewById(R.id.cancel);//取消
        view.findViewById(R.id.rl_popWindow_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        tv_delete.setOnClickListener(this);
        tv_share.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    //返回键
    @OnClick(R.id.iv_dongtai_catgory_back)
    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_delete:
                ToastUtils.showToast(this, "删除");
                break;

            case R.id.tv_share:
                ToastUtils.showToast(this, "分享");
                break;

            case R.id.cancel:
                popWindow.dismiss();
                break;
        }
    }
}
