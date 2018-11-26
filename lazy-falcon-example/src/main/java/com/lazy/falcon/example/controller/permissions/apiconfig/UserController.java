package com.lazy.falcon.example.controller.permissions.apiconfig;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.common.annotaion.ApiConfig;
import com.lazy.falcon.common.annotaion.ApiModule;
import com.lazy.falcon.common.tools.SetsUtils;
import com.lazy.falcon.example.dto.ConditionPagingDto;
import com.lazy.falcon.example.dto.ResponseDto;
import com.lazy.falcon.example.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p></p>
 *
 * @author laizhiyuan
 * @date 2018/6/3.
 */
@RestController
@ApiModule(value = "系统权限-用户控制器", moduleCls = UserEntity.class)
public class UserController {


    @ApiConfig("doc/users/put_user")
    @PutMapping("/users")
    public ResponseDto<UserEntity> putUser(@RequestBody UserEntity entity) {
        return new ResponseDto<UserEntity>("200", "新增用户成功", entity);
    }

    @ApiConfig("doc/users/post_user")
    @PostMapping("/users")
    public ResponseDto<UserEntity> postUser(@RequestBody UserEntity entity) {
        return new ResponseDto<UserEntity>("200", "更新用户成功", entity);
    }

    @ApiConfig("doc/users/conditions_user")
    @PostMapping("/users/conditions")
    public ResponseDto<UserEntity> usersByConditions(@RequestBody ConditionPagingDto conditionPagingDto) {
        System.out.println(JSON.toJSONString(conditionPagingDto));
        UserEntity entity = new UserEntity();
        entity.setLoginName("laizhiyuan");
        entity.setPassword("123456");
        entity.setMobile("13666666666");
        UserEntity entity1 = new UserEntity();
        entity1.setLoginName("laizhiyuan");
        entity1.setPassword("123456");
        entity1.setMobile("13666666666");
        List<UserEntity> rows = SetsUtils.list();
        rows.add(entity);
        rows.add(entity1);
        return new ResponseDto<UserEntity>("200", "查询成功", entity);
    }

    @ApiConfig("doc/users/del_user")
    @DeleteMapping("/users/{id}")
    public ResponseDto<Long> delUser(@PathVariable("id") Long id) {
        return new ResponseDto<Long>("200", "删除用户成功", id);
    }

}
