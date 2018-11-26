package com.lazy.falcon.core.parser;

import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiConfig;
import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.core.loader.GenDocApiClassLoader;
import com.lazy.falcon.core.model.input.ApiModel;
import com.lazy.falcon.core.parser.dto.ApiDto;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import com.lazy.falcon.core.parser.support.AbstractApiParser;
import com.lazy.falcon.core.parser.support.AbstractComponent;

import java.lang.reflect.Method;

/**
 * <p>
 * Api Observer Parser Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class ObserverApiParser extends AbstractApiParser {

    /**
     * 定义两个观察者
     */
    private AbstractApiParser apiConfigParserObserver = new DefaultApiConfigParser();
    private AbstractApiParser apiAnnotationParserObserver = new DefaultApiAnnotationParser();

    @Override
    public AbstractComponent setGenerateDto(ApiGenerateDto generateDto) {
        apiConfigParserObserver.setGenerateDto(generateDto);
        apiAnnotationParserObserver.setGenerateDto(generateDto);
        return super.setGenerateDto(generateDto);
    }

    @Override
    public AbstractComponent setLoader(GenDocApiClassLoader loader) {
        apiConfigParserObserver.setLoader(loader);
        apiAnnotationParserObserver.setLoader(loader);
        return super.setLoader(loader);
    }

    @Override
    public void setApiModule(ApiModule apiModule) {
        apiConfigParserObserver.setApiModule(apiModule);
        apiAnnotationParserObserver.setApiModule(apiModule);
        super.setApiModule(apiModule);
    }

    /**
     * 伪观察者模式解析
     *
     * @param method 方法类
     * @return {@link ApiModel}
     */
    @Override
    public ApiModel doParser(Method method) {
        ApiDto nowApiDto = new ApiDto();
        getGenerateDto().setNowApiDto(nowApiDto);
        if (method.isAnnotationPresent(ApiConfig.class)) {
            apiConfigParserObserver.setMethod(method);
            return apiConfigParserObserver.doParser(method);
        } else if (method.isAnnotationPresent(Api.class)) {
            apiAnnotationParserObserver.setMethod(method);
            return apiAnnotationParserObserver.doParser(method);
        } else {
            return null;
        }
    }
}
