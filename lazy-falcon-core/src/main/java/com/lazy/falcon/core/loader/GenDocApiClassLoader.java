package com.lazy.falcon.core.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * <p>
 *     自定义ClassLoader
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/9.
 */
public class GenDocApiClassLoader extends URLClassLoader {

    public GenDocApiClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
}
