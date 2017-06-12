package com.cucr.myapplication.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cucr.myapplication.R;
import com.cucr.myapplication.widget.signcalendar.SignCalendar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignActivity extends Activity {

    private SignCalendar calendar;
    private String date;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        ViewUtils.inject(this);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date = formatter.format(curDate);

        mTv = (TextView) findViewById(R.id.tv);
        calendar = (SignCalendar) findViewById(R.id.sc_main);

        //初始化标题日期
        mTv.setText(calendar.getCalendarYear()+" - "+calendar.getCalendarMonth());

                List<String> list = new ArrayList<String>();
                list.add(date);
//              当天日期标记
                calendar.addMarks(list, 0);

        calendar.setOnCalendarDateChangedListener(new SignCalendar.OnCalendarDateChangedListener() {
            @Override
            public void onCalendarDateChanged(int year, int month) {
                mTv.setText(year+" - "+month);
            }
        });
    }

    //点击返回
    @OnClick(R.id.iv_my_sign_back)
    public void back(View view){
        finish();
    }

}
