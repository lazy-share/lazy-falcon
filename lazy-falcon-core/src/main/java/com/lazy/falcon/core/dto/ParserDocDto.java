package com.lazy.falcon.core.dto;

import com.lazy.falcon.core.loader.GenDocApiClassLoader;
import com.lazy.falcon.core.model.output.SidebarTreeModel;
import com.lazy.falcon.core.tools.SetsUtils;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;

/**
 * <p>
 *     解析整个Api Doc DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/5/28.
 */
public class ParserDocDto {
    /**
     * Maven Plugin Logger
     */
    private org.apache.maven.plugin.logging.Log log;
    /**
     * 插件ClassLoader
     */
    private GenDocApiClassLoader classLoader;
    /**
     * 域名
     */
    private String domain;
    /**
     * 当前项目-所有控制器Class
     */
    private List<Class<?>> nowProjectControllerClasses;
    /**
     * 当前项目-ClassPath
     */
    private String nowProductClassPath;
    /**
     * 当前项目-文档标题
     */
    private String nowProjectDocTitle;
    /**
     * 工作目录-根目录字符串 BOOT-INF
     */
    private String workRootDirStr;
    /**
     * 工作目录-根目录字符串 class/static
     */
    private String workStaticDir;
    /**
     * 工作目录-根目录字符串 class/static/api_doc/
     */
    private String workApiDocDirStr;
    /**
     * 工作目录-modules目录字符串
     */
    private String workModulesDirStr;
    /**
     * 工作目录-modules/api_template目录字符串
     */
    private String workApiTemplateDirStr;
    /**
     * 工作目录-modules/req目录字符串
     */
    private String workReqParamDirStr;
    /**
     * 工作目录-modules/res目录字符串
     */
    private String workResParamDirStr;
    /**
     * 工作目录-左侧栏目录字符串
     */
    private String workSidebarTreeJsonDirStr;
    /**
     * 工作目录-根目录文件对象
     */
    private File workRootDirFile;
    /**
     * 工作目录-模块目录文件对象
     */
    private File workModulesDirFile;
    /**
     * 工作目录-modules/api_template目录文件对象
     */
    private File workApiTemplateDirFile;
    /**
     * 工作目录-modules/res/xxx.html目录响应参数模板文件对象
     */
    private File workResParamTemplateFile;
    /**
     * 工作目录-modules/req/xx.html目录请求参数文件对象
     */
    private File workReqParamTemplateFile;
    /**
     * 工作目录-modules/req目录文件对象
     */
    private File workReqParamDirFile;
    /**
     * 工作目录-modules/res目录文件对象
     */
    private File workResParamDirFile;
    /**
     * 工作目录-左侧栏文件对象
     */
    private File workSidebarTreeDirFile;
    /**
     * 左侧栏模型列表
     */
    private List<SidebarTreeModel> sidebarTreeModelList = SetsUtils.list();
    /**
     * 当前处理API DTO对象
     */
    private ParserNowApiDto nowApiDto;

    public File getWorkResParamTemplateFile() {
        return workResParamTemplateFile;
    }

    public void setWorkResParamTemplateFile(File workResParamTemplateFile) {
        this.workResParamTemplateFile = workResParamTemplateFile;
    }

    public File getWorkReqParamTemplateFile() {
        return workReqParamTemplateFile;
    }

    public void setWorkReqParamTemplateFile(File workReqParamTemplateFile) {
        this.workReqParamTemplateFile = workReqParamTemplateFile;
    }

    public String getWorkStaticDir() {
        return workStaticDir;
    }

    public void setWorkStaticDir(String workStaticDir) {
        this.workStaticDir = workStaticDir;
    }

    public String getWorkApiDocDirStr() {
        return workApiDocDirStr;
    }

    public void setWorkApiDocDirStr(String workApiDocDirStr) {
        this.workApiDocDirStr = workApiDocDirStr;
    }

    public String getWorkReqParamDirStr() {
        return workReqParamDirStr;
    }

