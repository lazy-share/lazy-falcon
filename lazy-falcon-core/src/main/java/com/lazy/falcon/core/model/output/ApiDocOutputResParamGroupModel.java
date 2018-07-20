package com.lazy.falcon.core.model.output;

import com.lazy.falcon.core.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     API DOC 响应参数Group 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 *
 * @see ApiDocOutputResParamItemModel
 */
public class ApiDocOutputResParamGroupModel implements Serializable{

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 8969896574679L;

    private List<ApiDocOutputResParamItemModel> rows = SetsUtils.list();

    public List<ApiDocOutputResParamItemModel> getRows() {
        return rows;
    }

    public void setRows(List<ApiDocOutputResParamItemModel> rows) {
        this.rows = rows;
    }
}
