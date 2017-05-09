package com.moxie.sdk.common;

import com.moxie.sdk.entity.MoxieResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FUFAN on 2017/5/9.
 */
public class MoxieWebUtils {

    private static final String  DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";

    private static final Map<String,String> map = new HashMap<String, String>();

    private MoxieWebUtils(){}

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @param isApiKey
     *            请求通过何种认证：apikey，token
     * @param isGzip
     *            返回的流是否压缩
     * @return URL所代表远程资源的响应
     */
    public static MoxieResponse doGet(String url, String param, boolean isApiKey, boolean isGzip) {
        MoxieResponse moxieResponse = new MoxieResponse();
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
//            httpURLConnection.setRequestProperty("Accept-Encoding","none");
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
            if(isApiKey)
                httpURLConnection.setRequestProperty("Authorization", Constants.APIKEY);
            else
                httpURLConnection.setRequestProperty("Authorization", Constants.TOKEN);
            // 建立实际的连接
            httpURLConnection.connect();
            // 定义BufferedReader输入流来读取URL的响应
            moxieResponse.setHttpStatusCode(httpURLConnection.getResponseCode());
            moxieResponse.setHttpStatusMsg(httpURLConnection.getResponseMessage());

            if(isGzip)
                result = GzipUtil.uncompress(httpURLConnection.getInputStream());
            else{
                in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(),"UTF-8"));

                String line = "";

                while ((line = in.readLine()) != null) {
                    result += line;
                }
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
        moxieResponse.setResult(result);
        return moxieResponse;
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
    public static MoxieResponse doPost(String url, String param) {
        MoxieResponse moxieResponse = new MoxieResponse();
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

            moxieResponse.setHttpStatusCode(httpURLConnection.getResponseCode());
            moxieResponse.setHttpStatusMsg(httpURLConnection.getResponseMessage());

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            result = GzipUtil.uncompress(httpURLConnection.getInputStream());
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
        moxieResponse.setResult(result);
        return moxieResponse;
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
