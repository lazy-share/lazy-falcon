package com.lazy.falcon.core.enums;

/**
 * <p>
 *     项目类型枚举
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public enum ProjectStructEnum {

    SPRING_BOOT("SpringBoot"),
    SPRING_MVS("SpringMvc"),

    ;

    private String value;

    ProjectStructEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
