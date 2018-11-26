package com.lazy.falcon.core.mojo;

import com.lazy.falcon.common.tools.AsserUtils;
import com.lazy.falcon.common.tools.SetsUtils;
import com.lazy.falcon.common.tools.StringUtils;
import com.lazy.falcon.core.enums.ProjectStructEnum;
import com.lazy.falcon.core.loader.GenDocApiClassLoader;
import com.lazy.falcon.core.parser.ParserBootstrap;
import com.lazy.falcon.core.parser.dto.ApiGenerateDto;
import com.lazy.falcon.core.type.ProjectStruct;
import com.lazy.falcon.core.type.dto.BasicProjectStructDto;
import com.lazy.falcon.core.type.dto.SpringBootProjectStructDto;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * auto gen api doc plugin
 *
 * @author laizhiyuan
 * @phase compile
 * @goal genApiDoc
 * @date 2018/5/27.
 */
@SuppressWarnings({"all", "unchecked"})
public class ApiGenerateMojo extends AbstractMojo {

    /*****************************************  plugin config param  start *****************************************/
    /**
     * config your localUri
     *
     * @required
     * @parameter property="localUri"
     */
    private String localUri;
    /**
     * config your localUri
     *
     * @required
     * @parameter property="testUri"
     */
    private String testUri;
    /**
     * config your need gen api doc package
     *
     * @required
     * @parameter property="scanPackage"
     */
    private String scanPackage;
    /**
     * config your output dir
     *
     * @parameter property="outputDir"
     */
    private String outputDir;
    /**
     * config your project base dir
     *
     * @required
     * @parameter property="projectBaseDir"
     */
    private String projectBaseDir;
    /**
     * setter gen api doc title
     *
     * @required
     * @parameter property="outputDocTitle"
     */
    private String outputDocTitle;
    /**
     * config your spring boot jar value
     *
     * @required
     * @parameter property="springBootJarName"
     */
    private String springBootJarName;
    /**
     * springboot or springmvc
     * <p>
     * {@link ProjectStructEnum}
     *
     * @parameter property="ProjectStruct"
     */
    private String ProjectStruct;
    /**
     * config deploy env
     *
     * @parameter property="deployEnv"
     */
    private String deployEnv;
    /*****************************************  plugin config param  end *****************************************/


    /**
     * workRootDir
     */
    private String workRootDir;
    /**
     * workStaticDir
     */
    private String workStaticDir;
    /**
     * workClassesDir
     */
    private String workClassesDir;
    /**
     * workApiDocDir
     */
    private String workApiDocDir;
    /**
     * workLibDir
     */
    private String workLibDir;
    /**
     * isUseDefaultOutputDir
     */
    private boolean isUseDefaultOutputDir;
    /**
     * GenDocApi ClassLoader
     */
    private GenDocApiClassLoader loader;
    /**
     * now project classpath
     */
    private String nowProjectClassPath;
    /**
     * controllerClasses
     */
    private List<Class<?>> controllerClasses;
    /**
     * project type impl
     */
    private ProjectStruct projectStruct;
    /**
     * ProjectStructDto
     */
    private BasicProjectStructDto ProjectStructDto;

    /**
     * Plugin Execute Method
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!isProdEnv()) {
            try {
                this.printBasicInfoLog();
                this.initDefaultPath();
                this.printPropertyInfoLog();
                this.buildRuntimeEnv();
                this.doParser();
            } catch (Exception e) {
                this.getLog().error("GEN API DOC ERROR：", e);
            } finally {
                try {
                    this.cleanAndCopyResource();
                } catch (Exception e) {
                    this.getLog().error("GEN API DOC ERROR:", e);
                }
            }
        }
    }

    /**
     * 是否是生产环境
     *
     * @return {@link Boolean}
     */
    private boolean isProdEnv() {
        if (null != this.deployEnv && this.deployEnv.startsWith("prod")) {
            this.getLog().info("GEN API DOC current env is production, will cancle gen");
            return true;
        }
        return false;
    }

