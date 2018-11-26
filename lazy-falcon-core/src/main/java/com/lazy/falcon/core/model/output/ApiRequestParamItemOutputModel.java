package com.lazy.falcon.core.model.output;

import java.io.Serializable;

/**
 * <p>
 *     API DOC 请求参数Item 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
public class ApiRequestParamItemOutputModel implements Serializable{

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 6576574679L;

    /**
     * 字段
     */
    private String reqParamField;
    /**
     * 描述
     */
    private String reqParamFieldDesc;
    /**
     * 类型
     */
    private String reqParamFieldType;
    /**
     * 备注
     */
    private String reqParamFieldRemark;
    /**
     * 是否必须
     */
    private String reqParamFieldRequired;

    public String getReqParamFieldRequired() {
        return reqParamFieldRequired;
    }

    public void setReqParamFieldRequired(String reqParamFieldRequired) {
        this.reqParamFieldRequired = reqParamFieldRequired;
    }

    public String getReqParamField() {
        return reqParamField;
    }

    public void setReqParamField(String reqParamField) {
        this.reqParamField = reqParamField;
    }

    public String getReqParamFieldDesc() {
        return reqParamFieldDesc;
    }

    public void setReqParamFieldDesc(String reqParamFieldDesc) {
        this.reqParamFieldDesc = reqParamFieldDesc;
    }

    public String getReqParamFieldType() {
        return reqParamFieldType;
    }

    public void setReqParamFieldType(String reqParamFieldType) {
        this.reqParamFieldType = reqParamFieldType;
    }

    public String getReqParamFieldRemark() {
        return reqParamFieldRemark;
    }

    public void setReqParamFieldRemark(String reqParamFieldRemark) {
        this.reqParamFieldRemark = reqParamFieldRemark;
    }
}
