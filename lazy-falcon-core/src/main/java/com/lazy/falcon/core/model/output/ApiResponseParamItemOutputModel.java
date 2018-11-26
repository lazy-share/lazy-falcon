package com.lazy.falcon.core.model.output;

/**
 * <p>
 *     API  响应参数Group 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
public class ApiResponseParamItemOutputModel {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 888886574679L;

    /**
     * 字段
     */
    private String resParamField;
    /**
     * 描述
     */
    private String resParamFieldDesc;
    /**
     * 类型
     */
    private String resParamFieldType;
    /**
     * 备注
     */
    private String resParamFieldRemark;

    public String getResParamField() {
        return resParamField;
    }

    public void setResParamField(String resParamField) {
        this.resParamField = resParamField;
    }

    public String getResParamFieldDesc() {
        return resParamFieldDesc;
    }

    public void setResParamFieldDesc(String resParamFieldDesc) {
        this.resParamFieldDesc = resParamFieldDesc;
    }

    public String getResParamFieldType() {
        return resParamFieldType;
    }

    public void setResParamFieldType(String resParamFieldType) {
        this.resParamFieldType = resParamFieldType;
    }

    public String getResParamFieldRemark() {
        return resParamFieldRemark;
    }

    public void setResParamFieldRemark(String resParamFieldRemark) {
        this.resParamFieldRemark = resParamFieldRemark;
    }
}
