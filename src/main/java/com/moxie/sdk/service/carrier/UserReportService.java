package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.Constants;
import com.moxie.sdk.common.WebUtils;

import java.util.Map;

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
        Map<String, String> map = WebUtils.doGet(Constants.BASEURL + "/mobiles/" + account +"/report", "", false);
        return WebUtils.handleMap(map);
    }


}
