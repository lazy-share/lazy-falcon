package com.lazy.falcon.core.model.output;

import com.lazy.falcon.core.tools.SetsUtils;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *     API DOC 请求参数Group 输出 模型
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 *
 * @see ApiDocOutputReqParamItemModel
 */
public class ApiDocOutputReqParamGroupModel implements Serializable{

    private static final long serialVersionUID = 780978689L;

    private List<ApiDocOutputReqParamItemModel> rows = SetsUtils.list();

    public List<ApiDocOutputReqParamItemModel> getRows() {
        return rows;
    }

    public void setRows(List<ApiDocOutputReqParamItemModel> rows) {
        this.rows = rows;
    }
}
