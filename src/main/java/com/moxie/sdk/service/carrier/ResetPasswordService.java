package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.Constants;
import com.moxie.sdk.common.WebUtils;
import com.moxie.sdk.entity.Account;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by MX on 2017/4/27.
 * 重置密码接口服务
 */
public class ResetPasswordService {

    /**
     * 创建修改密码任务
     * url：	https://api.51datakey.com/carrier/v3/tasks/reset/{task_id}/input
     * @param account
     * @return
     */
    public static String getResetTask(Account account){
        Map<String, String> map = WebUtils.doPost(Constants.BASEURL + "/tasks/reset",
                "{\"account\":\"" + account.getAccount() + "\"," +
                        "\"user_id\":\"" + account.getUserId() + "\"," +
                        "\"real_name\":\"" + account.getRealName() +"\"," +
                        "\"id_card\":\"" + account.getIdCard() + "\"}");
        return WebUtils.handleMap(map);
    }


    /**
     * 图片验证码/短信验证码输入/密码重置
     * url：https://api.51datakey.com/carrier/v3/tasks/reset/{task_id}/input
     * @param taskId
     * @param newPassWord
     * @return
     */
    public static String validateSMS(String taskId, String newPassWord){
        long pollEndTime = System.currentTimeMillis() + 180 * 1000;
        Map<String, String> map = new HashMap<String, String>();
        boolean isTaskDone = false;
        String result = "";
        System.out.println("请输入验证码：");
        Scanner scanner = new Scanner(System.in);
        String validateSMS = scanner.next();
        if(validateSMS != null){
            map = WebUtils.doPost(Constants.BASEURL + "/tasks/reset/" + taskId + "/input",
                    "{" +
                            "  \"inputs\": [" +
                            "     {" +
                            "        \"type\": \"sms\"," +
                            "        \"value\": \"" + validateSMS + "\"" +
                            "     }," +
                            "     {" +
                            "     \"type\": \"pwd\"," +
                            "        \"value\": \"" + newPassWord + "\"" +
                            "     }" +
                            "  ]" +
                            "}");
        }

        return WebUtils.handleMap(map);
//        result = map.get("result");

    }
}
