package com.lazy.falcon.core.type.dto;

/**
 * <p>
 *     Spring Boot 项目类型 DTO
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public class SpringBootProjectStructDto extends BasicProjectStructDto {

    private static final long serialVersionUID = 9887966341465462L;

    /**
     * Spring Boot Jar 全路径
     */
    private String springBootJarFullPath;
    /**
     * 工作Lib目录
     */
    private String workLibDir;
    /**
     * springBootJarName
     */
    private String springBootJarName;

    public String getSpringBootJarName() {
        return springBootJarName;
    }

    public void setSpringBootJarName(String springBootJarName) {
        this.springBootJarName = springBootJarName;
    }

    public String getSpringBootJarFullPath() {
        return springBootJarFullPath;
    }

    public void setSpringBootJarFullPath(String springBootJarFullPath) {
        this.springBootJarFullPath = springBootJarFullPath;
    }

    public String getWorkLibDir() {
        return workLibDir;
    }

    public void setWorkLibDir(String workLibDir) {
        this.workLibDir = workLibDir;
    }
}
