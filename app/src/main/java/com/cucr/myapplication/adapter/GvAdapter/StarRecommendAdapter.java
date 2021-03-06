package com.cucr.myapplication.adapter.GvAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.core.focus.FocusCore;
import com.cucr.myapplication.model.starList.StarListInfos;
import com.cucr.myapplication.utils.CommonViewHolder;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.widget.dialog.DialogCanaleFocusStyle;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 911 on 2017/5/22.
 */

public class StarRecommendAdapter extends BaseAdapter {
    Context mContext;

    int checked = -1;
    private DialogCanaleFocusStyle mDialogCanaleFocusStyle;
    private FrameLayout mFl;
    private List<StarListInfos.RowsBean> rows;
    private Activity activity;
    private FocusCore mCore;
    private int newPosition;

    public void setData(List<StarListInfos.RowsBean> rows){
        this.rows = rows;
        notifyDataSetChanged();
    }

    public void setCheck(int position) {
        checked = position;
        notifyDataSetChanged();
    }

    public StarRecommendAdapter(Context context, List<StarListInfos.RowsBean> rows) {
        this.mContext = context;
        this.rows = rows;
        mCore = new FocusCore();
        initDialog();
    }


    private void initDialog() {

        mDialogCanaleFocusStyle = new DialogCanaleFocusStyle(mContext, R.style.ShowAddressStyleTheme);
        mDialogCanaleFocusStyle.setOnClickBtListener(new DialogCanaleFocusStyle.OnClickBtListener() {
            @Override
            public void clickConfirm() {
//                mFl.setVisibility(View.GONE);
                StarListInfos.RowsBean rowsBean = rows.get(newPosition);
                mCore.cancaleFocus(rowsBean.getId());
                rowsBean.setIsfollow(0);
                notifyDataSetChanged();
                mDialogCanaleFocusStyle.dismiss();
            }

            @Override
            public void clickCancle() {
                mDialogCanaleFocusStyle.dismiss();
            }
        });
    }

    @Override
    public int getCount() {
        MyLogger.jLog().i("rows:" + rows);
        return rows == null ? 0 : rows.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CommonViewHolder cvh = CommonViewHolder.createCVH(convertView, mContext, R.layout.item_gv_star_recommend, null);
        final StarListInfos.RowsBean rowsBean = rows.get(position);
        Resources resources = mContext.getResources();

        //获取控件
        RelativeLayout rl_item = cvh.getView(R.id.rl_item, RelativeLayout.class);
        final TextView tv_focus = cvh.getTv(R.id.tv_focus);
        TextView tv_star_fans = cvh.getTv(R.id.tv_star_fans);
        TextView tv_star_name = cvh.getTv(R.id.tv_star_name);
        ImageView iv_pic = cvh.getIv(R.id.iv_pic);

        //显示明星列表图片
        ImageLoader.getInstance().displayImage(HttpContans.HTTP_HOST + rowsBean.getStartShowPic(), iv_pic);

        //粉丝数量
        tv_star_fans.setText(rowsBean.getFansCount());

        //明星姓名
//        final String realName = rowsBean.getRealName();
//        final int starId = rowsBean.getId();
        String realName = rowsBean.getRealName();
        final int starId = rowsBean.getId();
        tv_star_name.setText(realName);

        //是否关注  0：未关注      1：已关注
        final int isfollow = rowsBean.getIsfollow();

        MyLogger.jLog().i("position:"+position+",isfollow"+isfollow);
        if (isfollow == 0) {
            tv_focus.setText("加关注");
            tv_focus.setTextColor(resources.getColor(R.color.white));
            tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.care_nor));
        } else {
            tv_focus.setText("已关注");
            tv_focus.setTextColor(resources.getColor(R.color.pink));
            tv_focus.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_focud_bg));
        }

        tv_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfollow == 0) {
                    MyLogger.jLog().i("关注。。。");
                    mCore.toFocus(starId);

                    rowsBean.setIsfollow(1);
                } else {
                    mDialogCanaleFocusStyle.show();
                    mDialogCanaleFocusStyle.initTitle(rows.get(position).getRealName());
                    newPosition = position;
                    MyLogger.jLog().i("取消关注。。。");

//                    mCore.cancaleFocus(starId);
//                    rowsBean.setIsfollow(0);
                }
                notifyDataSetChanged();
            }
        });


//        //点击事件
//        rl_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), position + "", Toast.LENGTH_SHORT).show();
//            }
//        });

        return cvh.convertView;
    }

    public void stop() {
        mCore.stopRequest();
    }
}
