package com.lazy.falcon.core.parser.dto;

/**
 * <p>
 * 解析当前Api DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/9.
 */
public class ApiDto {

    /**
     * 当前Api 配置文件路径
     */
    private String nowApiConfigFilePath;
    /**
     * 当前Api Html Name
     */
    private String nowApiHtmlName;
    /**
     * 定义当前API URL
     */
    private String basicUri;

    public String getBasicUri() {
        return basicUri;
    }

    public void setBasicUri(String basicUri) {
        this.basicUri = basicUri;
    }

    public String getNowApiConfigFilePath() {
        return nowApiConfigFilePath + ".json";
    }

    public void setNowApiConfigFilePath(String nowApiConfigFilePath) {
        this.nowApiConfigFilePath = nowApiConfigFilePath;
    }

    public String getNowApiHtmlName() {
        return nowApiHtmlName;
    }

    public void setNowApiHtmlName(String nowApiHtmlName) {
        this.nowApiHtmlName = nowApiHtmlName;
    }
}
