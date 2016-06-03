package com.mediamonks.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.domain.Client;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.ClientRepository;
import com.mediamonks.repository.WechatAccountRepository;
import com.mediamonks.services.WechatService;
import com.mediamonks.utils.HttpClientUtils;
import com.mediamonks.utils.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhangchen on 16/3/11.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service("wechatService")
public class DefaultWechatService implements WechatService {

    private String accessTokenUrl = PropertiesUtils.get(PropertiesUtils.ACCESSTOKENURL);
    private String jsApiTicketUrl = PropertiesUtils.get(PropertiesUtils.JSAPITICKETURL);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Scheduled(fixedRate = 1000 * 60)
    public void refreshJsApiTickets() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if(client.hasWeChatAccounts() && client.isEnabled()){
                List<WeChatAccount> weChatAccounts = client.getWeChatAccounts();
                for (WeChatAccount weChatAccount : weChatAccounts) {
                    if(weChatAccount.needUpdate()){
                        refreshJsApiTicket(weChatAccount);
                    }
                }
            }
        }
    }

    private void refreshJsApiTicket(WeChatAccount weChatAccount) {
        String appId = weChatAccount.getAppId();
        String appSecret = weChatAccount.getAppSecret();


        String accessTokenObtainUrl = accessTokenUrl + "&appid=" + appId + "&secret=" + appSecret;
        JSONObject jsonResponse = HttpClientUtils.getJsonResponse(accessTokenObtainUrl);
        if(jsonResponse != null){
            String access_token = jsonResponse.getString("access_token");

            String jsApiTicketObtainUrl = jsApiTicketUrl + "?access_token=" + access_token + "&type=jsapi";

            jsonResponse = HttpClientUtils.getJsonResponse(jsApiTicketObtainUrl);
            if(jsonResponse != null){
                String ticket = jsonResponse.getString("ticket");
                weChatAccount.changeTicket(ticket);
            }
        }


    }

}
