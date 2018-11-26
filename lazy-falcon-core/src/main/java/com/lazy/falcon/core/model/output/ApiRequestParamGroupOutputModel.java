package com.lazy.falcon.core.model.output;

import com.lazy.falcon.common.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     API 请求参数Group 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 *
 * @see ApiRequestParamItemOutputModel
 */
public class ApiRequestParamGroupOutputModel implements Serializable{

    private static final long serialVersionUID = 780978689L;

    private List<ApiRequestParamItemOutputModel> rows = SetsUtils.list();

    public List<ApiRequestParamItemOutputModel> getRows() {
        return rows;
    }

    public void setRows(List<ApiRequestParamItemOutputModel> rows) {
        this.rows = rows;
    }
}
