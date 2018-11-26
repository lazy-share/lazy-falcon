package com.lazy.falcon.common.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 * 凡是需要生成Api的控制器均需要在类上添加该注解
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/10/15.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiModule {

    /**
     * 模块名称
     *
     * @return {@link String}
     */
    String value() default "";

    /**
     * 模块对应的实体
     *
     * @return {@link Class}
     */
    Class<?> moduleCls();

}
