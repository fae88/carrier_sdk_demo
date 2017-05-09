package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.MoxieWebUtils;
import com.moxie.sdk.entity.MoxieResponse;

/**
 * Created by MX on 2017/4/27.
 * 账单查询接口服务
 */
public class BillQueryService {

    /**
     * 获取账单的基本信息
     * url:https://api.51datakey.com/carrier/v3/mobiles/{mobile}/basic
     * @param task_id
     * @param account
     */
    public static String getBasicRecord(String task_id, String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/basic", "task_id=" + task_id, false, true);

        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("获取账单的基本信息成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }


    /**
     * 获取账号的账单记录
     * url:https://api.51datakey.com/carrier/v3/mobiles/{mobile}/bill
     * @param task_id
     * @param from_month
     * @param to_month
     * @param account
     */
    public static String getBillRecord(String task_id, String from_month, String to_month, String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/bill",
                "task_id=" + task_id + "&from_month=" + from_month + "&to_month=" + to_month, false,true);
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("获取账号的账单记录成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }

    }

    /**
     * 获取账号通话详情
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/call
     * @param task_id
     * @param month
     * @param account
     */
    public static String getCallDetailed(String task_id, String month, String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/call",
                "task_id=" + task_id + "&month=" + month , false, true);
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("获取账号通话详情成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }

    /**
     * 获取短信通话详情
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/sms
     * @param task_id
     * @param month
     * @param account
     */
    public  static String getSMSDetailed(String task_id, String month, String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/sms",
                "task_id=" + task_id + "&month=" + month , false, true);
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("获取短信通话详情成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }

    /**
     * 获取账号运营商的全部信息
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/mxdata
     * @param task_id
     * @param account
     */
    public static String getMXData(String task_id, String account){
        MoxieResponse response = MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/mobiles/" + account +"/mxdata",
                "task_id=" + task_id , false, true);
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("获取账号运营商的全部信息成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }


}
