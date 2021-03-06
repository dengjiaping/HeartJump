package com.cucr.myapplication.core.pay;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.pay.PayCenterInterf;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.listener.Pay.PayLisntener;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.cucr.myapplication.utils.SpUtil;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import static com.cucr.myapplication.alipay.OrderInfoUtil2_0.getOutTradeNo;

/**
 * Created by cucr on 2017/10/16.
 */

public class PayCenterCore implements PayCenterInterf {

    private RequestQueue mQueue;
    private OnCommonListener aliPayListener;
    private PayLisntener payResultListener;
    private OnCommonListener userMoneyListener;
    private Context context;

    public PayCenterCore() {
        this.context = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }


    @Override
    public void aliPay(double howMuch, String subject, OnCommonListener listener) {
        MyLogger.jLog().i("howMuch:"+howMuch);
        aliPayListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ALIPAY_PAY, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)));
        request.add("timeout_express", "30m");
        request.add("product_code", "QUICK_MSECURITY_PAY");
        request.add("total_amount", howMuch + "");
        request.add("subject", subject);
        request.add("out_trade_no", getOutTradeNo());
        request.add("body", "真是太好用了!!!");

        request.setCancelSign(1);

        mQueue.add(Constans.TYPE_ONE, request, responseListener);
    }

    @Override
    public void wxPay() {

    }

    @Override
    public void queryResult(String order, PayLisntener listener) {
        payResultListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_ALIPAY_CHECK, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add("orderNo", order)
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));

        request.setCancelSign(2);

        mQueue.add(Constans.TYPE_THREE, request, responseListener);
    }


    @Override
    public void queryUserMoney(OnCommonListener listener) {
        userMoneyListener = listener;
        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_USER_MONEY, RequestMethod.POST);
        request.add(SpConstant.USER_ID, ((int) SpUtil.getParam(SpConstant.USER_ID, -1)))
                .add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        mQueue.add(Constans.TYPE_FORE, request, responseListener);
    }

    private OnResponseListener<String> responseListener = new OnResponseListener<String>() {

        @Override
        public void onStart(int what) {
            switch (what) {
                case Constans.TYPE_ONE:

                    break;

                case Constans.TYPE_TWO:

                    break;

                case Constans.TYPE_THREE:
                    payResultListener.onRequestStar();
                    break;

                case Constans.TYPE_FORE:
                    break;
            }
        }

        @Override
        public void onSucceed(int what, Response<String> response) {
            switch (what) {
                case Constans.TYPE_ONE:
                    aliPayListener.onRequestSuccess(response);
                    break;

                case Constans.TYPE_TWO:

                    break;

                case Constans.TYPE_THREE:
                    payResultListener.onSuccess(response);

                    break;

                case Constans.TYPE_FORE:
                    userMoneyListener.onRequestSuccess(response);

                    break;
            }
//

        }

        @Override
        public void onFailed(int what, Response<String> response) {
            HttpExceptionUtil.showTsByException(response, MyApplication.getInstance());
        }

        @Override
        public void onFinish(int what) {

        }
    };

    public void stop() {
        mQueue.cancelAll();
    }
}
