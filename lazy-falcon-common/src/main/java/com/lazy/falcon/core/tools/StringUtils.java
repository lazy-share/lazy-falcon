package com.lazy.falcon.core.tools;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/5.
 */
public class StringUtils {

    public static boolean isEmpty(String str){
        return "".equals(str) || null == str;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
