package com.lazy.falcon.core.model.input;

import java.util.List;

/**
 * <p>
 *     API 请求参数Group 输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 *
 * @see ApiRequestParamItemInputModel
 */
public class ApiRequestParamGroupInputModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 参数Item 数组
     */
    private List<ApiRequestParamItemInputModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ApiRequestParamItemInputModel> getItems() {
        return items;
    }

    public void setItems(List<ApiRequestParamItemInputModel> items) {
        this.items = items;
    }
}
