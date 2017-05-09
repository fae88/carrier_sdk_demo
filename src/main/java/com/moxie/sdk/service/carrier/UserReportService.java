package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.MoxieWebUtils;
import com.moxie.sdk.entity.MoxieResponse;

/**
 * Created by MX on 2017/4/27.
 * 用户报告接口服务
 */
public class UserReportService {

    /**
     * 通过手机号码，查询用户报告
     * @param account
     * @return
     */
    public static String getUserReport(String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/report", "", false, true);
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("通过手机号码，查询用户报告成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }


}
