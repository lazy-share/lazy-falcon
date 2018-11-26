package com.lazy.falcon.core.type;

import com.lazy.falcon.core.type.dto.BasicProjectStructDto;

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
public interface ProjectStruct {

    /**
     * 加载 libs
     * @param dto DTO
     * @return libs
     */
    List<URL> loadLibs(BasicProjectStructDto dto);

    /**
     * 复制结果到归档文件
     * @param dto DTO
     */
    void copyResultToArtifacts(BasicProjectStructDto dto) throws Exception;
}
