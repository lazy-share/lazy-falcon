package com.lazy.falcon.common.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 * API 参数项
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParamItem {

    /**
     * 名称
     *
     * @return {@link String}
     */
    String name() default "";

    /**
     * 描述
     *
     * @return {@link String}
     */
    String value();

    /**
     * 备注
     *
     * @return {@link String}
     */
    String remark() default "";

    /**
     * 是否必须
     *
     * @return {@link Boolean}
     */
    boolean required() default true;

    /**
     * 参数类型
     *
     * @return {@link String}
     */
    String type() default "";

}
