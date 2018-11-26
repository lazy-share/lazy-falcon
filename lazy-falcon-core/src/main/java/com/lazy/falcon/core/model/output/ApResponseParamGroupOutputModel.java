package com.lazy.falcon.core.model.output;

import com.lazy.falcon.common.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * API  响应参数Group 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 * @see ApiResponseParamItemOutputModel
 */
public class ApResponseParamGroupOutputModel implements Serializable {

    /**
     * 序列化版本号
     */
    private static final long serialVersionUID = 8969896574679L;

    private List<ApiResponseParamItemOutputModel> rows = SetsUtils.list();

    public List<ApiResponseParamItemOutputModel> getRows() {
        return rows;
    }

    public void setRows(List<ApiResponseParamItemOutputModel> rows) {
        this.rows = rows;
    }
}
