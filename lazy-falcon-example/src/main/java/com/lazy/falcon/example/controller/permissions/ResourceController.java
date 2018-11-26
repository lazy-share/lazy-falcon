package com.lazy.falcon.example.controller.permissions;

import com.lazy.falcon.core.annotaion.GenApiDocByJsonFile;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.ResourceEnitty;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/7/26.
 */
@RestController
public class ResourceController {

    @GenApiDocByJsonFile("doc/resources/put_resource")
    @PutMapping("/resources")
    public ResponseDto putResource(@RequestBody ResourceEnitty enitty){
        return ResponseDto.buildSuccessfully("新增资源成功");
    }
}
