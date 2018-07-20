package com.lazy.falcon.core.model.input;

/**
 * <p>
 *     API DOC 请求参数 Item  输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public class ApiDocInputReqParamItemModel {

    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数描述
     */
    private String desc;
    /**
     * 参数类型
     */
    private String type;
    /**
     * 参数备注
     */
    private String remark;
    /**
     * 是否必须
     */
    private boolean required;
    /**
     * 对象字段
     */
    private String fieldsTitle;

    public String getFieldsTitle() {
        return fieldsTitle;
    }

    public void setFieldsTitle(String fieldsTitle) {
        this.fieldsTitle = fieldsTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
