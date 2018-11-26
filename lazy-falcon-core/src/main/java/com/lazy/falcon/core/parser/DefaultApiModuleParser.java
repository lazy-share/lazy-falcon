package com.lazy.falcon.core.parser;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.common.tools.ReflectUtils;
import com.lazy.falcon.core.model.input.ApiModel;
import com.lazy.falcon.core.parser.support.AbstractApiModuleParser;
import com.lazy.falcon.core.parser.support.AbstractApiParser;

import java.lang.reflect.Method;

/**
 * <p>
 * Api Module Parser Default Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class DefaultApiModuleParser extends AbstractApiModuleParser {


    public DefaultApiModuleParser(AbstractApiParser apiParser) {
        super(apiParser);
    }

    /**
     * 解析
     *
     * @param clazz 模块
     */
    @Override
    public void doParser(Class clazz) {
        ApiModule apiModule = (ApiModule) clazz.getAnnotation(ApiModule.class);
        apiParser.setApiModule(apiModule);
        Method[] methods = ReflectUtils.getMethods(clazz, 3);
        if (methods == null || methods.length < 1) {
            getLog().warn(String.format("GEN API DOC [%s] not method", clazz.getName()));
            return;
        }
        String apiModuleBaseUri = appendApiUriAndSetReqMethod(clazz.getAnnotations(), null, false);
        for (Method method : methods) {
            setCurrentParserApiKey(String.format("GEN API DOC BY CONFIG : [%s#%s]", clazz.getName(), method.getName()));
            if (method.isAnnotationPresent(Api.class)) {
                ApiModel apiModel = apiParser.doParser(method);
                if (apiModel == null) {
                    getLog().warn("GEN API DOC CONTINUE: " + getCurrentParserApiKey());
                    continue;
                }
                apiModel.setUrl(apiModuleBaseUri + apiModel.getUrl());
                apiModel.setModule(apiModule.value());
                setCurrentParserApiKey(String.format("GEN API DOC BY ANNOTATION : [%s#%s]", clazz.getName(), method.getName()));
                this.buildModuleItem(getGenerateDto(), apiModel);
                this.addSidebarTree(getGenerateDto(), apiModel);
                this.writeSidebarTree(getGenerateDto());
                getLog().info(getCurrentParserApiKey());
            }
        }
    }

}
