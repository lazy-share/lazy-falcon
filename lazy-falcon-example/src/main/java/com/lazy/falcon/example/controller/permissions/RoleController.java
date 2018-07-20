package com.lazy.falcon.example.controller.permissions;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.core.annotaion.GenApiDocByJsonFile;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.RoleEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
@Controller
public class RoleController {

    @GenApiDocByJsonFile("doc/roles/put_role")
    @PutMapping("/roles")
    public @ResponseBody
    ResponseDto putRole(@RequestBody RoleEntity entity){
        return ResponseDto.buildSuccessfully("新增角色成功", entity);
    }
}
