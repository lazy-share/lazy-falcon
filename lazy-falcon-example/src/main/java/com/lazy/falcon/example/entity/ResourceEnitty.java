package com.lazy.falcon.example.entity;

import java.io.Serializable;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/7/26.
 */
public class ResourceEnitty implements Serializable {

    private static final long serialVersionUID = 994565362L;

    private String resourceCode;
    private String resourceName;

    public String getResourceCode() {
        return resourceCode;
    }

    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }
}
