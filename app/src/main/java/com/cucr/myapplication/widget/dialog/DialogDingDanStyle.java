package com.cucr.myapplication.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;

/**
 * Created by 911 on 2017/4/26.
 */

public class DialogDingDanStyle extends Dialog {



    public DialogDingDanStyle(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_creatft_rlue);

        initView();

    }

    private void initView() {
        //设置点击外部消失
        setCanceledOnTouchOutside(true);

        TextView tv_isee = (TextView) findViewById(R.id.tv_isee);

        tv_isee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

}
