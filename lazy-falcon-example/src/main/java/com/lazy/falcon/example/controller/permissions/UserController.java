package com.lazy.falcon.example.controller.permissions;

import com.alibaba.fastjson.JSON;
import com.lazy.falcon.core.annotaion.GenApiDocByJsonFile;
import com.lazy.falcon.core.tools.SetsUtils;
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
public class UserController {


    @GenApiDocByJsonFile("doc/users/put_user")
    @PutMapping("/users")
    public ResponseDto putUser(@RequestBody UserEntity entity){
        return ResponseDto.buildSuccessfully("新增用户成功", entity);
    }

    @GenApiDocByJsonFile("doc/users/post_user")
    @PostMapping("/users")
    public ResponseDto postUser(@RequestBody UserEntity entity){
        return ResponseDto.buildSuccessfully("更新用户成功", entity);
    }

    @GenApiDocByJsonFile("doc/users/conditions_user")
    @PostMapping("/users/conditions")
    public ResponseDto usersByConditions(@RequestBody ConditionPagingDto conditionPagingDto){
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
        return ResponseDto.buildSuccessfully(rows);
    }

    @GenApiDocByJsonFile("doc/users/del_user")
    @DeleteMapping("/users/{id}")
    public ResponseDto delUser(@PathVariable("id") Long id){
        return ResponseDto.buildSuccessfully("删除用户成功 id：" + id);
    }

}
