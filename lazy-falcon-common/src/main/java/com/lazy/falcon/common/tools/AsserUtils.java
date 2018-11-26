package com.lazy.falcon.common.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/5.
 */
public class AsserUtils {

    /**
     * 是否需要生成Api标示
     *
     * @param clazz class
     * @return bool
     */
    public static boolean isApiModule(Class clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if ("ApiModule".equals(annotation.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是控制器
     *
     * @param clazz
     * @return
     */
    public static boolean isController(Class clazz) {
        for (Annotation annotation : clazz.getAnnotations()) {
            if ("Controller".equals(annotation.annotationType().getSimpleName()) || "RestController".equals(annotation.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是RequestBody
     *
     * @param parameters 参数
     * @return {@link Boolean}
     */
    public static boolean isRequestBody(Parameter[] parameters) {
        for (Parameter parameter : parameters) {
            Annotation[] annotations = parameter.getAnnotations();
            for (Annotation annotation : annotations) {
                if ("RequestBody".equals(annotation.annotationType().getSimpleName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否是RequestParam
     *
     * @param annotations 参数
     * @return {@link Boolean}
     */
    public static boolean isRequestParam(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if ("RequestParam".equals(annotation.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是RequestMapping
     *
     * @param method
     * @return
     */
    public static boolean isRequestMapping(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if ("RequestMapping".equals(annotation.annotationType().getSimpleName())
                    || "GetMapping".equals(annotation.annotationType().getSimpleName())
                    || "PostMapping".equals(annotation.annotationType().getSimpleName())
                    || "PutMapping".equals(annotation.annotationType().getSimpleName())
                    || "DeleteMapping".equals(annotation.annotationType().getSimpleName())) {
                return true;
            }
        }
        return false;
    }
}
