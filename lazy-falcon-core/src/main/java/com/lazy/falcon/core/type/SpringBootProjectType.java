package com.lazy.falcon.core.type;

import com.lazy.falcon.core.tools.ExecSystemCommandUtils;
import com.lazy.falcon.core.tools.JarUtils;
import com.lazy.falcon.core.tools.StringUtils;
import com.lazy.falcon.core.type.dto.BasicProjectTypeDto;
import com.lazy.falcon.core.type.dto.SpringBootProjectTypeDto;

import java.net.URL;
import java.util.List;

/**
 * <p>
 *     SpringBootProjectType Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public class SpringBootProjectType implements ProjectType {

    /**
     * 加载 libs
     * @param dto DTO
     * @return libs
     */
    @Override
    public List<URL> loadLibs(BasicProjectTypeDto dto) {
        SpringBootProjectTypeDto implDto = (SpringBootProjectTypeDto) dto;
        return JarUtils.copySpringBootJarLibsTo(implDto.getSpringBootJarFullPath().replaceAll("file:", ""),
                implDto.getWorkLibDir());
    }

    /**
     * 复制结果到归档文件
     * @param dto DTO
     */
    @Override
    public void copyResultToArtifacts(BasicProjectTypeDto dto) throws Exception {
        String result = null;
        SpringBootProjectTypeDto implDto = (SpringBootProjectTypeDto) dto;
        if (ExecSystemCommandUtils.isWindowsSystem()){
            result = ExecSystemCommandUtils.exec(ExecSystemCommandUtils.
                    genWindowsSystemCopyResultToSpringBootJarCommand(implDto.getSpringBootJarName()));
        }else {
            result = ExecSystemCommandUtils.exec(ExecSystemCommandUtils.
                    genLinuxSystemCopyResultToSpringBootJarCommand(implDto.getSpringBootJarName()));
        }
        if (!StringUtils.isEmpty(result)){
            implDto.getLog().info("GEN API DOC exec command result: " + result);
        }
    }
}
