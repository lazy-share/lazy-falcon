package com.lazy.falcon.core.type;

import com.lazy.falcon.common.tools.ExecSystemCommandUtils;
import com.lazy.falcon.common.tools.JarUtils;
import com.lazy.falcon.common.tools.StringUtils;
import com.lazy.falcon.core.type.dto.BasicProjectStructDto;
import com.lazy.falcon.core.type.dto.SpringBootProjectStructDto;

import java.net.URL;
import java.util.List;

/**
 * <p>
 * SpringBootProjectType Impl
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public class SpringBootProjectStruct implements ProjectStruct {

    /**
     * 加载 libs
     *
     * @param dto DTO
     * @return libs
     */
    @Override
    public List<URL> loadLibs(BasicProjectStructDto dto) {
        SpringBootProjectStructDto implDto = (SpringBootProjectStructDto) dto;
        return JarUtils.copySpringBootJarLibsTo(implDto.getSpringBootJarFullPath().replaceAll("file:", ""),
                implDto.getWorkLibDir());
    }

    /**
     * 复制结果到归档文件
     *
     * @param dto DTO
     */
    @Override
    public void copyResultToArtifacts(BasicProjectStructDto dto) throws Exception {
        String result = null;
        SpringBootProjectStructDto implDto = (SpringBootProjectStructDto) dto;
        if (ExecSystemCommandUtils.isWindowsSystem()) {
            result = ExecSystemCommandUtils.exec(ExecSystemCommandUtils.
                    genWindowsSystemCopyResultToSpringBootJarCommand(implDto.getSpringBootJarName()));
        } else {
            result = ExecSystemCommandUtils.exec(ExecSystemCommandUtils.
                    genLinuxSystemCopyResultToSpringBootJarCommand(implDto.getSpringBootJarName()));
        }
        if (!StringUtils.isEmpty(result)) {
            implDto.getLog().info("GEN API DOC exec command result: " + result);
        }
    }
}
