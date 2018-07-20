package com.lazy.falcon.core.parser;

import com.lazy.falcon.core.dto.ParserDocDto;

/**
 * <p>
 *     生成API DOC解析接口定义
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/2.
 */
public interface IParser {

    /**
     * 解析方法
     * @param docDto DTO对象
     */
    void doParser(ParserDocDto docDto);
}
