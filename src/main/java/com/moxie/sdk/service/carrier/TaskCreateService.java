package com.moxie.sdk.service.carrier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moxie.sdk.common.Constants;
import com.moxie.sdk.common.WebUtils;
import com.moxie.sdk.entity.Account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
    public static String getCarrierChannels(String account) throws IOException {
        Map<String, String> map = WebUtils.doPost("https://api.51datakey.com/carrier/v3/tasks/channels" ,"{ \"account\": \"" + account + "\"}");
        return WebUtils.handleMap(map);
    }


    /**
     * 创建Task
     * @throws IOException
     */
    public static String getCarrierTasks(Account account) throws IOException {
        Map<String, String> map = WebUtils.doPost(Constants.BASEURL + "/tasks",
                "{\"account\":\"" + account.getAccount() + "\"," +
                        "\"password\":\""+ account.getPassword() +"\"," +
                        "\"user_id\":\"" + account.getUserId() + "\"," +
                        "\"origin\":\"" + account.getOrigin() + "\"," +
                        "\"real_name\":\"" + account.getRealName() +"\"," +
                        "\"id_card\":\"" + account.getIdCard() + "\"," +
                        "\"login_type\":\"" + account.getLoginType() + "\"}");
        return WebUtils.handleMap(map);
    }

    /**
     * 查询Task状态,并使用验证码验证
     * @throws IOException
     */
    public static void getCarrierTaskStatus(String task_id) throws IOException {
        long pollEndTime = System.currentTimeMillis() + 180 * 1000;
        Map<String, String> map = new HashMap<String, String>();
        boolean isTaskDone = false;
        while(true){
            map =  WebUtils.doGet(Constants.BASEURL + "/tasks/" + task_id +"/status", "", true);
            int statusCode = Integer.parseInt(map.get("httpStatusCode"));
            if(statusCode>=200 && statusCode<300) {

                JSONObject result_json = (JSONObject) JSON.parse(map.get("result"));
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
                    if (inputType.equalsIgnoreCase("img")) {
                        System.out.println("base64在线转图片,访问 http://codebeautify.org/base64-to-image-converter");
                        System.out.println("请识别图片验证码:" + (String) result_json.get("value"));
                    }
                    System.out.print("请输入验证码:");
                    Scanner scanner = new Scanner(System.in);
                    String result = scanner.nextLine();
                    if (result != null) {
                        WebUtils.doPost(Constants.BASEURL + "/tasks/" + task_id + "/input", "{\"input\":\""+ result +"\"}");
                    }
                }

            }else{
                System.out.println("任务状态查询失败");
                System.out.println(map.get("httpStatusMsg"));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (System.currentTimeMillis() > pollEndTime) break;
        }

        if(isTaskDone){
            System.out.println("可以查数据了");
        }

    }
}
