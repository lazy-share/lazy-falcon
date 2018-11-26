package com.lazy.falcon.example.dto;

import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.common.annotaion.ApiParamItem;

import java.io.Serializable;

/**
 * <p>
 * 结果DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public class ResponseDto<V> implements Serializable {

    private static final long serialVersionUID = 99942L;

    /**
     * 响应Code
     */
    @ApiParamItem(value = "响应Code", remark = "200:成功；500:服务器错误; 400：请求参数错误")
    private String code;

    /**
     * 响应消息
     */
    @ApiParamItem(value = "响应消息")
    private String message;

    /**
     * 响应业务数据
     */
    @ApiParamGroup(value = "业务数据描述", isParamItem = true, remark = "参看业务数据描述", groupTitle = "业务数据描述", isUseModuleCls = true)
    private V data;

    public ResponseDto(String code, String message, V data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }
}
