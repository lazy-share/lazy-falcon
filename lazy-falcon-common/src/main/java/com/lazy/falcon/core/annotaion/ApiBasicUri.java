package com.lazy.falcon.core.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 *     API 基础URI
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiBasicUri {

    /**
     * 域名指值
     * @return
     */
    String value();
}
