package com.lazy.falcon.core.tools;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author laizhiyuan
 * @data 2017/12/26.
 */
public class SetsUtils {

    private SetsUtils(){}

    public static <K,V>HashMap<K,V> map(){
        return new HashMap<K,V>();
    }

    public static <K,V>HashMap<K,V> map(int size){
        return new HashMap<K,V>(size);
    }

    public static <T>ArrayList<T> list(){
        return new ArrayList<T>();
    }

    public static <T>ArrayList<T> list(int size){
        return new ArrayList<T>(size);
    }

    /**
     * 浅复制，如果嵌套复杂对象，需要实现深度复制的话谨慎使用
     * @param sourceList 源列表
     * @param <T> 泛型
     * @return 目标列表
     */
    public static <T>ArrayList<T> shallowList(List<T> sourceList){
        return new ArrayList<T>(sourceList);
    }

    public static <T>LinkedList<T> linkedList(){
        return new LinkedList<T>();
    }

    public static <T>HashSet<T> set(){
        return new HashSet<T>();
    }

    public static <T>HashSet<T> set(int size){
        return new HashSet<T>(size);
    }

    public static <T>LinkedHashSet<T> linkedSet(){
        return new LinkedHashSet<T>();
    }

    public static <K,V>LinkedHashMap<K,V> linkedMap(){
        return new LinkedHashMap<K,V>();
    }

    public static <K,V>ConcurrentHashMap<K,V> concurrentHashMap(){
        return new ConcurrentHashMap<K,V>();
    }
}
