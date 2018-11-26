package com.lazy.falcon.core.parser.support;

import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.core.model.input.ApiModel;

import java.lang.reflect.Method;

/**
 * <p>
 * Api Parser Abstract
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public abstract class AbstractApiParser extends AbstractComponent {

    /**
     * 解析当前API对应的method
     */
    protected Method method;

    /**
     * 对应模块主键
     */
    protected ApiModule apiModule;


    /**
     * 解析
     *
     * @param method 方法类
     * @return {@link ApiModel}
     */
    public abstract ApiModel doParser(Method method);

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ApiModule getApiModule() {
        return apiModule;
    }

    public void setApiModule(ApiModule apiModule) {
        this.apiModule = apiModule;
    }
}
