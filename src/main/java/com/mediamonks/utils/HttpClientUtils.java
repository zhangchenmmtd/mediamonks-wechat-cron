package com.mediamonks.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

/**
 * Created by zhangchen on 16/6/1.
 */
public class HttpClientUtils {
    private static HttpClient httpClient;

    static HttpClient get(){
        if(httpClient == null){
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    public static JSONObject getJsonResponse(String url){
        try {
            GetMethod getMethod = new GetMethod(url);
            get().executeMethod(getMethod);
            String response = getMethod.getResponseBodyAsString();
            return JSONObject.parseObject(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
