package com.mediamonks.services.resolver;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.utils.HttpClientUtils;
import com.mediamonks.utils.PropertiesUtils;
import com.mediamonks.utils.StringUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by zhangchen on 16/6/14.
 */
public class CustomJSSDKTicketRefresher implements JSSDKTicketRefresher{


    @Override
    public void handle(WeChatAccount weChatAccount) throws Exception {
        String appId = weChatAccount.getClientServerID();
        String appSecret = weChatAccount.getClientServerSecrect();
        String accessTokenUrl = weChatAccount.getAccessTokenUrl();
        String jsSDKTicketUrl = weChatAccount.getJsSDKTicketUrl();
        Assert.notNull(appId,"Client server ID can not be null");
        Assert.notNull(appSecret,"Client server secret can not be null");
        Assert.notNull(accessTokenUrl,"Access token url can not be null");
        Assert.notNull(jsSDKTicketUrl,"JS sdk ticket url can not be null");

        String nonString = UUID.randomUUID().toString();
        long timestamp = System.currentTimeMillis() / 1000;
        String string1 = "nonce=" + nonString + "&oid="+appId+"&timestamp="+timestamp;
        String string2 = string1 + appSecret;
        String sign = StringUtils.MD5(string2);
        accessTokenUrl += "?nonce="+nonString+"&oid="+appId+"&timestamp="+timestamp+"&sign="+sign;
        jsSDKTicketUrl += "?nonce="+nonString+"&oid="+appId+"&timestamp="+timestamp+"&sign="+sign;
        JSONObject jsonResponse = HttpClientUtils.getJsonResponse(accessTokenUrl);
        String access_token = jsonResponse.getString("access_token");
        weChatAccount.changeAccessToken(access_token);

        JSONObject ticketResponse = HttpClientUtils.getJsonResponse(jsSDKTicketUrl);
        String jsTicket = ticketResponse.getString("js_ticket");
        weChatAccount.changeTicket(jsTicket);
    }

    @Override
    public boolean supports(WeChatAccount weChatAccount) {
        return weChatAccount.usingClientServer();
    }
}
