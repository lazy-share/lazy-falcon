package com.lazy.falcon.core.model.input;

import java.util.List;

/**
 * <p>
 *     API DOC 请求参数Group 输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 *
 * @see ApiDocInputReqParamItemModel
 */
public class ApiDocInputReqParamGroupModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 参数Item 数组
     */
    private List<ApiDocInputReqParamItemModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ApiDocInputReqParamItemModel> getItems() {
        return items;
    }

    public void setItems(List<ApiDocInputReqParamItemModel> items) {
        this.items = items;
    }
}
