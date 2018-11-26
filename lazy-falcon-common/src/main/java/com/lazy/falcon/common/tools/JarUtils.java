package com.lazy.falcon.common.tools;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/5.
 */
public class JarUtils {

    /**
     * 复制SpringBoot Jar包的Lib文件到outputPath下
     *
     * @param springBootJar SpringBoot Jar
     * @param libPath    解压输出路径
     * @return {@link URL,List}
     * @throws IOException IO异常
     */
    public static List<URL> copySpringBootJarLibsTo(String springBootJar, String libPath) {
        if (!libPath.endsWith(File.separator)) {
            libPath = libPath + File.separator;
        }
        File file = new File(libPath);
        if (file.exists()) {
            file.delete();
        }
        file.mkdirs();
        InputStream in = null;
        OutputStream out = null;
        FileOutputStream fout = null;
        byte[] buffer;
        JarFile jf;
        JarEntry je;
        File f;
        String springBootLibDirKeyword = "BOOT-INF/lib/";
        try {
            jf = new JarFile(springBootJar);
            List<URL> libs = SetsUtils.list();

            for (Enumeration e = jf.entries(); e.hasMoreElements(); ) {
                je = (JarEntry) e.nextElement();
                if (!je.getName().startsWith(springBootLibDirKeyword)
                        || !je.getName().endsWith(".jar")
                        || springBootLibDirKeyword.equals(je.getName())) {
                    continue;
                }
                String outFileName = libPath + je.getName().replaceFirst(springBootLibDirKeyword, "");
                f = new File(outFileName);
                try {
                    in = jf.getInputStream(je);
                    fout = new FileOutputStream(f);
                    out = new BufferedOutputStream(fout);
                    buffer = new byte[2048];
                    int nBytes = 0;
                    while ((nBytes = in.read(buffer)) > 0) {
                        out.write(buffer, 0, nBytes);
                    }
                    libs.add((new URL("file:" + new File(outFileName).getCanonicalPath())));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                } finally {
                    if (null != out) {
                        out.flush();
                        out.close();
                        out = null;
                    }
                    if (null != fout){
                        fout.flush();
                        fout.close();
                        fout = null;
                    }
                    if (null != in){
                        in.close();
                        in = null;
                    }
                }
            }
            return libs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }finally {
            jf = null;
            je = null;
            //手动触发垃圾回收
            System.gc();
        }
    }

}
