package com.lazy.falcon.core.model.input;

import java.util.List;

/**
 * <p>
 *     API  响应参数Group 输入模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 *
 * @see ApiResponseParamItemInputModel
 */
public class ApiResponseParamGroupInputModel {

    /**
     * 标题
     */
    private String title;

    /**
     * 参数Item 数组
     */
    private List<ApiResponseParamItemInputModel> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ApiResponseParamItemInputModel> getItems() {
        return items;
    }

    public void setItems(List<ApiResponseParamItemInputModel> items) {
        this.items = items;
    }
}
