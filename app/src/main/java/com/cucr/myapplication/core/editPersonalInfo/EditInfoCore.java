package com.cucr.myapplication.core.editPersonalInfo;

import android.content.Context;

import com.cucr.myapplication.MyApplication;
import com.cucr.myapplication.constants.Constans;
import com.cucr.myapplication.constants.HttpContans;
import com.cucr.myapplication.constants.SpConstant;
import com.cucr.myapplication.interf.personalinfo.SavePersonalInfo;
import com.cucr.myapplication.listener.OnCommonListener;
import com.cucr.myapplication.model.EditPersonalInfo.PersonalInfo;
import com.cucr.myapplication.utils.EncodingUtils;
import com.cucr.myapplication.utils.HttpExceptionUtil;
import com.cucr.myapplication.utils.MyLogger;
import com.yanzhenjie.nohttp.FileBinary;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OnUploadListener;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;
import com.yanzhenjie.nohttp.rest.Response;

import java.io.File;

/**
 * Created by 911 on 2017/8/15.
 */

public class EditInfoCore implements SavePersonalInfo {

    private OnCommonListener commonListener;

    /**
     * 请求队列。
     */
    private RequestQueue mQueue;
    private Context mContext;

    public EditInfoCore() {
        mContext = MyApplication.getInstance();
        mQueue = NoHttp.newRequestQueue();
    }

    @Override
    public void save(Context context, PersonalInfo personalInfo, final OnCommonListener commonListener) {
        this.commonListener = commonListener;
        //用户头像
        String head = personalInfo.getuserHeadPortrait();

        Request<String> request = NoHttp.createStringRequest(HttpContans.HTTP_HOST + HttpContans.ADDRESS_EDIT_USERINFO, RequestMethod.POST);

        // 添加普通参数。
        request.add("userId", personalInfo.getUserId());
        request.add("sex", personalInfo.getSex());
        request.add("name", personalInfo.getName());
        request.add("birthday", personalInfo.getBirthday());
        request.add("provinceName", personalInfo.getProvinceId());
        request.add("cityName", personalInfo.getCityId());
        request.add("signName", personalInfo.getSignName());
        request.add(SpConstant.SIGN, EncodingUtils.getEdcodingSReslut(context, request.getParamKeyValues()));
        File file = new File(head);
        //如果头像路径为空 则不上传头像
        if (file.exists()) {
            // 上传文件需要实现NoHttp的Binary接口，NoHttp默认实现了FileBinary、InputStreamBinary、ByteArrayBitnary、BitmapBinary。
//            Bitmap imageByPath = CommonUtils.decodeBitmap(head);
            FileBinary fileBinary0 = new FileBinary(file);
//            BitmapBinary fileBinary0 = new BitmapBinary(imageByPath,head.substring(head.lastIndexOf("/")));

            /**
             * 监听上传过程，如果不需要监听就不用设置。
             * 第一个参数：what，what和handler的what一样，会在回调被调用的回调你开发者，作用是一个Listener可以监听多个文件的上传状态。
             * 第二个参数： 监听器。
             */
            fileBinary0.setUploadListener(0, mOnUploadListener);

            request.add("file", fileBinary0);// 添加1个文件

        }

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
                    commonListener.onRequestSuccess(response);
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

    /* 文件上传监听。
            */
    private OnUploadListener mOnUploadListener = new OnUploadListener() {

        @Override
        public void onStart(int what) {// 这个文件开始上传。
            MyLogger.jLog().i("onStart");
        }

        @Override
        public void onCancel(int what) {// 这个文件的上传被取消时。
            MyLogger.jLog().i("onCancel");
        }

        @Override
        public void onProgress(int what, int progress) {// 这个文件的上传进度发生边耍
            MyLogger.jLog().i("onProgress:" + progress);
        }

        @Override
        public void onFinish(int what) {// 文件上传完成
            MyLogger.jLog().i("onFinish");
        }

        @Override
        public void onError(int what, Exception exception) {// 文件上传发生错误。
            MyLogger.jLog().i("onError");
        }
    };


    //activity destory 时调用
    public void stopRequest() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        mQueue.cancelAll();
        // 因为回调函数持有了activity，所以退出activity时请停止队列。
        mQueue.stop();

    }
}
