package com.moxie.sdk.service.carrier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moxie.sdk.common.Constants;
import com.moxie.sdk.common.MoxieWebUtils;
import com.moxie.sdk.entity.Account;
import com.moxie.sdk.entity.MoxieResponse;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by MX on 2017/4/27.
 * 任务创建的接口服务
 */
public class TaskCreateService {
    /**
     * 测试运营商可用渠道
     * @throws IOException
     */
    public static String getCarrierChannels(String account) {

        MoxieResponse response = MoxieWebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks/channels" ,"{ \"account\": \"" + account + "\"}");
        //通过response判断服务是否执行成功
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("测试运营商可用渠道成功！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }



    /**
     * 创建Task
     * @throws IOException
     */
    public static String getCarrierTasks(Account account) {
        MoxieResponse response = MoxieWebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks",
                "{\"account\":\"" + account.getAccount() + "\"," +
                        "\"password\":\""+ account.getPassword() +"\"," +
                        "\"user_id\":\"" + account.getUserId() + "\"," +
                        "\"origin\":\"" + account.getOrigin() + "\"," +
                        "\"real_name\":\"" + account.getRealName() +"\"," +
                        "\"id_card\":\"" + account.getIdCard() + "\"," +
                        "\"login_type\":\"" + account.getLoginType() + "\"}");
        if(response.getHttpStatusCode()>=200 && response.getHttpStatusCode()<300){
            //服务正常，返回正常报文，并处理业务逻辑
            System.out.println("创建Task！");
            return response.getResult();
        }else{
            //异常情况，返回异常信息，并重新引导发起请求
            System.out.println("服务执行失败，请重新发送请求，错误信息: " + response.getHttpStatusMsg());
            return response.getHttpStatusCode() + ", " + response.getHttpStatusMsg();
        }
    }

    /**
     * 查询Task状态,并使用验证码验证
     * @throws IOException
     */
    public static void getCarrierTaskStatus(String task_id) {
        long pollEndTime = System.currentTimeMillis() + 180 * 1000;
        boolean isTaskDone = false;
        //轮询方式查看任务状态，并给客户发送验证码
        while(true){
            MoxieResponse response =  MoxieWebUtils.doGet("https://api.51datakey.com/carrier/v3/tasks/" + task_id +"/status", "", true, false);
            int statusCode = response.getHttpStatusCode();
            if(statusCode>=200 && statusCode<300) {

                JSONObject result_json = (JSONObject) JSON.parse(response.getResult());
                String phaseStatus = (String) result_json.get("phase_status");
                String description = (String) result_json.get("description");
                String phase = (String)result_json.get("phase");
                Boolean finished = (Boolean) result_json.get("finished");
                System.out.println(String.format("%s [%s] - %s", phase, phaseStatus, description));

                if (phaseStatus.equals("DOING")) {
                    System.out.println("任务正在执行");
                }
                if (phaseStatus.equals("DONE_SUCC") && finished) {
                    System.out.println("任务已完成");
                    isTaskDone = true;
                    break;
                }

                if (phaseStatus.equals("DONE_FAIL") || phaseStatus.equals("DONE_TIMEOUT")) {
                    System.out.println("任务失败/超时");
                    break;
                }

                if (phaseStatus.equals("WAIT_CODE")) {
                    String inputType = (String) result_json.get("type");
                    System.out.println("等待用户输入,类型:" + inputType);
//                    if (inputType.equalsIgnoreCase("img")) {
//                        System.out.println("base64在线转图片,访问 http://codebeautify.org/base64-to-image-converter");
//                        System.out.println("请识别图片验证码:" + (String) result_json.get("value"));
//                    }
                    System.out.print("请输入验证码:");
                    Scanner scanner = new Scanner(System.in);
                    String result = scanner.nextLine();
                    if (result != null) {
                        MoxieWebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks/" + task_id + "/input", "{\"input\":\""+ result +"\"}");
                    }
                }

            }else{
                //获取状态失败，进行处理逻辑
                System.out.println("任务状态查询失败");
                System.out.println(response.getHttpStatusMsg());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() > pollEndTime) break;
        }

        if(isTaskDone){
            //验证成功，已经通过认证，可以查询该客户相关数据
            System.out.println("可以查数据了");
        }

    }
}
