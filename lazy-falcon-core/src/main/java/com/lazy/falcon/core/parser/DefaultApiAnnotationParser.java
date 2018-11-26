package com.lazy.falcon.core.parser;

import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.common.annotaion.ApiParamItem;
import com.lazy.falcon.common.annotaion.DefaultLinkCls;
import com.lazy.falcon.common.tools.SetsUtils;
import com.lazy.falcon.common.tools.StringUtils;
import com.lazy.falcon.core.model.input.ApiRequestParamGroupInputModel;
import com.lazy.falcon.core.model.input.ApiRequestParamItemInputModel;
import com.lazy.falcon.core.model.input.ApiResponseParamGroupInputModel;
import com.lazy.falcon.core.parser.support.AbstractApiAnnotationParser;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * <p>
 * Default Api Annotation Parser Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public class DefaultApiAnnotationParser extends AbstractApiAnnotationParser {


    /**
     * 解析请求参数
     *
     * @param method 方法类
     * @return {@link List<ApiRequestParamGroupInputModel>}
     */
    @Override
    public List<ApiRequestParamGroupInputModel> doParserRequestParam(Method method) {
        List<ApiRequestParamGroupInputModel> requestParamGroupInputModelList = SetsUtils.list();
        Parameter[] parameters = method.getParameters();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Api api = method.getAnnotation(Api.class);
        if (parameters != null && parameters.length > 0) {
            ApiParamItem apiParamItem;
            ApiParamGroup apiParamGroup;
            ApiRequestParamGroupInputModel reqParamGroupModel = new ApiRequestParamGroupInputModel();
            reqParamGroupModel.setItems(SetsUtils.list());
            reqParamGroupModel.setTitle(api.reqParamTitle());
            boolean isAddGroup = false;
            Class groupCls;
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                apiParamItem = parameter.getAnnotation(ApiParamItem.class);
                apiParamGroup = parameter.getAnnotation(ApiParamGroup.class);
                if (apiParamItem != null && apiParamGroup != null) {
                    throw new RuntimeException("GEN API DOC ERROR: 不能同时注解ApiParamGroup和ApiParamItem到同一个参数或属性上");
                }
                if (apiParamItem == null && apiParamGroup == null) {
                    continue;
                }
                if (apiParamItem != null) {
                    ApiRequestParamItemInputModel reqParamItemModel = new ApiRequestParamItemInputModel();
                    reqParamItemModel.setDesc(apiParamItem.value());
                    reqParamItemModel.setName(StringUtils.isEmpty(apiParamItem.name()) ? this.getApiItemName(parameter, apiParamItem) : apiParamItem.name());
                    reqParamItemModel.setRequired(apiParamItem.required());
                    reqParamItemModel.setType(StringUtils.isEmpty(apiParamItem.type()) ? parameter.getType().getSimpleName() : apiParamItem.type());

                    reqParamGroupModel.getItems().add(reqParamItemModel);
                    if (!isAddGroup) {
                        requestParamGroupInputModelList.add(reqParamGroupModel);
                        isAddGroup = true;
                    }
                } else {
                    if (apiParamGroup.isParamItem()) {
                        ApiRequestParamItemInputModel reqParamItemModel = new ApiRequestParamItemInputModel();
                        reqParamItemModel.setDesc(apiParamGroup.value());
                        reqParamItemModel.setName(this.getApiItemName(parameter, apiParamGroup));
                        reqParamItemModel.setRequired(apiParamGroup.required());
                        reqParamItemModel.setType(StringUtils.isEmpty(apiParamGroup.type()) ? parameter.getType().getSimpleName() : apiParamGroup.type());

                        reqParamItemModel.setGroupTitle(apiParamGroup.groupTitle());
                        reqParamGroupModel.getItems().add(reqParamItemModel);
                        if (!isAddGroup) {
                            requestParamGroupInputModelList.add(reqParamGroupModel);
                            isAddGroup = true;
                        }
                        groupCls = parameter.getType().getClass();
                        if (apiParamGroup.isUseModuleCls()) {
                            groupCls = getApiModule().moduleCls();
                        }
                        if (apiParamGroup.linkCls() != DefaultLinkCls.class){
                            groupCls = apiParamGroup.linkCls();
                        }
                        recursionParserRequestParam(requestParamGroupInputModelList, groupCls, api.reqParamTitle(), apiParamGroup);
                    } else {
                        groupCls = parameterTypes[i];
                        if (apiParamGroup.isUseModuleCls()) {
                            groupCls = getApiModule().moduleCls();
                        }
                        if (apiParamGroup.linkCls() != DefaultLinkCls.class){
                            groupCls = apiParamGroup.linkCls();
                        }
                        recursionParserRequestParam(requestParamGroupInputModelList, groupCls, api.reqParamTitle(), apiParamGroup);
                    }
                }
            }
        }
        return requestParamGroupInputModelList;
    }


    /**
     * 解析响应参数
     *
     * @param method 方法类
     * @return {@link List<ApiResponseParamGroupInputModel>}
     */
    @Override
    public List<ApiResponseParamGroupInputModel> doParserResponseParam(Method method) {
        List<ApiResponseParamGroupInputModel> responseParamGroupInputModelList = SetsUtils.list();
        Class<?> returnType = method.getReturnType();
        Api api = method.getAnnotation(Api.class);
        recursionParserResponseParam(responseParamGroupInputModelList, returnType, api.resParamTitle());
        return responseParamGroupInputModelList;
    }
}
