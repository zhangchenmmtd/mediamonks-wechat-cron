package com.mediamonks.services.resolver;

import com.mediamonks.domain.WeChatAccount;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/14.
 */
public class CustomJSSDKTicketRefresherTest {
    @Test
    public void handle_exception() throws Exception {
        CustomJSSDKTicketRefresher customJSSDKTicketRefresher = new CustomJSSDKTicketRefresher();
        WeChatAccount weChatAccount = new WeChatAccount();
        try {
            customJSSDKTicketRefresher.handle(weChatAccount);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            weChatAccount.changeClientServerInfo("e9b13bebacc44cabbb88b1fadc0fdc6e","8db121d250104362974b47570a356cc4");
            customJSSDKTicketRefresher.handle(weChatAccount);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            weChatAccount.changeUrls("http://api.weixin.oookini.com/token",null);
            customJSSDKTicketRefresher.handle(weChatAccount);
            fail("Should throw exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void handle() throws Exception {
        CustomJSSDKTicketRefresher customJSSDKTicketRefresher = new CustomJSSDKTicketRefresher();
        WeChatAccount weChatAccount = new WeChatAccount();
        weChatAccount.changeClientServerInfo("e9b13bebacc44cabbb88b1fadc0fdc6e","8db121d250104362974b47570a356cc4");
        weChatAccount.changeUrls("http://api.weixin.oookini.com/token","http://api.weixin.oookini.com/ticket");
        customJSSDKTicketRefresher.handle(weChatAccount);
        assertNotNull(weChatAccount.getTicket());
        assertNotNull(weChatAccount.getAccessToken());
    }


    @Test
    public void supports_false() throws Exception {
        CustomJSSDKTicketRefresher customJSSDKTicketRefresher = new CustomJSSDKTicketRefresher();
        WeChatAccount weChatAccount = new WeChatAccount();
        assertFalse(customJSSDKTicketRefresher.supports(weChatAccount));
    }

    @Test
    public void supports_true() throws Exception {
        CustomJSSDKTicketRefresher customJSSDKTicketRefresher = new CustomJSSDKTicketRefresher();
        WeChatAccount weChatAccount = new WeChatAccount();
        weChatAccount.changeClientServerInfo("test","test1");
        assertTrue(customJSSDKTicketRefresher.supports(weChatAccount));
    }

}