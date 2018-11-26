package com.lazy.falcon.common.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 * 通过注解方式生成 API DOC
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    /**
     * api 名称
     *
     * @return {@link String}
     */
    String value();

    /**
     * 作者
     *
     * @return {@link String}
     */
    String author();

    /**
     * 描述
     *
     * @return {@link String}
     */
    String desc();

    /**
     * 创建日期
     *
     * @return {@link String}
     */
    String createDate() default "";

    /**
     * 最后更新日期
     *
     * @return {@link String}
     */
    String lastUpdateTime() default "";

    /**
     * 基本请求参数标题
     *
     * @return {@link String}
     */
    String reqParamTitle() default "基本请求参数";

    /**
     * 基本响应参数标题
     *
     * @return {@link String}
     */
    String resParamTitle() default "基本响应参数";

}