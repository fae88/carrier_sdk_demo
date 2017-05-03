package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.Constants;
import com.moxie.sdk.common.WebUtils;

import java.util.Map;

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
        Map<String,String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/basic", "task_id=" + task_id, false, true);

        return WebUtils.handleMap(map);
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
        Map<String,String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/bill",
                "task_id=" + task_id + "&from_month=" + from_month + "&to_month=" + to_month, false,true);

        return WebUtils.handleMap(map);

    }

    /**
     * 获取账号通话详情
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/call
     * @param task_id
     * @param month
     * @param account
     */
    public static String getCallDetailed(String task_id, String month, String account){
        Map<String,String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/call",
                "task_id=" + task_id + "&month=" + month , false, true);

        return WebUtils.handleMap(map);
    }

    /**
     * 获取短信通话详情
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/sms
     * @param task_id
     * @param month
     * @param account
     */
    public  static String getSMSDetailed(String task_id, String month, String account){
        Map<String,String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/sms",
                "task_id=" + task_id + "&month=" + month , false, true);

        return WebUtils.handleMap(map);
    }

    /**
     * 获取账号运营商的全部信息
     * https://api.51datakey.com/carrier/v3/mobiles/{mobile}/mxdata
     * @param task_id
     * @param account
     */
    public static String getMXData(String task_id, String account){
        Map<String,String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/mxdata",
                "task_id=" + task_id , false, true);

        return WebUtils.handleMap(map);
    }


}
