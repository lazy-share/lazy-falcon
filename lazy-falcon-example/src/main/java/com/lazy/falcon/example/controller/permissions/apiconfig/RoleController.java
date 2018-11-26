package com.lazy.falcon.example.controller.permissions.apiconfig;

import com.lazy.falcon.common.annotaion.ApiConfig;
import com.lazy.falcon.common.annotaion.ApiModule;
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
@ApiModule(value = "系统权限-角色", moduleCls = RoleEntity.class)
public class RoleController {

    @ApiConfig("doc/roles/put_role")
    @PutMapping("/roles")
    public @ResponseBody
    ResponseDto<RoleEntity> putRole(@RequestBody RoleEntity entity) {
        return new ResponseDto<RoleEntity>("200", "新增角色成功", entity);
    }
}
