package com.lazy.falcon.core.model.input;

import java.util.List;

/**
 * <p>
 *     API DOC 响应参数Group 输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 *
 * @see ApiDocInputResParamItemModel
 */
public class ApiDocInputResParamGroupModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 参数Item 数组
     */
    private List<ApiDocInputResParamItemModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ApiDocInputResParamItemModel> getItems() {
        return items;
    }

    public void setItems(List<ApiDocInputResParamItemModel> items) {
        this.items = items;
    }
}