    /**
     * 清理和复制资源
     *
     * @throws Exception
     */
    private void cleanAndCopyResource() throws Exception {
        if (loader != null) {
            loader.close();
        }
        //删除lib目录
        if (FileUtils.fileExists(this.workLibDir)) {
            FileUtils.deleteDirectory(this.workLibDir);
        }
        if (!controllerClasses.isEmpty()) {
            File outputFile = null;
            File sourceDocDirFile = null;
            //没有自定义输出目录的话，按照默认嵌入到项目中
            if (!this.isUseDefaultOutputDir) {
                //复制结果到归档文件
                this.projectStruct.copyResultToArtifacts(this.ProjectStructDto);
                //复制一份到ClassPath下
                sourceDocDirFile = new File(this.workClassesDir);
                outputFile = new File(this.nowProjectClassPath);
            } else {
                //如果有自定义则实例化输出目录
                sourceDocDirFile = new File(this.workStaticDir);
                outputFile = new File(this.outputDir);
            }
            FileUtils.copyDirectoryStructure(sourceDocDirFile, outputFile);
        }
        if (FileUtils.fileExists(this.workRootDir)) {
            FileUtils.deleteDirectory(this.workRootDir);
        }
        this.getLog().info("------------------------------------------------------------------------");
        this.getLog().info("GEN API DOC SUCCESS");
        this.getLog().info("------------------------------------------------------------------------");
    }

    /**
     * 解析
     *
     * @throws Exception
     */
    private void doParser() throws Exception {
        if (controllerClasses.isEmpty()) {
            this.getLog().info("GEN API DOC 加载Class结果为空,没有任何需要处理的类");
            return;
        }
        new ParserBootstrap().setLog(getLog()).setGenerateDto(buildGenDocDto()).setLoader(loader).run();
    }

    /**
     * 构建运行时环境
     *
     * @throws Exception
     */
    private void buildRuntimeEnv() throws Exception {
        String classPath = (new URL("file", null, new File(this.nowProjectClassPath).getCanonicalPath() + File.separator)).toString();
        URLStreamHandler sh = null;
        List<URL> libs = new ArrayList<>();
        libs.add(new URL(null, classPath, sh));
        //构建一个运行时环境
        this.getLog().info("GEN API DOC 开始加载所有lib包");
        libs.addAll(this.projectStruct.loadLibs(this.createProjectStructDto(classPath)));
        loader = new GenDocApiClassLoader(libs.toArray(new URL[libs.size()]), Thread.currentThread().getContextClassLoader());
        this.getLog().info("GEN API DOC 加载lib包完毕!");
        this.getLog().info("GEN API DOC 开始加载所有class文件");
        File classDir = new File(classPath.replace("file:", ""));
        this.doScanClass(classDir);
        this.getLog().info("GEN API DOC 加载class文件完毕");
    }

    /**
     * 创建项目类型Dto
     *
     * @return
     */
    private BasicProjectStructDto createProjectStructDto(String classPath) throws MalformedURLException {
        if (ProjectStructEnum.SPRING_BOOT.getValue().equals(this.ProjectStruct)) {
            SpringBootProjectStructDto dto = new SpringBootProjectStructDto();
            dto.setWorkLibDir(this.workLibDir);
            dto.setSpringBootJarFullPath((new URL("file", null, this.springBootJarName)).toString());
            dto.setSpringBootJarName(this.springBootJarName);
            dto.setLog(this.getLog());
            this.ProjectStructDto = dto;
            return dto;
        } else if (ProjectStructEnum.SPRING_MVS.getValue().equals(this.ProjectStruct)) {
            //TODO impl spring mvc project type dto create
            return null;
        } else {
            throw new RuntimeException("ProjectStruct not found enum");
        }
    }

