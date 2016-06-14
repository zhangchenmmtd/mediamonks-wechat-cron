package com.mediamonks.services.resolver;

import com.mediamonks.domain.WeChatAccount;

import java.io.IOException;

/**
 * Created by zhangchen on 16/6/14.
 */
public interface JSSDKTicketRefresher {

    void handle(WeChatAccount weChatAccount) throws Exception;

    boolean supports(WeChatAccount weChatAccount);
}
