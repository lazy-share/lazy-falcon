package com.lazy.falcon.common.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author laizhiyuan
 * @date 2017/12/29.
 * <p>反射工具类</p>
 */
@SuppressWarnings("all")
public abstract class ReflectUtils {


    /**
     * 反射获取getger
     * 支持多层级级继承
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Method[] getMethods(Class clazz, int superNum) {
        try {
            Method[] methods = clazz.getDeclaredMethods();
            List<Method> methodList = SetsUtils.list();
            Class superCls = clazz;
            for (int i = 0; i < superNum; i++) {
                superCls = superCls.getSuperclass();
                if (superCls == Object.class) {
                    break;
                }
                for (Method method : superCls.getDeclaredMethods()) {
                    methodList.add(method);
                }
            }
            if (methods != null) {
                for (Method method : methods) {
                    methodList.add(method);
                }
            }
            return methodList.toArray(new Method[methodList.size()]);
        }catch (Exception ex){
            return new Method[]{};
        }
    }

    /**
     * 反射获取getger
     * 支持多层级级继承
     *
     * @param fieldName
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class clazz, int superNum) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            List<Field> fieldList = SetsUtils.list();
            Class superCls = clazz;
            for (int i = 0; i < superNum; i++) {
                superCls = superCls.getSuperclass();
                if (superCls == Object.class) {
                    break;
                }
                for (Field field : superCls.getDeclaredFields()) {
                    fieldList.add(field);
                }
            }
            if (fields != null) {
                for (Field field : fields) {
                    fieldList.add(field);
                }
            }
            return fieldList.toArray(new Field[fieldList.size()]);
        }catch (Exception ex){
            return new Field[]{};
        }
    }
}
