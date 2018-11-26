package com.lazy.falcon.core.model.input;

/**
 * <p>
 *     API  响应参数 Item 输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public class ApiResponseParamItemInputModel {

    /**
     * 参数名称
     */
    private String name;
    /**
     * 参数描述
     */
    private String desc;
    /**
     * 参数备注
     */
    private String remark;
    /**
     * 类型
     */
    private String type;

    /**
     * 组标题
     */
    private String groupTitle;

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
