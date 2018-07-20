package com.lazy.falcon.core.type;

import com.lazy.falcon.core.type.dto.BasicProjectTypeDto;

import java.net.URL;
import java.util.List;

/**
 * <p>
 *     项目类型 接口定义
 * </p>
 *
 * @author laizhiyuan
 * @date 2018/6/17.
 */
public interface ProjectType {

    /**
     * 加载 libs
     * @param dto DTO
     * @return libs
     */
    List<URL> loadLibs(BasicProjectTypeDto dto);

    /**
     * 复制结果到归档文件
     * @param dto DTO
     */
    void copyResultToArtifacts(BasicProjectTypeDto dto) throws Exception;
}
