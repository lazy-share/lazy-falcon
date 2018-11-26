package com.lazy.falcon.common.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 * 通过JSON配置文件方式生成 API DOC
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiConfig {

    /**
     * JSON 文件位置
     *
     * @return {@link String}
     */
    String value();
}
