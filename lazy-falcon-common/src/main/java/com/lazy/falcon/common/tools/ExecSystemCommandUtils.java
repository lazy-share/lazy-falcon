package com.lazy.falcon.common.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/7.
 */
public class ExecSystemCommandUtils {

    /**
     * 在当前Java虚拟机创建一个子进程执行指定的可执行程序
     * @param command 命令
     * @return
     * @throws Exception
     */
    public static String exec(String command) throws Exception {
        Runtime rt;
        Process proc = null;
        InputStream stderr = null;
        InputStreamReader is = null;
        BufferedReader br = null;
        try{
            rt = Runtime.getRuntime();
            proc = rt.exec(command);
            stderr = proc.getErrorStream();
            is = new InputStreamReader(stderr, "GBK");
            br = new BufferedReader(is);
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = br.readLine()) != null){
                result.append(line).append("\n");
            }
            return result.toString();
        }finally {
            if (proc != null){
                proc.waitFor();
                proc.destroy();
                if (br != null){
                    br.close();
                    is.close();
                    stderr.close();
                }
            }
        }
    }

    /**
     * 判断是否是Windows系统
     * @return boolean
     */
    public static boolean isWindowsSystem() {
        return System.getProperties().getProperty("os.name").toUpperCase().contains("WINDOWS");
    }

    /**
     * 生成将结果文档复制到SpringBootJar Windows系统CMD命令
     * @param springBootJarPath spirngboot Jar 名称
     * @return 执行命令
     */
    public static String genWindowsSystemCopyResultToSpringBootJarCommand(String springBootJarPath){
        String[] spilitResult = springBootJarPath.replaceAll("\\\\", "/").split("/");
        StringBuffer command = new StringBuffer("cmd /c ");
        String commandStr = "dir";
        if (spilitResult.length > 0){
            String jarName = spilitResult[spilitResult.length - 1];
            String diskName = spilitResult[0];
            String cdPath = springBootJarPath.substring(0, springBootJarPath.indexOf(jarName));

            command.append(diskName).append(" && cd ")
                    .append(cdPath)
                    .append(" && ")
                    .append(" jar uf ")
                    .append(jarName)
                    .append(" BOOT-INF ");

            commandStr = command.toString();
            System.out.println("[INFO] GEN API DOC exec command: " + commandStr);
        }
        return commandStr;
    }

    /**
     * 生成将结果文档复制到SpringBootJar Linux系统Bash命令
     * @param springBootJarPath spirngboot Jar 名称
     * @return 执行命令
     */
    public static String genLinuxSystemCopyResultToSpringBootJarCommand(String springBootJarPath){
        String[] spilitResult = springBootJarPath.replaceAll("\\\\", "/").split("/");
        StringBuffer command;
        String commandStr = "ls";
        if (spilitResult.length > 0){
            String jarName = spilitResult[spilitResult.length - 1];
            String cdPath = springBootJarPath.substring(0, springBootJarPath.indexOf(jarName));
            command = new StringBuffer("");
            command.append("cd ")
                    .append(cdPath)
                    .append(" && ")
                    .append(" jar uf ")
                    .append(jarName)
                    .append(" BOOT-INF ");

            commandStr = command.toString();
            System.out.println("[INFO] GEN API DOC exec command: " + commandStr);
        }
        return commandStr;
    }

    public static void main(String[] args) throws Exception {
        if (isWindowsSystem()){
            exec(genWindowsSystemCopyResultToSpringBootJarCommand("D:\\OpenSource\\IdeaWorspace\\lazy-falcon-parent\\gendoc" +
                    "-maven-plugin-example\\target\\lazy-falcon-example-0.0.1-SNAPSHOT.jar"));
        }else {
            genLinuxSystemCopyResultToSpringBootJarCommand("/usr/local/application/sanitation/admin/build/sanitation/sanitation-admin/target/sanitation-admin.jar");
        }


//        System.out.println(isWindows());
//        try {
//            System.out.println(exec("cmd /c F: && cd F:\\test && jar uf lazy-falcon-example-0.0.1-SNAPSHOT.jar BOOT-INF"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
