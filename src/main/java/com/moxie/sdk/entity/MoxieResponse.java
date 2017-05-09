package com.moxie.sdk.entity;

/**
 * Created by FUFAN on 2017/5/9.
 */
public class MoxieResponse {

    int httpStatusCode;
    String httpStatusMsg;
    String result;

    public MoxieResponse(){

    }

    public MoxieResponse(int httpStatusCode, String httpStatusMsg, String result) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatusMsg = httpStatusMsg;
        this.result = result;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public String getHttpStatusMsg() {
        return httpStatusMsg;
    }

    public void setHttpStatusMsg(String httpStatusMsg) {
        this.httpStatusMsg = httpStatusMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "MoxieResponse{" +
                "httpStatusCode=" + httpStatusCode +
                ", httpStatusMsg='" + httpStatusMsg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
