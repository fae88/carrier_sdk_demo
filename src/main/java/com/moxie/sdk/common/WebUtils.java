package com.moxie.sdk.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MX on 2017/4/26.
 * 通过简单的URLConnection发送Get和Post方法
 */
public class WebUtils {
    private static final String  DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static final Map<String,String> map = new HashMap<String, String>();

    private WebUtils(){}

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static Map<String,String> doGet(String url, String param, boolean isApiKey) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            httpURLConnection.setRequestMethod(METHOD_GET);
            // 设置通用的请求属性
            httpURLConnection.setRequestProperty("user-agent",Constants.USER_AGENT);
            httpURLConnection.setRequestProperty("content-type", Constants.CONTENT_TYPE);
            if(isApiKey)
                httpURLConnection.setRequestProperty("Authorization", Constants.APIKEY);
            else
                httpURLConnection.setRequestProperty("Authorization", Constants.TOKEN);
            // 建立实际的连接
            httpURLConnection.connect();
            // 获取所有响应头字段
//            Map<String, List<String>> map = conn.getHeaderFields();
//            // 遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
//            }
            // 定义BufferedReader输入流来读取URL的响应
            map.put("httpStatusCode", String.valueOf(httpURLConnection.getResponseCode()));
            map.put("httpStatusMsg", httpURLConnection.getResponseMessage());
            in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));
//            String line = new String("".getBytes(), "UTF-8");

            String line = "";

            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        map.put("result", result);
        return map;
    }
    /**
     * 向指定URL发送POST方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数:raw。
     * @return URL所代表远程资源的响应
     */
    public static Map<String, String> doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) realUrl.openConnection();
            httpURLConnection.setRequestMethod(METHOD_POST);
            // 设置通用的请求属性
            httpURLConnection.setRequestProperty("user-agent",Constants.USER_AGENT);
            httpURLConnection.setRequestProperty("content-type", Constants.CONTENT_TYPE);
            httpURLConnection.setRequestProperty("Authorization", Constants.APIKEY);
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应

            map.put("httpStatusCode", String.valueOf(httpURLConnection.getResponseCode()));
            map.put("httpStatusMsg", httpURLConnection.getResponseMessage());
            in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        map.put("result", result);
        return map;
    }

    public static String handleMap(Map<String,String> map){
        int statusCode = Integer.parseInt(map.get("httpStatusCode"));
        if(statusCode>=200 && statusCode<300){
            return map.get("result");
        }else{
            System.out.println(map.get("httpStatusMsg"));
        }
        return "";
    }


}
