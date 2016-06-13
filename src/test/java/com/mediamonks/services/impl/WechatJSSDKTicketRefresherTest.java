package com.mediamonks.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.utils.HttpClientUtils;
import com.mediamonks.utils.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/13.
 */
public class WechatJSSDKTicketRefresherTest {


    @Test
    public void testNewTokenUrl() throws IOException {
        String token = getAccessToken();
        System.out.println("token: "+token);
        String js_ticket = getTicket();
        System.out.println("ticket: " + js_ticket);
    }

    private String  getAccessToken() throws IOException {
        String url = "http://api.weixin.oookini.com/token?";
        String nonString = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;
        String string1 = "nonce=" + nonString + "&oid=e9b13bebacc44cabbb88b1fadc0fdc6e&timestamp="+timestamp;
        String string2 = string1 + "8db121d250104362974b47570a356cc4";
        String sign = StringUtils.MD5(string2);
        url += "nonce="+nonString+"&oid=e9b13bebacc44cabbb88b1fadc0fdc6e&timestamp="+timestamp+"&sign="+sign;
        JSONObject jsonResponse = HttpClientUtils.getJsonResponse(url);
        return jsonResponse.getString("access_token");
    }


    public String getTicket() throws IOException {
        String url = "http://api.weixin.oookini.com/ticket?";
        String nonString = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;
        String string1 = "nonce=" + nonString + "&oid=e9b13bebacc44cabbb88b1fadc0fdc6e&timestamp="+timestamp;
        String string2 = string1 + "8db121d250104362974b47570a356cc4";
        String sign = StringUtils.MD5(string2);
        url += "nonce="+nonString+"&oid=e9b13bebacc44cabbb88b1fadc0fdc6e&timestamp="+timestamp+"&sign="+sign;
        JSONObject jsonResponse = HttpClientUtils.getJsonResponse(url);
        return jsonResponse.getString("js_ticket");
    }
}