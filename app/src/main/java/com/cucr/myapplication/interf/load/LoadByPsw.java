package com.cucr.myapplication.interf.load;

import com.cucr.myapplication.listener.OnLoginListener;

/**
 * Created by 911 on 2017/8/11.
 */

public interface LoadByPsw {
     void login(String userName, String psw, OnLoginListener loginListener);
}
