package com.lazy.falcon.core.parser.support;

import com.lazy.falcon.common.tools.ReflectUtils;
import com.lazy.falcon.common.tools.StringUtils;
import com.lazy.falcon.core.loader.GenDocApiClassLoader;
import com.lazy.falcon.core.model.input.ApiModel;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import org.apache.maven.plugin.logging.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <p>
 * Abstract Component
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/27.
 */
public abstract class AbstractComponent {

    /**
     * logger
     */
    private Log log;

    /**
     * ApiGenerateDto
     */
    private ApiGenerateDto generateDto;

    private String currentParserApiKey;

    /**
     * class loader
     */
    private GenDocApiClassLoader loader;

    public String getCurrentParserApiKey() {
        return currentParserApiKey;
    }

    public AbstractComponent setCurrentParserApiKey(String currentParserApiKey) {
        this.currentParserApiKey = currentParserApiKey;
        return this;
    }

    /**
     * 解析泛型字符串
     *
     * @param genericStr 泛型字符串
     * @return {@link String}
     */
    protected String parserGenericStr(String genericStr) {
        String[] genericStrArr = genericStr.split("<");
        if (genericStrArr.length > 1) {
            String parserStr = genericStrArr[1];
            parserStr = parserStr.replaceAll(">", "");
            return parserStr;
        }
        return null;
    }

    /**
     * 截掉上一个泛型
     *
     * @param genericStr    整串泛型字符串
     * @param preGenericStr 上一个泛型字符串
     * @return {@link String}
     */
    protected String splitPreGenericStr(String genericStr, String preGenericStr) {
        String[] parserStrArr = genericStr.split(preGenericStr);
        if (parserStrArr.length > 1) {
            String parserStr = parserStrArr[1];
            if (parserStr.startsWith("<")) {
                parserStr = parserStr.substring(1);
            }
            parserStr = parserStr.replaceAll(">", "");
            return parserStr;
        }
        return null;
    }


    /**
     * 追加path  设置request method
     *
     * @param annotations 注解数组
     * @param apiModel    实体
     */
    protected String appendApiUriAndSetReqMethod(Annotation[] annotations, ApiModel apiModel, boolean isActionPath) {
        String path = "";
        String method = "";
        if (!isActionPath) {
            for (Annotation annotation : annotations) {
                if (!isActionPath) {
                    if ("RequestMapping".equals(annotation.annotationType().getSimpleName())) {
                        path = getAnnoMethodVal(annotation, "value");
                        return path == null ? "" : path;
                    }
                }
            }
            return path;
        } else {
            for (Annotation annotation : annotations) {
                if ("RequestMapping".equals(annotation.annotationType().getSimpleName())) {
                    path = getAnnoMethodVal(annotation, "value");
                    method = getAnnoMethodVal(annotation, "method");
                } else if ("GetMapping".equals(annotation.annotationType().getSimpleName())) {
                    path = getAnnoMethodVal(annotation, "value");
                    method = "get";
                } else if ("PostMapping".equals(annotation.annotationType().getSimpleName())) {
                    path = getAnnoMethodVal(annotation, "value");
                    method = "post";
                } else if ("PutMapping".equals(annotation.annotationType().getSimpleName())) {
                    path = getAnnoMethodVal(annotation, "value");
                    method = "put";
                } else if ("DeleteMapping".equals(annotation.annotationType().getSimpleName())) {
                    path = getAnnoMethodVal(annotation, "value");
                    method = "delete";
                }
            }
            apiModel.setMethod(method);
        }

        if (StringUtils.isNotEmpty(path)) {
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (isActionPath) {
                if (StringUtils.isNotEmpty(apiModel.getUrl())) {
                    apiModel.setUrl(apiModel.getUrl() + path);
                } else {
                    apiModel.setUrl(path);
                }
            }
        }
        return path;
    }

    /**
     * 获取注解指定的方法
     *
     * @param annotation 注解数组
     * @param methodName 方法名
     * @return {@link String}
     */
    protected String getAnnoMethodVal(Annotation annotation, String methodName) {
        Class clazz = annotation.getClass();
        Method[] methods1 = clazz.getDeclaredMethods();
        for (Method method1 : methods1) {
            if (method1.getName().equals(methodName)) {
                try {
                    Object obj = method1.invoke(annotation);
                    if (obj == null) {
                        return "";
                    }
                    if (obj instanceof String[]) {
                        String[] pathArr = (String[]) obj;
                        return pathArr[0];
                    } else if (obj instanceof String) {
                        return obj.toString();
                    } else if (obj instanceof Object[]) {
                        Object[] objs = (Object[]) obj;
                        if ("RequestMethod[]".equals(objs.getClass().getSimpleName())) {
                            if (objs.length < 1) {
                                return "get";
                            } else {
                                Object tempObj = objs[0];
                                if ("RequestMethod".equals(tempObj.getClass().getSimpleName())) {
                                    Method[] methods = ReflectUtils.getMethods(tempObj.getClass(), 1);
                                    for (Method method : methods) {
                                        if ("name".equals(method.getName())) {
                                            return (String) method.invoke(tempObj);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        throw new RuntimeException("不支持的转换类型:" + obj.getClass().getName());
                    }
                } catch (Exception e) {
                    throw new RuntimeException("GEN API DOC ERROR:", e);
                }
            }
        }
        return null;
    }

    public GenDocApiClassLoader getLoader() {
        return loader;
    }

    public AbstractComponent setLoader(GenDocApiClassLoader loader) {
        this.loader = loader;
        return this;
    }

    public Log getLog() {
        return log;
    }

    public AbstractComponent setLog(Log log) {
        this.log = log;
        return this;
    }

    public ApiGenerateDto getGenerateDto() {
        return generateDto;
    }

    public AbstractComponent setGenerateDto(ApiGenerateDto generateDto) {
        this.generateDto = generateDto;
        return this;
    }
}
