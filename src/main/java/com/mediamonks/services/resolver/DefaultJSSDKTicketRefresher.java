package com.mediamonks.services.resolver;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.utils.HttpClientUtils;
import com.mediamonks.utils.PropertiesUtils;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Created by zhangchen on 16/6/14.
 */
public class DefaultJSSDKTicketRefresher implements JSSDKTicketRefresher {
    protected String accessTokenUrl = PropertiesUtils.get(PropertiesUtils.ACCESSTOKENURL);
    protected String jsApiTicketUrl = PropertiesUtils.get(PropertiesUtils.JSAPITICKETURL);

    @Override
    public void handle(WeChatAccount weChatAccount) throws Exception {
        Assert.notNull(weChatAccount.getAppId());
        Assert.notNull(weChatAccount.getAppSecret());
        String appId = weChatAccount.getAppId();
        String appSecret = weChatAccount.getAppSecret();
        String accessTokenObtainUrl = this.accessTokenUrl + "&appid=" + appId + "&secret=" + appSecret;
        JSONObject jsonResponse = HttpClientUtils.getJsonResponse(accessTokenObtainUrl);
        String access_token = jsonResponse.getString("access_token");
        String jsApiTicketObtainUrl = this.jsApiTicketUrl + "?access_token=" + access_token + "&type=jsapi";

        jsonResponse = HttpClientUtils.getJsonResponse(jsApiTicketObtainUrl);
        String ticket = jsonResponse.getString("ticket");
        weChatAccount.changeTicket(ticket);
    }

    @Override
    public boolean supports(WeChatAccount weChatAccount) {
        return !weChatAccount.usingClientServer();
    }
}
