package com.mediamonks.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.mediamonks.domain.Client;
import com.mediamonks.domain.WeChatAccount;
import com.mediamonks.repository.ClientRepository;
import com.mediamonks.repository.WechatAccountRepository;
import com.mediamonks.services.WechatService;
import com.mediamonks.services.resolver.CustomJSSDKTicketRefresher;
import com.mediamonks.services.resolver.DefaultJSSDKTicketRefresher;
import com.mediamonks.services.resolver.JSSDKTicketRefresher;
import com.mediamonks.utils.HttpClientUtils;
import com.mediamonks.utils.PropertiesUtils;
import com.mediamonks.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by zhangchen on 16/3/11.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service("wechatService")
public class WechatJSSDKTicketRefreshersHolder implements WechatService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private JSSDKTicketRefresher[] resolvers = new JSSDKTicketRefresher[]{
            new DefaultJSSDKTicketRefresher(),
            new CustomJSSDKTicketRefresher()
    };

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Scheduled(fixedRate = 1000 * 60)
    public void cronAction() {
        List<Client> clients = clientRepository.findAll();
        for (Client client : clients) {
            if (client.hasWeChatAccounts() && client.isEnabled()) {
                List<WeChatAccount> weChatAccounts = client.getWeChatAccounts();
                for (WeChatAccount weChatAccount : weChatAccounts) {
                    if (weChatAccount.needUpdate()) {
                        refreshJsApiTicket(weChatAccount);
                    }
                }
            }
        }
    }


    protected void refreshJsApiTicket(WeChatAccount weChatAccount) {
        Assert.notNull(weChatAccount,"Wechat account can not be null");
        for (JSSDKTicketRefresher resolver : resolvers) {
            if(resolver.supports(weChatAccount)){
                try {
                    resolver.handle(weChatAccount);
                    log.info("Get jssdk ticket for ["+weChatAccount.getClient().getName()+"] ["+weChatAccount.getTicket()+"]");
                } catch (Exception e) {
                    log.error("ERROR get jssdk ticket for ["+weChatAccount.getClient().getName()+"]");
                    log.error(e.getMessage(),e);
                }
            }
        }
    }
}
