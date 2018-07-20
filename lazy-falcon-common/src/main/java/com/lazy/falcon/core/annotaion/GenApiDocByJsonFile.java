package com.lazy.falcon.core.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 *     通过JSON文件方式生成 API DOC
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenApiDocByJsonFile {

    /**
     * JSON 文件位置
     * @return
     */
    String value();
}