    public void setWorkReqParamDirStr(String workReqParamDirStr) {
        this.workReqParamDirStr = workReqParamDirStr;
    }

    public String getWorkResParamDirStr() {
        return workResParamDirStr;
    }

    public void setWorkResParamDirStr(String workResParamDirStr) {
        this.workResParamDirStr = workResParamDirStr;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }

    public GenDocApiClassLoader getClassLoader() {
        return classLoader;
    }

    public void setClassLoader(GenDocApiClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<Class<?>> getNowProjectControllerClasses() {
        return nowProjectControllerClasses;
    }

    public void setNowProjectControllerClasses(List<Class<?>> nowProjectControllerClasses) {
        this.nowProjectControllerClasses = nowProjectControllerClasses;
    }

    public String getNowProductClassPath() {
        return nowProductClassPath;
    }

    public void setNowProductClassPath(String nowProductClassPath) {
        this.nowProductClassPath = nowProductClassPath;
    }

    public String getNowProjectDocTitle() {
        return nowProjectDocTitle;
    }

    public void setNowProjectDocTitle(String nowProjectDocTitle) {
        this.nowProjectDocTitle = nowProjectDocTitle;
    }

    public String getWorkRootDirStr() {
        return workRootDirStr;
    }

    public void setWorkRootDirStr(String workRootDirStr) {
        this.workRootDirStr = workRootDirStr;
    }

    public String getWorkModulesDirStr() {
        return workModulesDirStr;
    }

    public void setWorkModulesDirStr(String workModulesDirStr) {
        this.workModulesDirStr = workModulesDirStr;
    }

    public String getWorkApiTemplateDirStr() {
        return workApiTemplateDirStr;
    }

    public void setWorkApiTemplateDirStr(String workApiTemplateDirStr) {
        this.workApiTemplateDirStr = workApiTemplateDirStr;
    }

    public String getWorkSidebarTreeJsonDirStr() {
        return workSidebarTreeJsonDirStr;
    }

    public void setWorkSidebarTreeJsonDirStr(String workSidebarTreeJsonDirStr) {
        this.workSidebarTreeJsonDirStr = workSidebarTreeJsonDirStr;
    }

    public File getWorkRootDirFile() {
        return workRootDirFile;
    }

    public void setWorkRootDirFile(File workRootDirFile) {
        this.workRootDirFile = workRootDirFile;
    }

    public File getWorkModulesDirFile() {
        return workModulesDirFile;
    }

    public void setWorkModulesDirFile(File workModulesDirFile) {
        this.workModulesDirFile = workModulesDirFile;
    }

    public File getWorkApiTemplateDirFile() {
        return workApiTemplateDirFile;
    }

    public void setWorkApiTemplateDirFile(File workApiTemplateDirFile) {
        this.workApiTemplateDirFile = workApiTemplateDirFile;
    }

    public File getWorkReqParamDirFile() {
        return workReqParamDirFile;
    }

    public void setWorkReqParamDirFile(File workReqParamDirFile) {
        this.workReqParamDirFile = workReqParamDirFile;
    }

    public File getWorkResParamDirFile() {
        return workResParamDirFile;
    }

    public void setWorkResParamDirFile(File workResParamDirFile) {
        this.workResParamDirFile = workResParamDirFile;
    }

    public File getWorkSidebarTreeDirFile() {
        return workSidebarTreeDirFile;
    }

    public void setWorkSidebarTreeDirFile(File workSidebarTreeDirFile) {
        this.workSidebarTreeDirFile = workSidebarTreeDirFile;
    }

    public List<SidebarTreeModel> getSidebarTreeModelList() {
        return sidebarTreeModelList;
    }

    public void setSidebarTreeModelList(List<SidebarTreeModel> sidebarTreeModelList) {
        this.sidebarTreeModelList = sidebarTreeModelList;
    }

    public ParserNowApiDto getNowApiDto() {
        return nowApiDto;
    }

    public void setNowApiDto(ParserNowApiDto nowApiDto) {
        this.nowApiDto = nowApiDto;
    }
}
