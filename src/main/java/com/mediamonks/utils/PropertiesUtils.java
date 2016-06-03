package com.mediamonks.utils;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhangchen on 16/6/1.
 */
public class PropertiesUtils {
    public static final String ACCESSTOKENURL = "access_token.url";
    public static final String JSAPITICKETURL = "jsapi_ticket.url";

    public static String get(String key){
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Integer getInteger(String key){
        try {
            Properties properties = PropertiesLoaderUtils.loadAllProperties("application.properties");
            return Integer.valueOf(properties.getProperty(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
