package com.moxie.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moxie.sdk.entity.Account;
import com.moxie.sdk.service.carrier.BillQueryService;
import com.moxie.sdk.service.carrier.ResetPasswordService;
import com.moxie.sdk.service.carrier.TaskCreateService;
import com.moxie.sdk.service.carrier.UserReportService;
import org.junit.Test;
import test_data.TestDataConstants;

import java.io.IOException;

/**
 * Created by MX on 2017/4/26.
 * 测试运营商的接口服务
 */
public class CarrierTests {

    public static String taskId = "";

    public static void main(String[] args) {
//        //测试任务创建验证
//        testTaskCreateService();
//
//        //测试重置密码
//        testResetPasswordService();
//
//        //测试账单查询
//        testBillQueryService();
//
//        //测试用户报告
//        testUserReportService();
    }


    public static void testTaskCreateService(){
        try {
            //测试运营商可用渠道
            String channels = TaskCreateService.getCarrierChannels(TestDataConstants.account);
            System.out.println(channels);

            //step1。创建Task
            String taskIdResult = TaskCreateService.getCarrierTasks(new Account(
                    TestDataConstants.account,
                    TestDataConstants.password,
                    TestDataConstants.userId,
                    TestDataConstants.origin,
                    TestDataConstants.realName,
                    TestDataConstants.idCard,
                    TestDataConstants.loginType));
            JSONObject taskIdJson = (JSONObject) JSON.parse(taskIdResult);
            taskId = (String)taskIdJson.get("task_id");
            System.out.println("任务创建成功: " + taskId);

            //step2。轮询查询Task，并测试验证码
            TaskCreateService.getCarrierTaskStatus(taskId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void testResetPasswordService(){
        //创建修改密码Task
        String resetTaskResult = ResetPasswordService.getResetTask(new Account(
                TestDataConstants.account,
                TestDataConstants.password,
                TestDataConstants.userId,
                TestDataConstants.origin,
                TestDataConstants.realName,
                TestDataConstants.idCard,
                TestDataConstants.loginType));


        //通过task和验证短信及新密码去重置密码
        JSONObject resetTaskResultJson = (JSONObject) JSON.parse(resetTaskResult);
        String resetTaskId = (String)resetTaskResultJson.get("task_id");
        String resetResult = ResetPasswordService.validateSMS(resetTaskId, TestDataConstants.newPassword);

        System.out.println(resetResult);

    }

    public static void testUserReportService(){
        //用户报告
        String userReport = UserReportService.getUserReport(TestDataConstants.account);
        System.out.println("用户报告：" + userReport);

    }

    public static void testBillQueryService(){
        testTaskCreateService();

        //获取账单的基本信息
        String billBasic = BillQueryService.getBasicRecord(taskId,TestDataConstants.account);

        //获取账号的账单记录
        String billRecord = BillQueryService.getBillRecord(taskId,"", "", TestDataConstants.account);

        //获取账号通话详情
        String callDetailed = BillQueryService.getCallDetailed(taskId,"", TestDataConstants.account);

        //获取短信详情
        String smsDetailed = BillQueryService.getSMSDetailed(taskId,"",TestDataConstants.account);

        System.out.println("账单基本信息：" + billBasic);
        System.out.println("账单记录：" + billRecord);
        System.out.println("通话详情：" + callDetailed);
        System.out.println("短信详情：" + smsDetailed);
    }

}
