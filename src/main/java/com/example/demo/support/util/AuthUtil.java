package com.example.demo.support.util;


import com.example.demo.support.model.LoginInfo;

/**
 * @Author : wangyz
 * @Description :
 * @Date :  2017/5/20
 */
public class AuthUtil {


    private static ThreadLocal<LoginInfo>  threadLocal = new ThreadLocal<>();

//    private static  LoginInfo user;

    public static LoginInfo getUserInfo(){
        return threadLocal.get();
    }
    public static void setUserInfo(LoginInfo loginInfo){
        threadLocal.set(loginInfo);
    }




}
