package com.cucr.myapplication.listener;

import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/15.
 */

public interface OnCommonListener {
    void onRequestSuccess(Response<String> response);
}
