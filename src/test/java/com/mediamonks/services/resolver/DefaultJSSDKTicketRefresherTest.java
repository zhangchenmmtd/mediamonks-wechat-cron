package com.mediamonks.services.resolver;

import com.mediamonks.domain.WeChatAccount;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/14.
 */
public class DefaultJSSDKTicketRefresherTest {
    @Test
    public void handle() throws Exception {
        DefaultJSSDKTicketRefresher defaultJSSDKTicketRefresher = new DefaultJSSDKTicketRefresher();
        defaultJSSDKTicketRefresher.accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";
        defaultJSSDKTicketRefresher.jsApiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
        WeChatAccount weChatAccount = new WeChatAccount();
        weChatAccount.initial("wx8c57d4b45dc0cfdb","389ee2ea3896608468e112b227a14fbc");
        defaultJSSDKTicketRefresher.handle(weChatAccount);
        assertNotNull(weChatAccount.getTicket());
    }

    @Test
    public void handle_exception() throws Exception {
        DefaultJSSDKTicketRefresher defaultJSSDKTicketRefresher = new DefaultJSSDKTicketRefresher();

        WeChatAccount weChatAccount = new WeChatAccount();
        try {
            defaultJSSDKTicketRefresher.handle(weChatAccount);
            fail("Why no exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            weChatAccount.initial("test",null);
            defaultJSSDKTicketRefresher.handle(weChatAccount);
            fail("Why no exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

    }


    @Test
    public void supports() throws Exception {
        DefaultJSSDKTicketRefresher defaultJSSDKTicketRefresher = new DefaultJSSDKTicketRefresher();
        assertTrue(defaultJSSDKTicketRefresher.supports(new WeChatAccount()));
    }

}