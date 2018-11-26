package com.lazy.falcon.common.annotaion;

import java.lang.annotation.*;

/**
 * <p>
 * API 参数组
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParamGroup {

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
    String value() default "";

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
     * 对应组的标题 作用在对象属性时，该字段必填
     *
     * @return {@link String}
     */
    String groupTitle() default "";

    /**
     * 是否参数项
     *
     * @return {@link Boolean}
     */
    boolean isParamItem() default false;

    /**
     * 数组或列表时，需要定义连接的类
     *
     * @return {@link Class}
     */
    Class<?> linkCls() default DefaultLinkCls.class;

    /**
     * 类型 默认Object
     *
     * @return {@link String}
     */
    String type() default "Object";

    /**
     * 指定包含哪些属性
     *
     * @return {@link String}
     */
    String[] includeItems() default {};

    /**
     * 排除哪些属性
     *
     * @return {@link String}
     */
    String[] excludeItems() default {};

    /**
     * 是否使用当前模块的class
     *
     * @return {@link Boolean}
     */
    boolean isUseModuleCls() default false;

}
