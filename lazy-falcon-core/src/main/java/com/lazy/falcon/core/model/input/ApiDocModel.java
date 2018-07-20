package com.lazy.falcon.core.model.input;

import java.util.List;

/**
 * <p>
 *     Api Doc 模型
 *     映射 JSON FILE 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 *
 * @see ApiDocInputReqParamGroupModel
 * @see ApiDocInputResParamGroupModel
 */
public class ApiDocModel {

    /**
     * Api所属模块
     */
    private String module;
    /**
     * 作者
     */
    private String author;
    /**
     * 模块Item
     */
    private String moduleItem;
    /**
     * Api url
     */
    private String url;
    /**
     * Api Method
     */
    private String method;
    /**
     * Api 描述
     */
    private String desc;
    /**
     * Api 创建时间
     */
    private String createDate;
    /**
     * Api 最后更新时间
     */
    private String lstUpdDate;
    /**
     * 请求参数信息
     */
    private List<ApiDocInputReqParamGroupModel> reqParams;
    /**
     * 响应参数信息
     */
    private List<ApiDocInputResParamGroupModel> resParams;

    public String getModule() {
        return module.trim();
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModuleItem() {
        return moduleItem;
    }

    public void setModuleItem(String moduleItem) {
        this.moduleItem = moduleItem;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLstUpdDate() {
        return lstUpdDate;
    }

    public void setLstUpdDate(String lstUpdDate) {
        this.lstUpdDate = lstUpdDate;
    }

    public List<ApiDocInputReqParamGroupModel> getReqParams() {
        return reqParams;
    }

    public void setReqParams(List<ApiDocInputReqParamGroupModel> reqParams) {
        this.reqParams = reqParams;
    }

    public List<ApiDocInputResParamGroupModel> getResParams() {
        return resParams;
    }

    public void setResParams(List<ApiDocInputResParamGroupModel> resParams) {
        this.resParams = resParams;
    }
}
