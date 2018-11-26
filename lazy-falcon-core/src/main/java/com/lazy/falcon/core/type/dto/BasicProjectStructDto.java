package com.lazy.falcon.core.type.dto;

import org.apache.maven.plugin.logging.Log;

import java.io.Serializable;

/**
 * <p>
 *     项目类型 父类 DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public class BasicProjectStructDto implements Serializable{

    private static final long serialVersionUID = 9887966341465462L;

    /**
     * Maven Plugin Logger
     */
    private org.apache.maven.plugin.logging.Log log;

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
