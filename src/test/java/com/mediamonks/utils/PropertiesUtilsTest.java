package com.mediamonks.utils;

import com.mediamonks.MmWeChatApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MmWeChatApplication.class)
@WebAppConfiguration
public class PropertiesUtilsTest {
    @Test
    public void get() throws Exception {
        assertNotNull(PropertiesUtils.get("access_token.url"));
        assertNull(PropertiesUtils.get("test123"));
    }

    @Test
    public void getInteger() throws Exception {
        assertNull(PropertiesUtils.getInteger("access_token.url"));
        assertNull(PropertiesUtils.getInteger("test123"));
    }

}