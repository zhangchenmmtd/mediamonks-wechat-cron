package com.mediamonks.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by zhangchen on 16/6/14.
 */
public class StringUtilsTest {

    @Test
    public void encodeSHA() throws Exception {
        assertNotNull(StringUtils.encodeSHA("test"));
        try {
            StringUtils.encodeSHA(" ");
            fail("where is exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void MD5() throws Exception {
        assertNotNull(StringUtils.MD5("test"));
        try {
            StringUtils.MD5(" ");
            fail("where is exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void hasText() throws Exception {
        assertTrue(StringUtils.hasText("test"));
        assertTrue(StringUtils.hasText("123"));
    }

    @Test
    public void hasText_null_arg() throws Exception {
        try {
            StringUtils.hasText(null);
            fail("where is exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            StringUtils.hasText(" ");
            fail("where is exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }

        try {
            StringUtils.hasText("");
            fail("where is exception");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

}