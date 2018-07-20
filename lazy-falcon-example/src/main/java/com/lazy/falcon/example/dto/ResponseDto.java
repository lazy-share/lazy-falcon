package com.lazy.falcon.example.dto;

import java.io.Serializable;

/**
 * <p>
 *     结果DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public class ResponseDto implements Serializable {

    private static final long serialVersionUID = 99942L;

    /**
     * 响应Code
     */
    private String code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应业务数据
     */
    private Object data;

    public static ResponseDto buildDto(String code, String message, Object data) {
        ResponseDto dto = new ResponseDto();
        dto.code = code;
        dto.message = message;
        dto.data = data;
        return dto;
    }

    public static ResponseDto buildSuccessfully(String message, Object data){
        ResponseDto dto = new ResponseDto();
        dto.code = "200";
        dto.message = message;
        dto.data = data;
        return dto;
    }

    public static ResponseDto buildSuccessfully(Object data){
        ResponseDto dto = new ResponseDto();
        dto.code = "200";
        dto.message = "success";
        dto.data = data;
        return dto;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
