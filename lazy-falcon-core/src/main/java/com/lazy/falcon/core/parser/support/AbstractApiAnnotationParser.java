package com.lazy.falcon.core.parser.support;

import com.lazy.falcon.common.annotaion.Api;
import com.lazy.falcon.common.annotaion.ApiParamGroup;
import com.lazy.falcon.common.annotaion.ApiParamItem;
import com.lazy.falcon.common.annotaion.DefaultLinkCls;
import com.lazy.falcon.common.tools.*;
import com.lazy.falcon.core.model.input.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 抽象 API 注解 解析
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public abstract class AbstractApiAnnotationParser extends AbstractApiParser {

    /**
     * 解析
     *
     * @param method 方法类
     * @return {@link ApiModel}
     */
    @Override
    public ApiModel doParser(Method method) {
        ApiModel apiModel = new ApiModel();
        Api api = method.getAnnotation(Api.class);
        apiModel.setModuleItem(api.value());
        this.appendApiUriAndSetReqMethod(method.getAnnotations(), apiModel, true);
        apiModel.setAuthor(api.author());
        apiModel.setCreateDate(StringUtils.isEmpty(api.createDate()) ? DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD) : api.createDate());
        apiModel.setLstUpdDate(StringUtils.isEmpty(api.lastUpdateTime()) ? DateUtils.getCurrentDateStr(DateUtils.YYYY_MM_DD) : api.lastUpdateTime());
        apiModel.setDesc(api.desc());
        apiModel.setRequestBody(AsserUtils.isRequestBody(method.getParameters()));
        apiModel.setReqParams(this.doParserRequestParam(method));
        apiModel.setResParams(this.doParserResponseParam(method));

        return apiModel;
    }

    /**
     * 获取API item 名称
     *
     * @param parameter    参数
     * @param apiParamItem 注解
     * @return {@link String}
     */
    protected String getApiItemName(Parameter parameter, ApiParamItem apiParamItem) {
        if (StringUtils.isNotEmpty(apiParamItem.name())) {
            return apiParamItem.name();
        }
        Annotation[] annotations = parameter.getAnnotations();
        if (AsserUtils.isRequestParam(annotations)) {
            for (Annotation annotation : annotations) {
                if ("RequestParam".equals(annotation.annotationType().getSimpleName())) {
                    return getAnnoMethodVal(annotation, "value");
                }
            }
        }
        return parameter.getName();
    }

    /**
     * 获取API item 名称
     *
     * @param field        参数
     * @param apiParamItem 注解
     * @return {@link String}
     */
    private String getApiItemName(Field field, ApiParamItem apiParamItem) {
        if (StringUtils.isNotEmpty(apiParamItem.name())) {
            return apiParamItem.name();
        }
        Annotation[] annotations = field.getAnnotations();
        if (AsserUtils.isRequestParam(annotations)) {
            for (Annotation annotation : annotations) {
                if ("RequestParam".equals(annotation.annotationType().getSimpleName())) {
                    return getAnnoMethodVal(annotation, "value");
                }
            }
        }
        return field.getName();
    }

    /**
     * 获取API item 名称
     *
     * @param parameter     参数
     * @param apiParamGroup 注解
     * @return {@link String}
     */
    protected String getApiItemName(Parameter parameter, ApiParamGroup apiParamGroup) {
        if (StringUtils.isNotEmpty(apiParamGroup.name())) {
            return apiParamGroup.name();
        }
        Annotation[] annotations = parameter.getAnnotations();
        if (AsserUtils.isRequestParam(annotations)) {
            for (Annotation annotation : annotations) {
                if ("RequestParam".equals(annotation.annotationType().getSimpleName())) {
                    return getAnnoMethodVal(annotation, "value");
                }
            }
        }
        return parameter.getName();
    }

    /**
     * 获取API item 名称
     *
     * @param field         参数
     * @param apiParamGroup 注解
     * @return {@link String}
     */
    private String getApiItemName(Field field, ApiParamGroup apiParamGroup) {
        if (StringUtils.isNotEmpty(apiParamGroup.name())) {
            return apiParamGroup.name();
        }
        Annotation[] annotations = field.getAnnotations();
        if (AsserUtils.isRequestParam(annotations)) {
            for (Annotation annotation : annotations) {
                if ("RequestParam".equals(annotation.annotationType().getSimpleName())) {
                    return getAnnoMethodVal(annotation, "value");
                }
            }
        }
        return field.getName();
    }

    /**
     * 递归解析响应参数
     *
     * @param responseParamGroupInputModels 响应参数列表
     * @param clazz                         Class类
     * @param parentGroupTitle              父级标题
     */
    protected void recursionParserResponseParam(List<ApiResponseParamGroupInputModel> responseParamGroupInputModels, Class clazz, String parentGroupTitle) {
        if (clazz == null) {
            return;
        }
        Field[] fields = ReflectUtils.getFields(clazz, 3);
        if (fields != null && fields.length > 0) {
            ApiParamItem apiParamItem;
            ApiParamGroup apiParamGroup;
            ApiResponseParamGroupInputModel responseParamGroupInputModel = new ApiResponseParamGroupInputModel();
            responseParamGroupInputModel.setItems(SetsUtils.list());
            responseParamGroupInputModel.setTitle(parentGroupTitle);
            responseParamGroupInputModels.add(responseParamGroupInputModel);
            Class fieldCls;
            for (Field field : fields) {
                apiParamItem = field.getAnnotation(ApiParamItem.class);
                apiParamGroup = field.getAnnotation(ApiParamGroup.class);
                if (apiParamItem != null && apiParamGroup != null) {
                    throw new RuntimeException("GEN API DOC ERROR: 不能同时注解ApiParamGroup和ApiParamItem到同一个参数或属性上");
                }
                if (apiParamItem == null && apiParamGroup == null) {
                    continue;
                }
                if (apiParamItem != null) {
                    ApiResponseParamItemInputModel responseParamItemInputModel = new ApiResponseParamItemInputModel();
                    responseParamItemInputModel.setDesc(apiParamItem.value());
                    responseParamItemInputModel.setRemark(apiParamItem.remark());
                    responseParamItemInputModel.setName(this.getApiItemName(field, apiParamItem));
                    responseParamItemInputModel.setType(StringUtils.isEmpty(apiParamItem.type()) ? field.getType().getSimpleName() : apiParamItem.type());

                    responseParamGroupInputModel.getItems().add(responseParamItemInputModel);
                } else {
                    String fieldTypeStr = StringUtils.isEmpty(apiParamGroup.type()) ? field.getType().getSimpleName() : apiParamGroup.type();
                    ApiResponseParamItemInputModel responseParamItemInputModel = new ApiResponseParamItemInputModel();
                    responseParamItemInputModel.setDesc(apiParamGroup.value());
                    responseParamItemInputModel.setRemark(apiParamGroup.remark());
                    responseParamItemInputModel.setName(this.getApiItemName(field, apiParamGroup));
                    responseParamItemInputModel.setType(fieldTypeStr);

                    responseParamItemInputModel.setGroupTitle(apiParamGroup.groupTitle());
                    responseParamGroupInputModel.getItems().add(responseParamItemInputModel);
                    if (apiParamGroup.isUseModuleCls()) {
                        fieldCls = getApiModule().moduleCls();
                    } else {
                        fieldCls = apiParamGroup.linkCls() != DefaultLinkCls.class ? apiParamGroup.linkCls() : field.getType();
                    }
                    recursionParserResponseParam(responseParamGroupInputModels, fieldCls, apiParamGroup.groupTitle());
                }
            }
        }
    }

    /**
     * 是否实现集合接口
     *
     * @param clazz Class
     * @return {@link Boolean}
     * @throws ClassNotFoundException
     */
    private boolean isCollectionImpl(Class clazz) {
        Class[] interfaceClses = clazz.getInterfaces();
        for (Class interfaceCls : interfaceClses) {
            if (interfaceCls == Collection.class) {
                return true;
            }
        }
        return false;
    }


    /**
     * 递归解析请求参数
     *
     * @param requestParamGroupInputModels 请求参数列表
     * @param clazz                        Class类
     * @param parentGroupTitle             父级标题
     */
    protected void recursionParserRequestParam(List<ApiRequestParamGroupInputModel> requestParamGroupInputModels, Class clazz, String parentGroupTitle, ApiParamGroup parentGroup) {
        if (clazz == null) {
            return;
        }
        Field[] fields = ReflectUtils.getFields(clazz, 3);
        if (fields != null && fields.length > 0) {
            ApiParamItem apiParamItem;
            ApiParamGroup apiParamGroup;
            ApiRequestParamGroupInputModel reqParamGroupModel = new ApiRequestParamGroupInputModel();
            reqParamGroupModel.setItems(SetsUtils.list());
            reqParamGroupModel.setTitle(parentGroupTitle);
            requestParamGroupInputModels.add(reqParamGroupModel);
            Class groupCls;
            for (Field field : fields) {
                apiParamItem = field.getAnnotation(ApiParamItem.class);
                apiParamGroup = field.getAnnotation(ApiParamGroup.class);
                if (apiParamItem != null && apiParamGroup != null) {
                    throw new RuntimeException("GEN API DOC ERROR: 不能同时注解ApiParamGroup和ApiParamItem到同一个参数或属性上");
                }
                if (apiParamItem == null && apiParamGroup == null) {
                    continue;
                }
                String name = this.getApiItemName(field, parentGroup);
                if (!checkInclude(name, parentGroup)) {
                    continue;
                }
                if (apiParamItem != null) {
                    ApiRequestParamItemInputModel reqParamItemModel = new ApiRequestParamItemInputModel();
                    reqParamItemModel.setDesc(apiParamItem.value());
                    reqParamItemModel.setRemark(apiParamItem.remark());
                    reqParamItemModel.setName(this.getApiItemName(field, apiParamItem));
                    reqParamItemModel.setRequired(apiParamItem.required());
                    reqParamItemModel.setType(StringUtils.isEmpty(apiParamItem.type()) ? field.getType().getSimpleName() : apiParamItem.type());

                    reqParamGroupModel.getItems().add(reqParamItemModel);
                } else {
                    ApiRequestParamItemInputModel reqParamItemModel = new ApiRequestParamItemInputModel();
                    reqParamItemModel.setDesc(apiParamGroup.value());
                    reqParamItemModel.setRemark(apiParamGroup.remark());
                    reqParamItemModel.setName(this.getApiItemName(field, apiParamGroup));
                    reqParamItemModel.setRequired(apiParamGroup.required());
                    reqParamItemModel.setType(StringUtils.isEmpty(apiParamGroup.type()) ? field.getType().getSimpleName() : apiParamGroup.type());

                    reqParamItemModel.setGroupTitle(apiParamGroup.groupTitle());
                    reqParamGroupModel.getItems().add(reqParamItemModel);

                    if (apiParamGroup.linkCls() != DefaultLinkCls.class) {
                        groupCls = apiParamGroup.linkCls();
                    } else if (apiParamGroup.isUseModuleCls()) {
                        groupCls = getApiModule().moduleCls();
                    } else {
                        groupCls = field.getType();
                    }
                    recursionParserRequestParam(requestParamGroupInputModels, groupCls, apiParamGroup.groupTitle(), apiParamGroup);
                }
            }
        }
    }

    /**
     * 检查是否包含
     *
     * @param itemName      项名称
     * @param apiParamGroup API参数组
     * @return {@link Boolean}
     */
    private boolean checkInclude(String itemName, ApiParamGroup apiParamGroup) {
        if (apiParamGroup.includeItems().length > 0 && apiParamGroup.excludeItems().length > 0) {
            throw new RuntimeException("不能同时定义includeItem和excludeItem");
        }
        if (apiParamGroup.includeItems().length > 0) {
            for (String include : apiParamGroup.includeItems()) {
                if (include.equals(itemName)) {
                    return true;
                }
            }
            return false;
        }
        if (apiParamGroup.excludeItems().length > 0) {
            for (String exclude : apiParamGroup.excludeItems()) {
                if (exclude.equals(itemName)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }

    /**
     * 解析请求参数
     *
     * @param method 方法类
     * @return {@link List<ApiRequestParamGroupInputModel>}
     */
    public abstract List<ApiRequestParamGroupInputModel> doParserRequestParam(Method method);

    /**
     * 解析响应参数
     *
     * @param method 方法类
     * @return {@link List<ApiResponseParamGroupInputModel>}
     */
    public abstract List<ApiResponseParamGroupInputModel> doParserResponseParam(Method method);
}