    /**
     * 打印属性信息日志
     */
    private void printPropertyInfoLog() {
        this.getLog().info(String.format("GEN API DOC PARAMETER:${deployEnv}: [%s]", this.deployEnv));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${outputDir}: [%s]", this.outputDir));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${localUri}: [%s]", this.localUri));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${testUri}: [%s]", this.testUri));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${scanPackage}: [%s]", this.scanPackage));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${classPath}: [%s]", this.nowProjectClassPath));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${outputDocTitle}: [%s]", this.outputDocTitle));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${ProjectStruct}: [%s]", this.ProjectStruct));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${projectBaseDir}: [%s]", this.projectBaseDir));
        this.getLog().info(String.format("GEN API DOC PARAMETER:${springBootJarName}: [%s]", this.springBootJarName));
    }

    /**
     * 打印基础信息日志
     */
    private void printBasicInfoLog() {
        this.getLog().info("GEN API DOC [ 作者：laizhiyuan ] ");
        this.getLog().info("GEN API DOC [ 编写日期：2018-05-28 ] ");
        this.getLog().info("GEN API DOC [ 欢迎使用lazyfalcon:代号:猎鹰 ] ");
    }

    /**
     * 初始化默认 Path
     *
     * @return
     */
    private void initDefaultPath() throws Exception {
        //初始化基础目录
        if (!this.projectBaseDir.endsWith(File.separator)) {
            this.projectBaseDir = this.projectBaseDir + File.separator;
        }
        //是否使用默认的输出目录
        this.isUseDefaultOutputDir = StringUtils.isNotEmpty(this.outputDir);
        //初始化默认的输出目录
        if (StringUtils.isEmpty(this.outputDir)) {
            this.getLog().warn("GEN API DOC configure outputDir is empty, default output to /target/classes ");
            this.outputDir = this.projectBaseDir + "target/classes";
        }
        //初始化默认的输出目录
        if (StringUtils.isEmpty(this.ProjectStruct)) {
            this.getLog().warn("GEN API DOC configure ProjectStruct is empty, default springBoot ");
            this.ProjectStruct = "SpringBoot";
        }
        //初始化项目类型实现类
        Class<?> ProjectStructClass = Class.forName(String.format("com.lazy.falcon.core.type.%s%s", this.ProjectStruct, "ProjectStruct"));
        this.projectStruct = (ProjectStruct) ProjectStructClass.newInstance();
        //初始化其它变量
        this.controllerClasses = SetsUtils.list();
        this.springBootJarName = this.projectBaseDir + "target/" + this.springBootJarName + ".jar";
        this.nowProjectClassPath = this.projectBaseDir + "target/classes";
        this.workRootDir = this.projectBaseDir + "target/BOOT-INF";
        this.workLibDir = this.workRootDir + "/lib";
        this.workClassesDir = this.workRootDir + "/classes";
        this.workStaticDir = this.workRootDir + "/classes/static";
        this.workApiDocDir = this.workStaticDir + "/api_doc";
    }

    /**
     * 创建Doc DTO 对象
     *
     * @return
     */
    private ApiGenerateDto buildGenDocDto() {
        ApiGenerateDto docDto = new ApiGenerateDto();
        docDto.setClassLoader(loader);
        docDto.setnowProjectClassPath(nowProjectClassPath);
        docDto.setDomain("test".equalsIgnoreCase(this.deployEnv) ? this.testUri : this.localUri);
        docDto.setLog(this.getLog());
        docDto.setNowProjectDocTitle(this.outputDocTitle);
        docDto.setNowApiModuleClasses(controllerClasses);
        docDto.setWorkRootDirStr(this.workRootDir);
        docDto.setWorkApiDocDirStr(this.workApiDocDir);
        docDto.setWorkStaticDir(this.workStaticDir);
        return docDto;
    }

    /**
     * 扫描Class文件
     *
     * @param classes
     * @param classDir
     */
    private void doScanClass(File classDir) {
        if (classDir == null) {
            return;
        }
        File[] classFiles = classDir.listFiles();
        for (File f : classFiles) {
            if (f.isDirectory()) {
                doScanClass(f);
            } else {
                //不是所有的文件都会被接收
                if (!f.getName().endsWith(".class")) {
                    continue;
                }
                String className = f.getPath().replaceAll("\\\\", "/")
                        .replaceAll(nowProjectClassPath.replaceAll("\\\\", "/"), "")
                        .replaceAll("/", "\\.")
                        .replaceAll(".class", "");

                className = className.substring(1);
                try {
                    if (className.startsWith(scanPackage)) {
                        Class clazz = loader.loadClass(className);
                        if (AsserUtils.isController(clazz) && AsserUtils.isApiModule(clazz)) {
                            controllerClasses.add(clazz);
                        }
                    }
                } catch (Exception e) {
                    this.getLog().error("GEN API DOC ERROR: ", e);
                    continue;
                }
            }
        }
    }
}
