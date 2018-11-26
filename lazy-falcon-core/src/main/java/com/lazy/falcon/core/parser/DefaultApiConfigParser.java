package com.lazy.falcon.core.parser;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.common.annotaion.ApiConfig;
import com.lazy.falcon.core.model.input.ApiModel;
import com.lazy.falcon.core.parser.support.AbstractApiParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.lang.reflect.Method;

/**
 * <p>
 * Default Api Config Parser Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class DefaultApiConfigParser extends AbstractApiParser {


    /**
     * 解析
     *
     * @param method 方法类
     * @return {@link ApiModel}
     */
    @Override
    public ApiModel doParser(Method method) {
        try {
            getGenerateDto().getNowApiDto().setNowApiConfigFilePath(method.getAnnotation(ApiConfig.class).value());
            File nowApiConfigFile = new File(String.format("%s/%s", getGenerateDto().getNowProjectClassPath(), getGenerateDto().getNowApiDto().getNowApiConfigFilePath()));
            String nowApiConfigFileStr = FileUtils.readFileToString(nowApiConfigFile, "UTF-8");
            return JSON.parseObject(nowApiConfigFileStr, ApiModel.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
