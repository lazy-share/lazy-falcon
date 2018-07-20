package com.lazy.falcon.core.tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/5.
 */
public class AsserUtils {

    /**
     * 是否是控制器
     * @param clazz
     * @return
     */
    public static boolean isController(Class clazz){
        for (Annotation annotation : clazz.getAnnotations()){
            if ("Controller".equals(annotation.annotationType().getSimpleName()) || "RestController".equals(annotation.annotationType().getSimpleName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是RequestMapping
     * @param method
     * @return
     */
    public static boolean isRequestMapping(Method method){
        for (Annotation annotation : method.getAnnotations()){
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
