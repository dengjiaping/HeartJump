package com.cucr.myapplication.core.editPersonalInfo;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.personalinfo.QueryPersonalInfo;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

/**
 * Created by 911 on 2017/8/18.
 */

public class QueryPersonalMsgCore implements QueryPersonalInfo {

    private OnCommonListener onCommonListener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public QueryPersonalMsgCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }
    @Override
    public void queryPersonalInfo(final OnCommonListener onCommonListener) {
        this.onCommonListener = onCommonListener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_QUERY_USERINFO, RequestMethod.POST);
        // 添加普通参数。

        request.add("userId", ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(mContext, request.getParamKeyValues()));
        //缓存主键 在这里用sign代替  保证全局唯一  否则会被其他相同数据覆盖
        request.setCacheKey(HttpContans.ADDRESS_QUERY_USERINFO);
        //没有缓存才去请求网络
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    private OnResponseListener responseListener = new OnResponseListener() {
        @Override
        public void onStart(int what) {

        }

        @Override
        public void onSucceed(int what, Response response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    onCommonListener.onRequestSuccess(response);
                    break;
            }
        }

        @Override
        public void onFailed(int what, Response response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {

        }
    };
}
