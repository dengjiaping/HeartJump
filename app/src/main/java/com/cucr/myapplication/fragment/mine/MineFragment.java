package com.cucr.myapplication.fragment.mine;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.R;
import com.cucr.myapplication.activity.MessageActivity;
import com.cucr.myapplication.activity.dongtai.DongTaiActivity;
import com.cucr.myapplication.activity.journey.MyJourneyActivity;
import com.cucr.myapplication.activity.myHomePager.FocusActivity;
import com.cucr.myapplication.activity.pay.PayCenterActivity_new;
import com.cucr.myapplication.activity.pay.TxRecordActivity;
import com.cucr.myapplication.activity.setting.InvateActivity;
import com.cucr.myapplication.activity.setting.MyRequiresActivity;
import com.cucr.myapplication.activity.setting.PersonalInfoActivity;
import com.cucr.myapplication.activity.setting.RenZhengActivity;
import com.cucr.myapplication.activity.setting.SettingActivity;
import com.cucr.myapplication.activity.yuyue.MyYuYueActivity;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.editPersonalInfo.QueryPersonalMsgCore;
import com.cucr.myapplication.fragment.BaseFragment;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.EditPersonalInfo.PersonMessage;
import com.cucr.myapplication.model.eventBus.EventQueryPersonalInfo;
import com.cucr.myapplication.utils.CommonUtils;
import com.cucr.myapplication.utils.ToastUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yanzhenjie.nohttp.rest.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by 911 on 2017/4/10.
 */

public class MineFragment extends BaseFragment {

    //界面头部
    @ViewInject(R.id.head)
    private RelativeLayout head;

    //用户头像
    @ViewInject(R.id.iv_user_mine)
    private ImageView userPic;

    //用户昵称
    @ViewInject(R.id.textView3)
    private TextView nickName;

    //用户签名
    @ViewInject(R.id.tv_sign)
    private TextView userSign;

    private Intent mIntent;
    private QueryPersonalMsgCore mQucryCore;
    private PersonMessage.ObjBean mObj;

    @Override
    protected void initView(View childView) {
        EventBus.getDefault().register(this);
        ViewUtils.inject(this, childView);
        initHead();
        queryInfos();


        mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//        如果是企业用户
//        if (){
//            rl_my_yuyue.setVisibility(View.VISIBLE);
//        如果是明星用户
//        }else if(){
//        普通用户
//         }else{
// }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //点击保存的时候再查一遍
    public void onDataSynEvent(EventQueryPersonalInfo event) {
        queryInfos();
    }

    //查询用户信息
    private void queryInfos() {
        mQucryCore = new QueryPersonalMsgCore();
        mQucryCore.queryPersonalInfo(new OnCommonListener() {
            @Override
            public void onRequestSuccess(Response<String> response) {
                PersonMessage personMessage = mGson.fromJson(response.get().toString(), PersonMessage.class);
                if (personMessage.isSuccess()) {
                    mObj = mGson.fromJson(personMessage.getMsg(), PersonMessage.ObjBean.class);
                    initViews();
                } else {
                    ToastUtils.showToast(personMessage.getMsg());
                }
            }
        });
    }

    private void initViews() {
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + mObj.getUserHeadPortrait(),
                userPic, MyApplication.getImageLoaderOptions());
        nickName.setText(mObj.getName());
        if (TextUtils.isEmpty(mObj.getSignName())) {
            userSign.setText("还木有设置签名哦!");
        } else {
            userSign.setText(mObj.getSignName());
        }

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

    //跳转到消息界面
    @OnClick(R.id.iv_header_msg)
    public void goMsg(View view) {
        mIntent.setClass(mContext, MessageActivity.class);
        mContext.startActivity(mIntent);
    }

    //设置
    @OnClick(R.id.rl_setting)
    public void goSetting(View view) {
        mIntent.setClass(mContext, SettingActivity.class);
        mContext.startActivity(mIntent);
    }

    //进入个人资料
    @OnClick(R.id.rl_enter_mypager)
    public void goPersonalInfo(View view) {
        mIntent.setClass(mContext, PersonalInfoActivity.class);
        mIntent.putExtra("data", mObj);
        mContext.startActivity(mIntent);
    }

    //关注
    @OnClick(R.id.ll_mine_focus)
    public void goFocus(View view) {
        mIntent.setClass(mContext, FocusActivity.class);
        mContext.startActivity(mIntent);
    }

    //粉丝
    @OnClick(R.id.ll_mine_fans)
    public void goFans(View view) {
        mIntent.setClass(mContext, FocusActivity.class);
        mContext.startActivity(mIntent);
    }

    //动态
    @OnClick(R.id.ll_mine_dongtai)
    public void goDongTai(View view) {
        mIntent.setClass(mContext, DongTaiActivity.class);
        mContext.startActivity(mIntent);
    }

    //星币
    @OnClick(R.id.ll_pay)
    public void goXb(View view) {
        mIntent.setClass(mContext, TxRecordActivity.class);
        mContext.startActivity(mIntent);
    }

    //充值中心
    @OnClick(R.id.rl_pay_center)
    public void goPayCenter(View view) {
        mIntent.setClass(mContext, PayCenterActivity_new.class);
        mContext.startActivity(mIntent);
    }

    //认证
    @OnClick(R.id.rl_ren_zheng)
    public void goRz(View view) {
        mIntent.setClass(mContext, RenZhengActivity.class);
        mContext.startActivity(mIntent);
    }

    //预约
    @OnClick(R.id.rl_my_yuyue)
    public void goYuYue(View view) {
        mIntent.setClass(mContext, MyYuYueActivity.class);
        mContext.startActivity(mIntent);
    }

    //票务
    @OnClick(R.id.rl_piaowu)
    public void goPw(View view) {

    }

    //行程
    @OnClick(R.id.rl_my_journey)
    public void goJourney(View view) {
        mIntent.setClass(mContext, MyJourneyActivity.class);
        mContext.startActivity(mIntent);
    }

    //邀请有礼
    @OnClick(R.id.rl_yaoqing)
    public void goInvate(View view) {
        mIntent.setClass(mContext, InvateActivity.class);
        mContext.startActivity(mIntent);
    }

    //我的要求
    @OnClick(R.id.rl_require)
    public void goMyRequire(View view) {
        mIntent.setClass(mContext, MyRequiresActivity.class);
        mContext.startActivity(mIntent);
    }

    //是否需要头部
    @Override
    protected boolean needHeader() {
        return false;
    }

    @Override
    public int getContentLayoutRes() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
