package com.moxie.sdk.service.carrier;

import com.moxie.sdk.common.MoxieWebUtils;
import com.moxie.sdk.entity.Account;
import com.moxie.sdk.entity.MoxieResponse;

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
        MoxieResponse response = MoxieWebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks/reset",
                "{\"account\":\"" + account.getAccount() + "\"," +
                        "\"user_id\":\"" + account.getUserId() + "\"," +
                        "\"real_name\":\"" + account.getRealName() +"\"," +
                        "\"id_card\":\"" + account.getIdCard() + "\"}");
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("创建修改密码任务成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
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
        boolean isTaskDone = false;
        MoxieResponse response = new MoxieResponse();
        String result = "";
        System.out.println("请输入验证码：");
        Scanner scanner = new Scanner(System.in);
        String validateSMS = scanner.next();
        if(validateSMS != null){
            response = MoxieWebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks/reset/" + taskId + "/input",
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

        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("密码重置成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }
}
